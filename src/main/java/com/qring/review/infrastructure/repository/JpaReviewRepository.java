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
    Object[] findReviewStatisticsRawByRestaurantId(@Param("restaurantId") Long restaurantId);

    //JPQL의 SELECT new 구문은 런타임에 클래스를 찾아 인스턴스화해야 하므로,
    // 자바의 클래스로더가 클래스를 정확히 찾을 수 있도록 전체 패키지 경로가 반드시 필요합니다.
    // import 문은 컴파일 시점에만 작동하기 때문에 JPQL에서는 사용할 수 없습니다.
}
