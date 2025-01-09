package com.qring.review.application.v1.message;

import lombok.Getter;

@Getter
public class ReviewStatistics {
    private final long reviewCount;
    private final long totalRating;

    // JPQL에서 사용할 수 있도록 public 생성자를 추가합니다.
    public ReviewStatistics(long reviewCount, long totalRating) {
        this.reviewCount = reviewCount;
        this.totalRating = totalRating;
    }
}
