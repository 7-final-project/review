package com.qring.review.application.v1.service;

import com.qring.review.application.global.dto.ResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface RestaurantServiceV1 {

    ResponseEntity<ResDTO<Object>> getBy(@PathVariable Long id);

}
