package com.qring.review.application.v1.message;

public interface KafkaMessageProducerV1 {

    void publishReviewEvent(ReviewEventMessage reviewEventMessage);

}