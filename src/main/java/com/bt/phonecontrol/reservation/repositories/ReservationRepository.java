package com.bt.phonecontrol.reservation.repositories;

import com.bt.phonecontrol.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByDeviceIdAndReturnedDateIsNull(Long deviceId);

}
