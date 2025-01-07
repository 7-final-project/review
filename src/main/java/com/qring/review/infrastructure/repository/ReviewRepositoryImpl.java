package com.qring.review.infrastructure.repository;

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
    public Page<ReviewEntity> findReviewPageByDeletedAtIsNullWithConditions(Pageable pageable, Long userId, Long restaurantId, String sort) {
        return reviewQueryRepository.findReviewPageByDeletedAtIsNullWithConditions(pageable, userId, restaurantId, sort);
    }

    // 리뷰 저장
    @Override
    public ReviewEntity save(ReviewEntity ReviewEntity) {
        return jpaReviewRepository.save(ReviewEntity);
    }
}
