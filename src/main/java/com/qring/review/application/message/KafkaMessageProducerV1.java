package com.qring.review.application.message;

public interface KafkaMessageProducerV1 {

    void publishReviewEvent(ReviewEventMessageDTOV1 reviewEventMessage);

}