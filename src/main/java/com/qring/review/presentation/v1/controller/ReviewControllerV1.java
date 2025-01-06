package com.qring.review.presentation.v1.controller;


import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.v1.res.ReviewGetByIdResDTOV1;
import com.qring.review.application.v1.res.ReviewPostResDTOV1;
import com.qring.review.application.v1.res.ReviewSearchResDTOV1;
import com.qring.review.application.v1.service.ReviewService;
import com.qring.review.domain.model.ReviewEntity;
import com.qring.review.infrastructure.docs.ReviewControllerSwagger;
import com.qring.review.presentation.v1.req.PostReviewReqDTOV1;
import com.qring.review.presentation.v1.req.PutReviewReqDTOV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
public class ReviewControllerV1 implements ReviewControllerSwagger {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResDTO<ReviewPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                             @Valid @RequestBody PostReviewReqDTOV1 dto) {

        return new ResponseEntity<>(
                ResDTO.<ReviewPostResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("리뷰 생성에 성공했습니다.")
                        .data(reviewService.postBy(userId, dto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ReviewSearchResDTOV1>> searchBy(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(name = "userId", required = false) Long userId,
                                                                 @RequestParam(name = "restaurantId", required = false) Long restaurantId,
                                                                 @RequestParam(name = "sort", required = false) String sort) {

        return new ResponseEntity<>(
                ResDTO.<ReviewSearchResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 검색에 성공했습니다.")
                        .data(reviewService.searchBy(pageable, userId, restaurantId, sort))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ReviewGetByIdResDTOV1>> getBy(@PathVariable Long id) {

        return new ResponseEntity<>(
                ResDTO.<ReviewGetByIdResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 검색에 성공했습니다.")
                        .data(reviewService.getBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-User-Id") Long userId,
                                                @PathVariable Long id,
                                                @Valid @RequestBody PutReviewReqDTOV1 dto) {
        reviewService.putBy(userId, id, dto);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 수정에 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
        reviewService.deleteBy(userId, id);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 삭제에 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
