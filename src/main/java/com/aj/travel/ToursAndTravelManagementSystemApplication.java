package com.aj.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ToursAndTravelManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToursAndTravelManagementSystemApplication.class, args);
    }

}
