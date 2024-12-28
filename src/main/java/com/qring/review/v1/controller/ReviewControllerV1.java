package com.qring.review.v1.controller;

import com.qring.review.domain.v1.res.ResDTO;
import com.qring.review.domain.v1.res.ReviewPostResDTOv1;
import com.qring.review.v1.req.PostReviewReqDTOv1;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewControllerV1 {

    @PostMapping("/v1/reviews")
    public ResponseEntity<ResDTO<ReviewPostResDTOv1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                             @Valid @RequestBody PostReviewReqDTOv1 dto) {

        return new ResponseEntity<>(
                ResDTO.<ReviewPostResDTOv1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("리뷰 생성에 성공했습니다.")
                        .data(ReviewPostResDTOv1.of(1L, 9999L, 5, "example-content"))
                        .build(),
                HttpStatus.CREATED
        );
    }
}
