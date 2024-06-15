package com.pollution.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
	public String dataPage(Model model) {
	    LocalDateTime endOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1);
	    LocalDateTime startOfDay = endOfDay.minusDays(1);

	    List<Sensor> sensors = sensorDataRepository.findByCreatedAtBetween(startOfDay, endOfDay);

	    int averageTemperature = (int) sensors.stream()
	                                             .mapToDouble(Sensor::getTemperature)
	                                             .average()
	                                             .orElse(0.0);

	    int averageAqi = (int) sensors.stream()
	                                      .mapToDouble(Sensor::getAqi)
	                                      .average()
	                                      .orElse(0.0);

	    float averageHumidity = (float) sensors.stream()
	                                           .mapToDouble(Sensor::getHumidity)
	                                           .average()
	                                           .orElse(0.0);

	    model.addAttribute("averageTemperature", averageTemperature);
	    model.addAttribute("averageAqi", averageAqi);
	    model.addAttribute("averageHumidity", averageHumidity);

	    return "data.html";
	}


	
	@GetMapping("/map")
	public String mapPage()
	{
		return "map.html";
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
