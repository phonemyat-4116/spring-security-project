package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.WeatherLog;
import com.codesnippet.weather_service.repository.WeatherLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class WeatherLogService {

    @Autowired
    private WeatherLogRepository weatherLogRepository;

    @PostAuthorize("returnObject.performedBy == authentication.name")
    public WeatherLog getLogById(Long id) {
        return weatherLogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Log not found"));
    }

    public WeatherLog createLog(WeatherLog log) {
        return weatherLogRepository.save(log);
    }
}
