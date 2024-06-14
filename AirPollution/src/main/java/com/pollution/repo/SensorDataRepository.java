package com.pollution.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pollution.model.Sensor;

public interface SensorDataRepository extends JpaRepository<Sensor, Integer>{

}
