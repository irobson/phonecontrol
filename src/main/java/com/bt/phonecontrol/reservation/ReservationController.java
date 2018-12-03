package com.bt.phonecontrol.reservation;

import com.bt.phonecontrol.reservation.entities.Device;
import com.bt.phonecontrol.reservation.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class ReservationController {

    private ReservationService reservationService;

    private ReservationFormatter reservationFormatter;

    /**
     * load the specified device description and send to the details html page
     * which will be responsible to call <b>Fonoapi</b> web-service and show related
     * specification information.
     * @param model exposed model
     * @param id device id
     * @return details html page
     */
    @GetMapping("/device/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Optional<Device> result = reservationService.findDeviceById(id);
        model.addAttribute("device", result.get().getDescription());
        return "details";
    }

    /**
     * list all the devices and show its information regarding reservations
     * @param model exposed model
     * @return related index html page
     */
    @GetMapping("/")
    public String index(Model model) {
        List<ReservationRepresentation> reservationRepresentationList = new ArrayList<>();

        reservationService.findAllDevices().forEach(device -> {

            ReservationRepresentation reservationRepresentation = new ReservationRepresentation();
            reservationRepresentation.setDeviceId(device.getId());
            reservationRepresentation.setDeviceDescription(device.getDescription());
            reservationRepresentation.setAvailable(Boolean.TRUE);

            Reservation reservation = reservationService.findReservationByDevice(device.getId());

            if (reservation != null) {
                reservationRepresentation.setReservationId(reservation.getId());
                reservationRepresentation.setAvailable(Boolean.FALSE);
                reservationRepresentation.setBookedBy(reservation.getUser().getName());
                reservationRepresentation.setBookedDate(reservationFormatter.format(reservation.getBookedDate()));
            }

            reservationRepresentationList.add(reservationRepresentation);
        });
        model.addAttribute("users", reservationService.findAllUsers());

        model.addAttribute("deviceReservations", reservationRepresentationList);
        return "index";
    }

    /**
     * return the device action
     * @param id reservation id
     * @return redirects to index action
     */
    @PostMapping("/reservation/return")
    public String remove(@RequestParam("id") Long id) {
        reservationService.remove(id);
        return "redirect:/";
    }

    /**
     * book the device action
     * @param userId user id
     * @param deviceId device id
     * @return redirects to index action
     */
    @PostMapping("/reservation/book")
    public String addNew(@RequestParam("userId") Long userId, @RequestParam("deviceId") Long deviceId) {
        reservationService.addNew(deviceId, userId);
        return "redirect:/";
    }

}
