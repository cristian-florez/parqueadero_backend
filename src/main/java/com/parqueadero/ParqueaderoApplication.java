package com.parqueadero;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ParqueaderoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParqueaderoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
	}

}