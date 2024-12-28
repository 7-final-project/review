package com.qring.review.infrastructure.docs;

import com.qring.review.domain.v1.res.ResDTO;
import com.qring.review.domain.v1.res.ReviewGetByIdResDTOv1;
import com.qring.review.domain.v1.res.ReviewPostResDTOv1;
import com.qring.review.domain.v1.res.ReviewSearchResDTOv1;
import com.qring.review.v1.req.PostReviewReqDTOv1;
import com.qring.review.v1.req.PutReviewReqDTOv1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "생성, 조회, 검색, 수정, 삭제 관련 리뷰 API")
public interface ReviewControllerSwagger {

    @Operation(summary = "리뷰 생성", description = "사용자의 Id 와 식당의 Id 를 기준으로 리뷰를 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 생성 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/v1/reviews")
    ResponseEntity<ResDTO<ReviewPostResDTOv1>> postBy(@RequestHeader("X-User-Id") Long userId, @Valid @RequestBody PostReviewReqDTOv1 dto);


    @Operation(summary = "리뷰 검색", description = "동적 조건은 기준으로 리뷰를 검색하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 검색 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 생성 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @GetMapping("/v1/reviews")
    ResponseEntity<ResDTO<ReviewSearchResDTOv1>> searchBy(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(name = "userId", required = false) Long userId,
                                                                 @RequestParam(name = "restaurantId", required = false) Long restaurantId,
                                                                 @RequestParam(name = "sort", required = false) String sort);

    @Operation(summary = "리뷰 상세 조회", description = "리뷰 ID 를 기준으로 리뷰를 상세 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 조회 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 조회 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    ResponseEntity<ResDTO<ReviewGetByIdResDTOv1>> getBy(@PathVariable Long reviewId);


    @Operation(summary = "리뷰 수정", description = "사용자의 Id 와 리뷰의 Id 를 기준으로 리뷰를 수정하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 수정 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PutMapping("/v1/reviews/{reviewId}")
    ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-User-Id") Long userId, @PathVariable Long reviewId, @Valid @RequestBody PutReviewReqDTOv1 dto);


    @Operation(summary = "리뷰 삭제", description = "사용자의 Id 와 리뷰 Id 를 기준으로 리뷰를 삭제하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 삭제 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @DeleteMapping("/v1/reviews/{reviewId}")
    ResponseEntity<ResDTO<Object>> deleteBy(@RequestHeader("X-User-Id") Long userId, @PathVariable Long reviewId);
}
