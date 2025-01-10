package com.qring.review.application.v1.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatisticsDTOV1 {

    private long reviewCount;
    private long totalRating;

    // from 메서드 추가
    public static ReviewStatisticsDTOV1 from(long reviewCount, long totalRating) {
        return ReviewStatisticsDTOV1.builder()
                .reviewCount(reviewCount)
                .totalRating(totalRating)
                .build();
    }
}
