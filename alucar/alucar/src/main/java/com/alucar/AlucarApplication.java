package com.alucar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.alucar") // <--- Adicione esta linha
public class AlucarApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlucarApplication.class, args);
    }
}