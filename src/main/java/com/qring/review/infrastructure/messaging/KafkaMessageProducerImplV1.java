package com.qring.review.infrastructure.messaging;

import com.qring.review.application.message.KafkaMessageProducerV1;
import com.qring.review.application.message.ReviewEventMessageDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducerImplV1 implements KafkaMessageProducerV1 {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishReviewEvent(ReviewEventMessageDTOV1 reviewEventMessage) {
        // 토픽 이름: review-event-topic
        // 메시지 키(파티션 id로 사용됨): 식당 ID
        // 메시지: 리뷰 정보
        kafkaTemplate.send("review-event-topic", String.valueOf(reviewEventMessage.getRestaurantId()), reviewEventMessage);
    }
}