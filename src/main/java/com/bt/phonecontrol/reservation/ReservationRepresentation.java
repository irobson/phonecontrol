package com.bt.phonecontrol.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRepresentation {
    private Long deviceId;
    private String deviceDescription;
    private Long reservationId;
    private Boolean available;
    private String bookedBy;
    private String bookedDate;
}
