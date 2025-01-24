package com.qring.review.application.service;

import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.res.ReservationGetByIdResDTOV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ReservationServiceV1 {

    ResponseEntity<ResDTO<ReservationGetByIdResDTOV1>> getBy(@RequestHeader("X-Passport-Token") String passport, @PathVariable Long id);
}
