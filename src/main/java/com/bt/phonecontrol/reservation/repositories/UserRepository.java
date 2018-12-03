package com.bt.phonecontrol.reservation.repositories;

import com.bt.phonecontrol.reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
