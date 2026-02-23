package com.codesnippet.weather_service.repository;

import com.codesnippet.weather_service.entity.WeatherLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLogRepository extends JpaRepository<WeatherLog, Long> {
}
