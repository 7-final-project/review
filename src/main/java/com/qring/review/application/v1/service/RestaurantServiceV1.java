package com.qring.review.application.v1.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface RestaurantServiceV1 {

    boolean existsBy(@PathVariable Long id);

}
