package com.qring.review.infrastructure.messaging;

import com.qring.review.application.v1.message.KafkaMessageProducerV1;
import com.qring.review.application.v1.message.ReviewEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducerImplV1 implements KafkaMessageProducerV1 {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishReviewEvent(ReviewEventMessage reviewEventMessage) {
        // 토픽, 키(식당 ID), 메시지 전달
        kafkaTemplate.send("review-event-topic", String.valueOf(reviewEventMessage.getRestaurantId()), reviewEventMessage);
    }
}