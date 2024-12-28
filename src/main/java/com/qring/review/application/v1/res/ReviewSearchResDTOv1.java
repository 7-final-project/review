package com.qring.review.application.v1.res;

import com.qring.review.domain.model.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSearchResDTOv1 {

    private ReviewPage reviewPage;

    public static ReviewSearchResDTOv1 of(Page<ReviewEntity> reviewEntityPage) {
        return ReviewSearchResDTOv1.builder()
                .reviewPage(ReviewPage.from(reviewEntityPage))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPage {

        private List<Review> content;
        private PageDetails page;

        public static ReviewPage from(Page<ReviewEntity> reviewEntityPage) {
            return ReviewPage.builder()
                    .content(Review.from(reviewEntityPage.getContent()))
                    .page(PageDetails.from(reviewEntityPage))
                    .build();
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Review {

            private Long userId;
            private Long reviewId;
            private Long restaurantId;
            private int rating;
            private String content;

            public static List<Review> from(List<ReviewEntity> reviewEntityList) {
                return reviewEntityList.stream()
                        .map(Review::from)
                        .toList();
            }

            public static Review from(ReviewEntity reviewEntity) {

                return Review.builder()
                        .userId(reviewEntity.getUserId())
                        .reviewId(reviewEntity.getId())
                        .restaurantId(reviewEntity.getRestaurantId())
                        .rating(reviewEntity.getRating())
                        .content(reviewEntity.getContent())
                        .build();
            }
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PageDetails {

            private int size;
            private int number;
            private long totalElements;
            private int totalPages;

            public static PageDetails from(Page<ReviewEntity> reviewEntityPage) {
                return PageDetails.builder()
                        .size(reviewEntityPage.getSize())
                        .number(reviewEntityPage.getNumber())
                        .totalElements(reviewEntityPage.getTotalElements())
                        .totalPages(reviewEntityPage.getTotalPages())
                        .build();
            }
        }
    }
}
