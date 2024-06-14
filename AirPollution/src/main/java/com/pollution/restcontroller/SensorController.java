package com.pollution.restcontroller;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pollution.model.Sensor;
import com.pollution.repo.SensorDataRepository;

@RestController
public class SensorController {
	 @Autowired
	    private SensorDataRepository sensorDataRepository;

	    @PostMapping("/sendData")
	    public ResponseEntity<String> receiveSensorData(@RequestBody Sensor request) {
	        try {
	            Sensor sensorData = new Sensor();
	            sensorData.setGas(request.getGas());
	            sensorData.setGasPPM(request.getGasPPM());
	            sensorData.setAqi(request.getAqi());
	            sensorData.setSound(request.getSound());
	            sensorData.setTemperature(request.getTemperature());
	            sensorData.setHumidity(request.getHumidity());
	            sensorData.setLatitude(request.getLatitude());
	            sensorData.setLongitude(request.getLongitude());
	            sensorDataRepository.save(sensorData);
	            return new ResponseEntity<>("Sensor data saved successfully", HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>("Failed to save sensor data", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
