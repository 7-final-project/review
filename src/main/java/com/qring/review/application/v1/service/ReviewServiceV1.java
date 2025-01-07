package com.qring.review.application.v1.service;

import com.qring.review.application.global.exception.EntityNotFoundException;
import com.qring.review.application.global.exception.UnauthorizedAccessException;
import com.qring.review.application.v1.res.ReviewGetByIdResDTOV1;
import com.qring.review.application.v1.res.ReviewPostResDTOV1;
import com.qring.review.application.v1.res.ReviewSearchResDTOV1;
import com.qring.review.domain.model.ReviewEntity;
import com.qring.review.domain.repository.ReviewRepository;
import com.qring.review.infrastructure.util.PassportUtil;
import com.qring.review.presentation.v1.req.PostReviewReqDTOV1;
import com.qring.review.presentation.v1.req.PutReviewReqDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceV1 {

    private final ReviewRepository reviewRepository;
    private final RestaurantServiceV1 ReviewServiceV1;

    @Transactional
    public ReviewPostResDTOV1 postBy(String passport, PostReviewReqDTOV1 dto) {
        // 리뷰 엔티티 생성
        /*
         -----
         TODO : FeignClent 로직 구현
        step 1. 예약 조회(FeignClent)
                - passport에 있는 userId와 dto에 있는 restaurantId를 사용하여 해당 식당을 방문한적이 있는지 조회합니다.
                - 방문한 적이 없다면 방문한 고객만 리뷰를 작성할 수 있다고 안내합니다.
         -----
        */
        // 식당 조회(FeignClent)
        if(!ReviewServiceV1.existsBy(dto.getReview().getRestaurantId())){
            throw new EntityNotFoundException("식당을 찾을 수 없습니다.");
        }

        ReviewEntity reviewEntityForSave = ReviewEntity.createReviewEntity(
                PassportUtil.getUserId(passport),
                dto.getReview().getRestaurantId(),
                dto.getReview().getRating(),
                dto.getReview().getContent(),
                PassportUtil.getUsername(passport)
        );

        // 저장 및 DTO 반환
        return ReviewPostResDTOV1.of(reviewRepository.save(reviewEntityForSave));
    }

    @Transactional(readOnly = true)
    public ReviewSearchResDTOV1 searchBy(Pageable pageable, Long userId, Long restaurantId, String sort) {
        return ReviewSearchResDTOV1.of(reviewRepository.findReviewPageByDeletedAtIsNullWithConditions(pageable, userId, restaurantId, sort));
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
    }

    @Transactional
    public void deleteBy(String passport, Long id) {
        // 리뷰 조회
        ReviewEntity reviewEntityForDelete = getReviewEntityById(id);

        validateReviewOwnership(passport, reviewEntityForDelete.getUserId(), "삭제");

        // 리뷰 논리 삭제
        reviewEntityForDelete.deleteReviewEntity(PassportUtil.getUsername(passport));
    }

    private ReviewEntity getReviewEntityById(Long id) {
        return reviewRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
    }


    private void validateReviewOwnership(String passport, Long entityUserId, String action) {
        String role = PassportUtil.getRole(passport);
        Long userId = PassportUtil.getUserId(passport);

        if (!"관리자".equals(role) && !entityUserId.equals(userId)) {
            throw new UnauthorizedAccessException("본인이 작성한 리뷰만 " + action + "할 수 있습니다.");
        }
    }


}

