package com.revature.revshop.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.revshop.dto.OrderRequest;
import com.revature.revshop.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	
	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		
		this.orderService = orderService;
	}
	
	@PostMapping
	@CircuitBreaker(name="inventory-service", fallbackMethod = "fallbackResponse")
	@ResponseStatus(HttpStatus.OK)
	@Retry(name = "inventory-service")
	//@TimeLimiter(name="inventory-service")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
		
			
		return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));			
			
		
	}
	
	public CompletableFuture<String>  fallbackResponse(OrderRequest orderRequest, RuntimeException runtimeException) {
		runtimeException.printStackTrace();
		
		return CompletableFuture.supplyAsync(() -> "somthing went wrong, please try to order after sometime.");
		
	}

}
