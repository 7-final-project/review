package com.qring.review.infrastructure.client;

import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.v1.service.RestaurantServiceV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient extends RestaurantServiceV1 {

    @GetMapping("/v1/restaurants/{id}")
    ResponseEntity<ResDTO<Object>> getBy(@PathVariable("id") Long id);
}
