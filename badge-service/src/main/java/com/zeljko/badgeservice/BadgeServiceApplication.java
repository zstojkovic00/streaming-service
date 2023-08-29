package com.zeljko.badgeservice;

import com.zeljko.badgeservice.dto.VideoProgressMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class BadgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadgeServiceApplication.class, args);
	}



}
