package com.pollution.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pollution.model.Sensor;
import com.pollution.repo.ContactRepository;
import com.pollution.repo.SensorDataRepository;

@Controller
public class AdminController {
	
	@Autowired
	private SensorDataRepository sensorDataRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/admin")
	public String adminPage()
	{
		return "admindash.html";
	}
	@GetMapping("/admin/amap")
    public String amapPage() {
        return "amap.html";
    }
	@GetMapping("/admin/asensor")
    public String asensorPage(Model model) {
		 Optional<Sensor> latestSensorData = sensorDataRepository.findTopByOrderByCreatedAtDesc();
	        if (latestSensorData.isPresent()) {
	            model.addAttribute("aqi", latestSensorData.get().getAqi());
	            model.addAttribute("gasPPM", latestSensorData.get().getGasPPM());
	        }
	        return "asensor.html";
        
    }
	@GetMapping("/admin/hsensor")
    public String hsensorPage(Model model) {
		  Optional<Sensor> latestSensorData = sensorDataRepository.findTopByOrderByCreatedAtDesc();
	        if (latestSensorData.isPresent()) {
	            model.addAttribute("humidity", latestSensorData.get().getHumidity());
	        }
	        
        return "hsensor.html";
    }
	
	
	@GetMapping("/admin/tsensor")
    public String tsensorPage(Model model) {
		 Optional<Sensor> latestSensorData = sensorDataRepository.findTopByOrderByCreatedAtDesc();
	        if (latestSensorData.isPresent()) {
	            model.addAttribute("temperature", latestSensorData.get().getTemperature());
	        }
        return "tsensor.html";
    }
	 @GetMapping("/admin/afeed")
	    public String afeedPage(Model model) {
	        model.addAttribute("contact", contactRepository.findAll());
	        return "afeed.html";
	    }
	 

}
