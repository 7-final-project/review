package com.qring.review.application.message;

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
    private String eventType;  // CREATE, UPDATE, DELETE

    public static ReviewEventMessageDTOV1 from(Long restaurantId, int rating, String eventType) {
        return ReviewEventMessageDTOV1.builder()
                .restaurantId(restaurantId)
                .rating(rating)
                .eventType(eventType)
                .build();
    }
}

