package com.bt.phonecontrol.reservation;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationFormatter {

    private static final String PATTERN = "MM/dd/yyyy - HH:mm:ss Z";

    public String format(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        String formattedString = zonedDateTime.format(formatter);
        return formattedString;
    }

}
