package com.qring.review.infrastructure.repository;

import com.qring.review.application.v1.message.ReviewStatisticsDTOV1;
import com.qring.review.domain.model.ReviewEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // 특정 ID로 삭제되지 않은 식당 조회
    Optional<ReviewEntity> findByIdAndDeletedAtIsNull(Long id);

    // 특정 식당의 총 리뷰 개수와 총 점수를 반환
    @Query("SELECT COUNT(r.id), COALESCE(SUM(r.rating), 0) " +
            "FROM ReviewEntity r " +
            "WHERE r.restaurantId = :restaurantId AND r.deletedAt IS NULL")
    Object[] findReviewStatisticsByRestaurantIdAndDeletedAtIsNull(@Param("restaurantId") Long restaurantId);

}
