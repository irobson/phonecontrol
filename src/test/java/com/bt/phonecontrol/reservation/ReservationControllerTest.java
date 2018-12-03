package com.bt.phonecontrol.reservation;

import com.bt.phonecontrol.reservation.entities.Device;
import com.bt.phonecontrol.reservation.entities.Reservation;
import com.bt.phonecontrol.reservation.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ReservationService reservationService;

    @Test
    public void shouldRenderizeIndexPage() throws Exception {
        List<Device> devices = new ArrayList<>();
        devices.add(Device.builder().id(1L).description("test").build());
        devices.add(Device.builder().id(2L).description("test2").build());

        when(reservationService.findAllDevices()).thenReturn(devices);

        Reservation reservation = Reservation
                .builder()
                .bookedDate(ZonedDateTime.now())
                .device(Device.builder().id(2L).description("test2").build())
                .user(User.builder().id(1L).name("Robson").build())
                .build();
        when(reservationService.findReservationByDevice(2L)).thenReturn(reservation);
        when(reservationService.findReservationByDevice(1L)).thenReturn(null);


        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).name("Robson").build());

        when(reservationService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("users", hasSize(1)))
                .andExpect(model().attribute("deviceReservations", hasSize(2)));

        verify(reservationService, times(1)).findAllDevices();

    }
}
