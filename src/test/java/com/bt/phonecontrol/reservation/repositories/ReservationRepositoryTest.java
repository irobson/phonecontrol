package com.bt.phonecontrol.reservation.repositories;

import com.bt.phonecontrol.reservation.entities.Device;
import com.bt.phonecontrol.reservation.entities.Reservation;
import com.bt.phonecontrol.reservation.entities.User;
import com.bt.phonecontrol.reservation.repositories.DeviceRepository;
import com.bt.phonecontrol.reservation.repositories.ReservationRepository;
import com.bt.phonecontrol.reservation.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    private Device nokia;

    private User robson;

    private Reservation reservation;

    @Before
    public void setUp() {
        deviceRepository.save(Device.builder().description("Samsung Galaxy S9").build());
        deviceRepository.save(Device.builder().description("Samsung Galaxy S8").build());
        deviceRepository.save(Device.builder().description("Samsung Galaxy S7").build());
        deviceRepository.save(Device.builder().description("Motorola Nexus 6").build());
        deviceRepository.save(Device.builder().description("LG Nexus 5X").build());
        deviceRepository.save(Device.builder().description("Huawei Honor 7X").build());
        deviceRepository.save(Device.builder().description("Apple iPhone X").build());
        deviceRepository.save(Device.builder().description("Apple iPhone 8").build());
        deviceRepository.save(Device.builder().description("Apple iPhone 4s").build());
        this.nokia = deviceRepository.save(Device.builder().description("Nokia 3310").build());

        //create few testing users
        this.robson = userRepository.save(User.builder().name("Robson").build());
        userRepository.save(User.builder().name("Cris").build());
        userRepository.save(User.builder().name("Donna").build());
        userRepository.save(User.builder().name("Messi").build());
        userRepository.save(User.builder().name("Gabriela").build());


        //create a reservation
        this.reservation = Reservation
                .builder()
                .bookedDate(ZonedDateTime.now())
                .device(nokia)
                .user(robson)
                .build();
        reservationRepository.save(reservation);
    }

    @Test
    public void testFindReservationByDevice() {
        assertThat(reservationRepository.findReservationByDeviceIdAndReturnedDateIsNull(nokia.getId()).get(0))
                .isEqualTo(reservation);
        reservation.setReturnedDate(ZonedDateTime.now());
        reservationRepository.save(reservation);
        assertThat(reservationRepository.findReservationByDeviceIdAndReturnedDateIsNull(nokia.getId()))
                .isEmpty();
    }
}
