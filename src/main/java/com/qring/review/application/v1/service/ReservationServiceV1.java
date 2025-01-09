package com.qring.review.application.v1.service;

import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.v1.res.ReservationGetByIdResDTOV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReservationServiceV1 {

    ResponseEntity<ResDTO<ReservationGetByIdResDTOV1.ReservationInfo>> getBy(@PathVariable Long id);
}
