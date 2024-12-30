package com.qring.review.presentation.v1.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PutReviewReqDTOv1 {

    @Valid
    @NotNull(message = "리뷰 정보를 입력해주세요.")
    private Review review;

    @Getter
    public static class Review {

        @Positive(message = "평점을 입력해주세요.")
        @Min(value = 1, message = "평점은 최소 1점부터입니다.")
        @Max(value = 5, message = "평점은 최대 5점까지입니다.")
        private int rating;

        @NotBlank(message = "내용을 입력해주세요")
        private String content;

    }
}
