package com.pollution.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pollution.model.Sensor;

public interface SensorDataRepository extends JpaRepository<Sensor, Integer>{
	 Optional<Sensor> findTopByOrderByCreatedAtDesc();

	List<Sensor> findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
