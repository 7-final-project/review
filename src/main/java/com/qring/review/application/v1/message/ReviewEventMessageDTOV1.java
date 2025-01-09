package com.qring.review.application.v1.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEventMessageDTOV1 {
    private Long restaurantId; // 식당 ID
    private int rating;        // 현재 리뷰의 평점
    private long reviewCount;  // 총 리뷰 개수
    private long totalRating;   // 총 리뷰 점수
    private String eventType;  // CREATE, UPDATE, DELETE
}

