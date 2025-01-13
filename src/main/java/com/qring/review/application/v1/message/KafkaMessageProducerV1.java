package com.qring.review.application.v1.message;

public interface KafkaMessageProducerV1 {

    void publishReviewEvent(ReviewEventMessageDTOV1 reviewEventMessage);

}