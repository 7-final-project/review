package com.qring.review.infrastructure.client;

import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.v1.res.ReservationGetByIdResDTOV1;
import com.qring.review.application.v1.service.ReservationServiceV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservation-service")
public interface ReservationClient extends ReservationServiceV1 {

    @GetMapping("/v1/reservations/{id}/review")
    ResponseEntity<ResDTO<ReservationGetByIdResDTOV1.ReservationInfo>> getBy(@PathVariable("id") Long id);
}
