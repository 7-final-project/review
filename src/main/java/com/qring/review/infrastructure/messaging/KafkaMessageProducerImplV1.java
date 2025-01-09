package com.qring.review.infrastructure.messaging;

import com.qring.review.application.v1.message.KafkaMessageProducerV1;
import com.qring.review.application.v1.res.ReviewPostResDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducerImplV1 implements KafkaMessageProducerV1 {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishReviewCreateEvent(ReviewPostResDTOV1.ReviewInfo reviewInfo) {


        kafkaTemplate.send("review-create-event-topic", reviewInfo);
    }
}
