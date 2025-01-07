package com.qring.review.application.v1.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface RestaurantService {

    boolean existsBy(@PathVariable Long id);

}
