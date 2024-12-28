package com.qring.review.infrastructure.docs;

import com.qring.review.domain.v1.res.ResDTO;
import com.qring.review.domain.v1.res.ReviewPostResDTOv1;
import com.qring.review.v1.req.PostReviewReqDTOv1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Review", description = "생성, 조회, 검색, 수정, 삭제 관련 리뷰 API")
public interface ReviewControllerSwagger {

    @Operation(summary = "리뷰 생성", description = "사용자의 Id 와 식당의 Id 를 기준으로 리뷰를 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 생성 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/v1/reviews")
    ResponseEntity<ResDTO<ReviewPostResDTOv1>> postBy(@RequestHeader("X-User-Id") Long userId, @Valid @RequestBody PostReviewReqDTOv1 dto);
}