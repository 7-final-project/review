package com.qring.review.infrastructure.repository;

import com.qring.review.domain.model.ReviewEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.qring.review.domain.model.QReviewEntity.reviewEntity;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    // 조건에 따른 식당 검색
    public Page<ReviewEntity> findReviewPageByDeletedAtIsNullWithConditions(
            Long userId, Long restaurantId, String sort, Pageable pageable) {

        // 조건에 맞는 결과 조회
        List<ReviewEntity> results = queryFactory
                .selectFrom(reviewEntity)
                .where(
                        reviewEntity.deletedAt.isNull(),
                        userIdEq(userId),
                        restaurantIdEq(restaurantId)
                )
                .orderBy(getOrderSpecifier(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        JPQLQuery<Long> countQuery = queryFactory
                .select(reviewEntity.count())
                .from(reviewEntity)
                .where(
                        reviewEntity.deletedAt.isNull(),
                        userIdEq(userId),
                        restaurantIdEq(restaurantId)
                );

        // Page 반환
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    // 조건 메서드들
    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? reviewEntity.userId.eq(userId) : null;
    }
    private BooleanExpression restaurantIdEq(Long restaurantId) {
        return restaurantId != null ? reviewEntity.restaurantId.eq(restaurantId) : null;
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort) {
        switch (sort.toLowerCase()) {
            case "high":
                return reviewEntity.rating.desc();
            case "low":
                return reviewEntity.rating.asc();
            case "oldest":
                return reviewEntity.createdAt.asc();
            default:
                return reviewEntity.createdAt.desc();
        }
    }


}
