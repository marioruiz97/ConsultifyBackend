package com.asisge.consultifybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ConsultifyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultifyBackendApplication.class, args);
    }

}
