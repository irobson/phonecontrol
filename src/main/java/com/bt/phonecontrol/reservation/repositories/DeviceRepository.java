package com.bt.phonecontrol.reservation.repositories;

import com.bt.phonecontrol.reservation.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
