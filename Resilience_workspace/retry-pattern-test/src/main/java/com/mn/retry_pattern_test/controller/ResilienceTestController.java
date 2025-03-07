package com.mn.retry_pattern_test.controller;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.retry.annotation.Retry;



@RestController
public class ResilienceTestController {

	private static final Logger logger = LoggerFactory.getLogger(ResilienceTestController.class);


	@Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() throws TimeoutException {
		logger.debug("getBuildInfo() method Invoked");
		throw new TimeoutException();
		//return ResponseEntity.status(HttpStatus.OK).body("1.0");
	}

	public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
		logger.debug("getBuildInfoFallback() method Invoked");
		return ResponseEntity.status(HttpStatus.OK).body("0.9");
	}
}
