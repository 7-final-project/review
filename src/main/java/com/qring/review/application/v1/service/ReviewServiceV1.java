package com.qring.review.application.v1.service;

import com.qring.review.application.global.exception.EntityNotFoundException;
import com.qring.review.application.global.exception.UnauthorizedAccessException;
import com.qring.review.application.v1.message.KafkaMessageProducerV1;
import com.qring.review.application.v1.message.ReviewEventMessage;
import com.qring.review.application.v1.message.ReviewStatistics;
import com.qring.review.application.v1.res.*;
import com.qring.review.domain.model.ReviewEntity;
import com.qring.review.domain.repository.ReviewRepository;
import com.qring.review.infrastructure.util.PassportUtil;
import com.qring.review.presentation.v1.req.PostReviewReqDTOV1;
import com.qring.review.presentation.v1.req.PutReviewReqDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceV1 {

    private final ReviewRepository reviewRepository;
    private final RestaurantServiceV1 restaurantServiceV1;
    private final ReservationServiceV1 reservationServiceV1;
    private final KafkaMessageProducerV1 kafkaMessageProducerV1;

    private static final String STATUS_VISITED = "방문";
    private static final String ROLE_ADMIN = "관리자";

    @Transactional
    public ReviewPostResDTOV1 postBy(String passport, PostReviewReqDTOV1 dto) {
        /*
         -----
         TODO : FeignClent 로직 구현
        step 1. 예약 조회(FeignClent)
                - passport에 있는 userId
                - 예약 id -> userId, restaurantId,status 를 반환하는데
                - 없는 예약이거나 방문전이거나 방문이 아니면 userId, restaurantId는 빈 값으로 status는 미방문으로
                - 방문 한 예약이면 userId, restaurantId는 해당 값으로 status는 방문으로
                - 최종 테스트  완료하면 아래 더미 데이터와 TODO 주석 제거
         -----
        */
        // 예약 조회(FeignClient)
//        ReservationGetByIdResDTOV1.ReservationInfo reservationInfo =
//                reservationServiceV1
//                .getByReview(dto.getReview().getReservationId())
//                .getBody()
//                .getData();

        // 더미 데이터 생성
        ReservationGetByIdResDTOV1.ReservationInfo reservationInfo = ReservationGetByIdResDTOV1.ReservationInfo.builder()
                .userId(664440243592086250L) // 더미 유저 ID
                .restaurantId(664879975619523130L) // 더미 식당 ID
                .status("방문") // 더미 상태 값
                .build();


        // 예약이 없는 경우 또는 미방문 상태일 경우
        if (reservationInfo == null || !Objects.equals(reservationInfo.getStatus(), STATUS_VISITED)) {
            throw new EntityNotFoundException("예약 정보가 없거나 방문 기록이 없습니다.");
        }

        // 예약된 유저와 현재 유저가 동일한지 확인
        if (!Objects.equals(reservationInfo.getUserId(), PassportUtil.getUserId(passport))) {
            throw new UnauthorizedAccessException("로그인한 유저와 예약 정보가 일치하지 않습니다.");
        }

        // 예약된 식당과 현재 요청된 식당이 동일한지 확인
        if (!Objects.equals(reservationInfo.getRestaurantId(), dto.getReview().getRestaurantId())) {
            throw new UnauthorizedAccessException("예약된 식당과 요청된 식당이 일치하지 않습니다.");
        }

        // 식당 조회(FeignClent)
        boolean isExist = restaurantServiceV1.getBy(dto.getReview().getRestaurantId()).getStatusCode().is2xxSuccessful();

        if (!isExist) {
            throw new EntityNotFoundException("식당을 찾을 수 없습니다.");
        }

        ReviewEntity reviewEntityForSave = ReviewEntity.createReviewEntity(
                PassportUtil.getUserId(passport),
                dto.getReview().getRestaurantId(),
                dto.getReview().getReservationId(),
                dto.getReview().getRating(),
                dto.getReview().getContent(),
                PassportUtil.getUsername(passport)
        );
        reviewRepository.save(reviewEntityForSave);

        // 리뷰 통계 계산
        ReviewStatistics statistics = reviewRepository.findReviewStatisticsByRestaurantIdAndDeletedAtIsNull(dto.getReview().getRestaurantId());

        // Kafka 메시지 발행
        kafkaMessageProducerV1.publishReviewEvent(
                ReviewEventMessage.builder()
                        .restaurantId(dto.getReview().getRestaurantId())
                        .rating(dto.getReview().getRating())
                        .reviewCount(statistics.getReviewCount())
                        .totalRating(statistics.getTotalRating())
                        .eventType("CREATE")
                        .build()
        );

        // 저장 및 DTO 반환
        return ReviewPostResDTOV1.of(reviewEntityForSave);
    }

    @Transactional(readOnly = true)
    public ReviewSearchResDTOV1 searchBy(Pageable pageable, Long userId, Long restaurantId, Long reservationId, String sort) {
        return ReviewSearchResDTOV1.of(reviewRepository.findReviewPageByDeletedAtIsNullWithConditions(pageable, userId, restaurantId, reservationId, sort));
    }

    @Transactional(readOnly = true)
    public ReviewGetByIdResDTOV1 getBy(Long id) {
        // 리뷰 조회
        ReviewEntity reviewEntityForMapping = reviewRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        return ReviewGetByIdResDTOV1.of(reviewEntityForMapping);
    }

    @Transactional
    public void putBy(String passport, Long id, PutReviewReqDTOV1 dto) {
        // 리뷰 조회
        ReviewEntity reviewEntityForModify = getReviewEntityById(id);

        validateReviewOwnership(passport, reviewEntityForModify.getUserId(), "수정");

        // 리뷰 수정
        reviewEntityForModify.updateReviewEntity(
                dto.getReview().getRating(),
                dto.getReview().getContent(),
                PassportUtil.getUsername(passport)
        );

        // 리뷰 통계 계산
        ReviewStatistics statistics = reviewRepository.findReviewStatisticsByRestaurantIdAndDeletedAtIsNull(reviewEntityForModify.getRestaurantId());

        // Kafka 메시지 발행
        kafkaMessageProducerV1.publishReviewEvent(
                ReviewEventMessage.builder()
                        .restaurantId(reviewEntityForModify.getRestaurantId())
                        .rating(dto.getReview().getRating())
                        .reviewCount(statistics.getReviewCount())
                        .totalRating(statistics.getTotalRating())
                        .eventType("UPDATE")
                        .build()
        );
    }

    @Transactional
    public void deleteBy(String passport, Long id) {
        // 리뷰 조회
        ReviewEntity reviewEntityForDelete = getReviewEntityById(id);

        validateReviewOwnership(passport, reviewEntityForDelete.getUserId(), "삭제");

        // 리뷰 논리 삭제
        reviewEntityForDelete.deleteReviewEntity(PassportUtil.getUsername(passport));

        // 리뷰 통계 계산
        ReviewStatistics statistics = reviewRepository.findReviewStatisticsByRestaurantIdAndDeletedAtIsNull(reviewEntityForDelete.getRestaurantId());

        // Kafka 메시지 발행
        kafkaMessageProducerV1.publishReviewEvent(
                ReviewEventMessage.builder()
                        .restaurantId(reviewEntityForDelete.getRestaurantId())
                        .rating(0)
                        .reviewCount(statistics.getReviewCount())
                        .totalRating(statistics.getTotalRating())
                        .eventType("DELETE")
                        .build()
        );
    }

    private ReviewEntity getReviewEntityById(Long id) {
        return reviewRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
    }


    private void validateReviewOwnership(String passport, Long entityUserId, String action) {
        String role = PassportUtil.getRole(passport);
        Long userId = PassportUtil.getUserId(passport);

        if (!Objects.equals(role, ROLE_ADMIN) && !Objects.equals(entityUserId, userId)) {
            throw new UnauthorizedAccessException("본인이 작성한 리뷰만 " + action + "할 수 있습니다.");
        }
    }


}

