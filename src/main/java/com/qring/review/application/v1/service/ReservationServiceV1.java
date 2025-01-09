package com.qring.review.application.v1.service;

import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.v1.res.ReservationGetByIdResDTOV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ReservationServiceV1 {

    ResponseEntity<ResDTO<ReservationGetByIdResDTOV1>> getBy(@RequestHeader("X-Passport-Token") String passport, @PathVariable Long id);
}
