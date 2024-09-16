package com.scdt.shorturl.distributed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {
	private final HealthService healthService;
	public HealthCheckController(HealthService healthService) {
		this.healthService = healthService;
	}
	@GetMapping("/instances")
	public Mono<List<Map<String, String>>> get(){
		return Mono.just(healthService.getInstances());
	}
	@GetMapping("/role")
	public Mono<String> getRole(){
		return Mono.just(healthService.getRole());
	}
	@GetMapping("/start-time")
	public Mono<String> getStartTime(){
		return Mono.just(healthService.getStartUpTime());
	}
}
