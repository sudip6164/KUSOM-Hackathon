package com.pollution.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sensor")
public class Sensor {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float gas;
    private float gasPPM;
    private float aqi;
    private int sound;
    private float temperature;
    private float humidity;
    private String latitude;
    private String longitude;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public float getGas() {
		return gas;
	}
	public void setGas(float gas) {
		this.gas = gas;
	}
	
	public float getGasPPM() {
		return gasPPM;
	}
	public void setGasPPM(float gasPPM) {
		this.gasPPM = gasPPM;
	}
	public float getAqi() {
		return aqi;
	}
	
	public void setAqi(float aqi) {
		this.aqi = aqi;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public int getSound() {
		return sound;
	}
	public void setSound(int sound) {
		this.sound = sound;
	}
	
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
