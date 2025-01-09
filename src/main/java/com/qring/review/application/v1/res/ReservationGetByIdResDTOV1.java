package com.qring.review.application.v1.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationGetByIdResDTOV1 {

    private ReservationInfo reservationInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationInfo {

        private Long userId;
        private Long restaurantId;
        private String status;

    }
}
