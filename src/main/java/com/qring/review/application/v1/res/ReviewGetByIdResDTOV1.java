package com.qring.review.application.v1.res;

import com.qring.review.domain.model.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewGetByIdResDTOV1 {

    private Review review;

    public static ReviewGetByIdResDTOV1 of(ReviewEntity reviewEntity) {
        return ReviewGetByIdResDTOV1.builder()
                .review(Review.from(reviewEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {

        private Long userId;
        private Long id;
        private Long restaurantId;
        private int rating;
        private String content;

        public static Review from(ReviewEntity reviewEntity) {
            return Review.builder()
                    .userId(reviewEntity.getUserId())
                    .id(reviewEntity.getId())
                    .restaurantId(reviewEntity.getRestaurantId())
                    .rating(reviewEntity.getRating())
                    .content(reviewEntity.getContent())
                    .build();
        }
    }
}
