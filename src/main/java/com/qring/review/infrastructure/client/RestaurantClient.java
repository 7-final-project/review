package com.qring.review.infrastructure.client;

import com.qring.review.application.v1.service.RestaurantService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient extends RestaurantService {

    @GetMapping("/v1/restaurants/{id}/exists")
    boolean existsBy(@PathVariable("id") Long id);
}
