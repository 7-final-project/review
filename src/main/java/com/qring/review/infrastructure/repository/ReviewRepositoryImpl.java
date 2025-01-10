package com.qring.review.infrastructure.repository;

import com.qring.review.application.v1.message.ReviewStatisticsDTOV1;
import com.qring.review.domain.model.ReviewEntity;
import com.qring.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JpaReviewRepository jpaReviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    // 특정 ID로 삭제되지 않은 리뷰 조회
    @Override
    public Optional<ReviewEntity> findByIdAndDeletedAtIsNull(Long id) {
        return jpaReviewRepository.findByIdAndDeletedAtIsNull(id);
    }

    // 조건에 따른 리뷰 검색
    @Override
    public Page<ReviewEntity> findReviewPageByDeletedAtIsNullWithConditions(Pageable pageable, Long userId, Long restaurantId, Long reservationId, String sort) {
        return reviewQueryRepository.findReviewPageByDeletedAtIsNullWithConditions(pageable, userId, restaurantId, reservationId, sort);
    }

    // 리뷰 저장
    @Override
    public ReviewEntity save(ReviewEntity ReviewEntity) {
        return jpaReviewRepository.save(ReviewEntity);
    }

    @Override
    public ReviewStatisticsDTOV1 findReviewStatisticsByRestaurantIdAndDeletedAtIsNull(Long restaurantId) {
        Object[] rawResult = jpaReviewRepository.findReviewStatisticsRawByRestaurantId(restaurantId);
        Object[] result = (Object[]) rawResult[0];  // 중첩된 배열에서 실제 결과 추출하기 위해서 이렇게 사용함.

        long reviewCount = Long.valueOf(result[0].toString());
        int totalRating = Integer.valueOf(result[1].toString());

        return ReviewStatisticsDTOV1.from(reviewCount, totalRating);
    }
}
