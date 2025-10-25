package com.nlu.tai_lieu_xanh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaiLieuXanhApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaiLieuXanhApplication.class, args);
    }
}
