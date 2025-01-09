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
public class ReviewPostResDTOV1 {

    private Review review;
    private ReviewInfo reviewInfo;

    public static ReviewPostResDTOV1 of(ReviewEntity reviewEntity) {
        return ReviewPostResDTOV1.builder()
                .review(Review.from(reviewEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {

        private Long userId;
        private Long restaurantId;
        private Long reservationId;
        private int rating;
        private String content;

        public static Review from(ReviewEntity reviewEntity) {
            return Review.builder()
                    .userId(reviewEntity.getUserId())
                    .restaurantId(reviewEntity.getRestaurantId())
                    .reservationId(reviewEntity.getReservationId())
                    .rating(reviewEntity.getRating())
                    .content(reviewEntity.getContent())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewInfo {

        private Long id;
        private Long restaurantId;
        private int rating;

        public static ReviewInfo from(Long id, Long restaurantId, int rating) {
            return ReviewInfo.builder()
                    .id(id)
                    .restaurantId(restaurantId)
                    .rating(rating)
                    .build();
        }
    }

}
