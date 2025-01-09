package com.qring.review.application.v1.res;

import com.qring.review.domain.model.constraint.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationGetByIdResDTOV1 {

    private Reservation reservation;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Reservation {

        private Long userId;
        private Long restaurantId;
        private ReservationStatus status;

    }
}
