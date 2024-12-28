package com.qring.review.v1.controller;

import com.qring.review.domain.v1.res.ResDTO;
import com.qring.review.domain.v1.res.ReviewPostResDTOv1;
import com.qring.review.infrastructure.docs.ReviewControllerSwagger;
import com.qring.review.v1.req.PostReviewReqDTOv1;
import com.qring.review.v1.req.PutReviewReqDTOv1;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewControllerV1 implements ReviewControllerSwagger {

    @PostMapping("/v1/reviews")
    public ResponseEntity<ResDTO<ReviewPostResDTOv1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                             @Valid @RequestBody PostReviewReqDTOv1 dto) {

        return new ResponseEntity<>(
                ResDTO.<ReviewPostResDTOv1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("리뷰 생성에 성공했습니다.")
                        .data(ReviewPostResDTOv1.of(userId, 9999L, 5, "example-content"))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-User-Id") Long userId,
                                                @PathVariable Long reviewId,
                                                @Valid @RequestBody PutReviewReqDTOv1 dto) {

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 수정에 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@RequestHeader("X-User-Id") Long userId, @PathVariable Long reviewId) {

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 삭제에 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
