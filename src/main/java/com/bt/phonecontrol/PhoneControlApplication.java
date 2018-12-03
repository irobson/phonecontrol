package com.bt.phonecontrol;

import com.bt.phonecontrol.reservation.entities.Device;
import com.bt.phonecontrol.reservation.repositories.DeviceRepository;
import com.bt.phonecontrol.reservation.entities.Reservation;
import com.bt.phonecontrol.reservation.repositories.ReservationRepository;
import com.bt.phonecontrol.reservation.entities.User;
import com.bt.phonecontrol.reservation.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZonedDateTime;

@SpringBootApplication
@Slf4j
public class PhoneControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneControlApplication.class);
    }

    /**
     * initialize the in-memory H2 database with specified device list and few dummy users for post usage
     * as well as creates a reservation for demonstration purpose.
     * @param deviceRepository
     * @param userRepository
     * @param reservationRepository
     * @return
     */
    @Bean
    public CommandLineRunner init(DeviceRepository deviceRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        return (args) -> {
            //create the default devices
            deviceRepository.save(Device.builder().description("Samsung Galaxy S9").build());
            deviceRepository.save(Device.builder().description("Samsung Galaxy S8").build());
            deviceRepository.save(Device.builder().description("Samsung Galaxy S7").build());
            deviceRepository.save(Device.builder().description("Motorola Nexus 6").build());
            deviceRepository.save(Device.builder().description("LG Nexus 5X").build());
            deviceRepository.save(Device.builder().description("Huawei Honor 7X").build());
            deviceRepository.save(Device.builder().description("Apple iPhone X").build());
            deviceRepository.save(Device.builder().description("Apple iPhone 8").build());
            deviceRepository.save(Device.builder().description("Apple iPhone 4s").build());
            Device nokia = deviceRepository.save(Device.builder().description("Nokia 3310").build());

            //create few testing users
            User robson = userRepository.save(User.builder().name("Robson").build());
            userRepository.save(User.builder().name("Cris").build());
            userRepository.save(User.builder().name("Donna").build());
            userRepository.save(User.builder().name("Messi").build());
            userRepository.save(User.builder().name("Gabriela").build());


            //create a reservation
            Reservation reservation = Reservation
                    .builder()
                    .bookedDate(ZonedDateTime.now())
                    .device(nokia)
                    .user(robson)
                    .build();
            reservationRepository.save(reservation);
        };
    }
}
