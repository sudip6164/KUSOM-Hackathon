package com.pollution.controller;

import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pollution.repo.SensorDataRepository;
import com.pollution.model.Sensor;

@Controller
public class PollutionController {
	@Autowired
	private SensorDataRepository sensorDataRepository;
	
    @GetMapping("/")
    public String frontPage(Model model) {
        OptionalDouble averageAqi = sensorDataRepository.findAll().stream()
            .mapToDouble(Sensor::getAqi)
            .average();
        
        if (averageAqi.isPresent()) {
            model.addAttribute("averageAqi", String.format("%.2f", averageAqi.getAsDouble()));
        } else {
            model.addAttribute("averageAqi", "N/A");
        }
        
        return "index.html";
    }
	
	@GetMapping("/about")
	public String aboutPage()
	{
		return "about.html";
	}
	
	@GetMapping("/data")
	public String dataPage()
	{
		return "data.html";
	}
	
	@GetMapping("/map")
	public String mapPage()
	{
		return "map.html";
	}
	
	@GetMapping("/contact")
	public String contactPage()
	{
		return "contact.html";
	}
	
	@GetMapping("/airdetails")
    public String airdetailsPage(Model model) {
        Optional<Sensor> latestSensorData = sensorDataRepository.findTopByOrderByCreatedAtDesc();
        if (latestSensorData.isPresent()) {
            model.addAttribute("aqi", latestSensorData.get().getAqi());
            model.addAttribute("gasPPM", latestSensorData.get().getGasPPM());
        }
        return "airdetails.html";
    }
	
	@GetMapping("/humidity")
    public String humidityPage(Model model) {
        Optional<Sensor> latestSensorData = sensorDataRepository.findTopByOrderByCreatedAtDesc();
        if (latestSensorData.isPresent()) {
            model.addAttribute("humidity", latestSensorData.get().getHumidity());
        }
        return "humidity.html";
    }
	
	@GetMapping("/temperature")
    public String temperaturePage(Model model) {
        Optional<Sensor> latestSensorData = sensorDataRepository.findTopByOrderByCreatedAtDesc();
        if (latestSensorData.isPresent()) {
            model.addAttribute("temperature", latestSensorData.get().getTemperature());
        }
        return "temperature.html";
    }
	
}
