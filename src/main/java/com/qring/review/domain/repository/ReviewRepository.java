package com.qring.review.domain.repository;

import com.qring.review.application.v1.message.ReviewStatisticsDTOV1;
import com.qring.review.domain.model.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRepository {

    // 특정 ID로 삭제되지 않은 리뷰 조회
    Optional<ReviewEntity> findByIdAndDeletedAtIsNull(Long id);

    // 조건에 따른 리뷰 검색
    Page<ReviewEntity> findReviewPageByDeletedAtIsNullWithConditions(Pageable pageable, Long userId, Long restaurantId, Long reservationId, String sort);

    // 리뷰 저장
    ReviewEntity save(ReviewEntity categoryEntity);

    ReviewStatisticsDTOV1 findReviewStatisticsByRestaurantIdAndDeletedAtIsNull(Long restaurantId);
}
