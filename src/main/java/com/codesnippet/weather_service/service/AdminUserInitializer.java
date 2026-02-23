package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.Roles;
import com.codesnippet.weather_service.entity.Users;
import com.codesnippet.weather_service.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userDetailsRepository.findByUsername("admin").isEmpty()) {
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Roles.ADMIN);

                userDetailsRepository.save(admin);
                System.out.println("Default ADMIN User created");
            }

            if(userDetailsRepository.findByUsername("user").isEmpty()) {
                Users user = new Users();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole(Roles.USER);

                userDetailsRepository.save(user);
                System.out.println("Default USER User created");
            }
        };
    }
}
