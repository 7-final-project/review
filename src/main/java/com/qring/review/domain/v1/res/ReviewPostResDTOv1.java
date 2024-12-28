package com.qring.review.domain.v1.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPostResDTOv1 {

    private Review review;

    public static ReviewPostResDTOv1 of(Long userId, Long restaurantId, int rating, String content) {
        return ReviewPostResDTOv1.builder()
                .review(Review.from(userId, restaurantId, rating, content))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {

        private Long userId;
        private Long restaurantId;
        private int rating;
        private String content;

        public static Review from(Long userId, Long restaurantId, int rating, String content) {
            return Review.builder()
                    .userId(userId)
                    .restaurantId(restaurantId)
                    .rating(rating)
                    .content(content)
                    .build();
        }
    }
}
