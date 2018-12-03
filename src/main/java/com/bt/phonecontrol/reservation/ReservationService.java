package com.bt.phonecontrol.reservation;

import com.bt.phonecontrol.reservation.entities.Device;
import com.bt.phonecontrol.reservation.entities.Reservation;
import com.bt.phonecontrol.reservation.entities.User;
import com.bt.phonecontrol.reservation.repositories.DeviceRepository;
import com.bt.phonecontrol.reservation.repositories.ReservationRepository;
import com.bt.phonecontrol.reservation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Robson_Farias
 * responsible to control all the facade methods which handles the behavior related to do/undo reservations
 */
@Service
@AllArgsConstructor
@Slf4j
public class ReservationService {
    private final DeviceRepository deviceRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    /**
     * virtually removes an association between a device and an user setting the 'returned date'
     * field. The record is not deleted in order to keep the history.
     *
     * @param id reservation id
     */
    @Transactional
    public void remove(Long id) {
        Optional<Reservation> result = reservationRepository.findById(id);
        if (!result.isPresent()) {
            log.error("Can´t find the reservation with id: {}", id);
            return;
        }
        Reservation reservation = result.get();
        reservation.setReturnedDate(ZonedDateTime.now());
        reservationRepository.save(reservation);
    }

    /**
     * create a new association between the given device and given user by adding a new record to
     * reservation table assuming the booked date as the current date.
     *
     * @param deviceId device id
     * @param userId   user id
     */
    @Transactional
    public void addNew(Long deviceId, Long userId) {

        Optional<Device> device = deviceRepository.findById(deviceId);
        if (!device.isPresent()) {
            log.error("Can´t find device with id: {}", deviceId);
            return;
        }

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            log.error("Can´t find user with id: {}", userId);
            return;
        }

        Reservation reservation = Reservation
                .builder()
                .bookedDate(ZonedDateTime.now())
                .device(device.get())
                .user(user.get())
                .build();

        reservationRepository.save(reservation);
    }

    /**
     * fetch all devices
     *
     * @return device list
     */
    @Transactional(readOnly = true)
    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    /**
     * fetch all users
     *
     * @return user list
     */
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * find only 'open' reservations i.e. the ones without 'returned date' for a given device id
     *
     * @param deviceId device id
     * @return the first 'open' reservation found
     */
    @Transactional(readOnly = true)
    public Reservation findReservationByDevice(Long deviceId) {
        List<Reservation> reservations = reservationRepository.findReservationByDeviceIdAndReturnedDateIsNull(deviceId);

        if (reservations.isEmpty()) {
            return null;
        }

        if (reservations.size() > 1) {
            log.warn("Found multiple open reservations for the same device id: {}", deviceId);
        }

        return reservations.get(0);

    }

    /**
     * find a device by its id
     *
     * @param id device id
     * @return an optional result with/without a valid device
     */
    @Transactional(readOnly = true)
    public Optional<Device> findDeviceById(Long id) {
        return deviceRepository.findById(id);
    }
}
