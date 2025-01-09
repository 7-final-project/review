package com.qring.review.application.v1.message;

import com.qring.review.application.v1.res.ReviewPostResDTOV1;

public interface KafkaMessageProducerV1 {

    void publishReviewCreateEvent(ReviewPostResDTOV1.ReviewInfo reviewInfo);

}
