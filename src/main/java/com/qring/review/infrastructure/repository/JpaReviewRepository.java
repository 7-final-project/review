package com.qring.review.infrastructure.repository;

import com.qring.review.domain.model.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // 특정 ID로 삭제되지 않은 식당 조회
    Optional<ReviewEntity> findByIdAndDeletedAtIsNull(Long id);

}
