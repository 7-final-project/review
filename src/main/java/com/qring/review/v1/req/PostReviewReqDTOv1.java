package com.qring.review.v1.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReviewReqDTOv1 {

    @Valid
    @NotNull(message = "리뷰 정보를 입력해주세요.")
    private Review review;

    @Getter
    public static class Review {

        @NotNull(message = "식당 정보를 입력해주세요.")
        private Long restaurantId;

        @NotNull(message = "평점을 입력해주세요.")
        private int rating;

        @NotBlank(message = "내용을 입력해주세요")
        private String content;

    }
}
