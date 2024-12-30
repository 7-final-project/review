package com.qring.review.presentation.v1.controller;



import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.v1.res.ReviewGetByIdResDTOV1;
import com.qring.review.application.v1.res.ReviewPostResDTOV1;
import com.qring.review.application.v1.res.ReviewSearchResDTOV1;
import com.qring.review.domain.model.ReviewEntity;
import com.qring.review.infrastructure.docs.ReviewControllerSwagger;
import com.qring.review.presentation.v1.req.PostReviewReqDTOV1;
import com.qring.review.presentation.v1.req.PutReviewReqDTOV1;
import jakarta.validation.Valid;
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
public class ReviewControllerV1 implements ReviewControllerSwagger {

    @PostMapping
    public ResponseEntity<ResDTO<ReviewPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                             @Valid @RequestBody PostReviewReqDTOV1 dto) {

        // -----
        // TODO : 더미데이터입니다.
        ReviewEntity dummyReviewEntity = ReviewEntity.builder()
                .userId(1L)
                .restaurantId(501L)
                .rating(5)
                .content("정말 최고의 음식이었어요!")
                .build();
        // ----- 추후 삭제하시면 됩니다.

        return new ResponseEntity<>(
                ResDTO.<ReviewPostResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("리뷰 생성에 성공했습니다.")
                        .data(ReviewPostResDTOV1.of(dummyReviewEntity))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ReviewSearchResDTOV1>> searchBy(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(name = "userId", required = false) Long userId,
                                                                 @RequestParam(name = "restaurantId", required = false) Long restaurantId,
                                                                 @RequestParam(name = "sort", required = false) String sort) {
        // --
        // TODO : 평점 높은 순, 평점 낮은 순, 최신순, 오래된순 조건 ENUM or 상수 클래스로 관리
        // --


        // -----
        // TODO : 더미데이터입니다. 추후에는 ReviewPage 로 변경하셔야 합니다.

        List<ReviewEntity> dummyReviews = List.of(
                ReviewEntity.builder()
                        .userId(1L)
                        .restaurantId(501L)
                        .rating(5)
                        .content("정말 최고의 음식이었어요!")
                        .build(),
                ReviewEntity.builder()
                        .userId(2L)
                        .restaurantId(502L)
                        .rating(4)
                        .content("맛있었지만 조금 비쌌어요.")
                        .build(),
                ReviewEntity.builder()
                        .userId(3L)
                        .restaurantId(503L)
                        .rating(3)
                        .content("괜찮았지만 기대에는 못 미쳤습니다.")
                        .build()
        );

        Page<ReviewEntity> dummyPage = new PageImpl<>(dummyReviews, pageable, dummyReviews.size());

        // ----- 추후 삭제하시면 됩니다.

        return new ResponseEntity<>(
                ResDTO.<ReviewSearchResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 검색에 성공했습니다.")
                        .data(ReviewSearchResDTOV1.of(dummyPage))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ReviewGetByIdResDTOV1>> getBy(@PathVariable Long id) {

        // -----
        // TODO : 더미데이터입니다.
        ReviewEntity dummyReviewEntity = ReviewEntity.builder()
                .userId(1L)
                .restaurantId(501L)
                .rating(5)
                .content("정말 최고의 음식이었어요!")
                .build();
        // ----- 추후 삭제하시면 됩니다.

        return new ResponseEntity<>(
                ResDTO.<ReviewGetByIdResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 검색에 성공했습니다.")
                        .data(ReviewGetByIdResDTOV1.of(dummyReviewEntity))
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-User-Id") Long userId,
                                                @PathVariable Long id,
                                                @Valid @RequestBody PutReviewReqDTOV1 dto) {

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

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("리뷰 삭제에 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
