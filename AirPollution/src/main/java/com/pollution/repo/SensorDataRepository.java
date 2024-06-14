package com.pollution.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pollution.model.Sensor;

public interface SensorDataRepository extends JpaRepository<Sensor, Integer>{
	 Optional<Sensor> findTopByOrderByCreatedAtDesc();
}
