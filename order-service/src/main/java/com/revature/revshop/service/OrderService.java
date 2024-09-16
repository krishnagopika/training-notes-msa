package com.revature.revshop.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.revshop.dto.OrderLineItemsDto;
import com.revature.revshop.dto.OrderRequest;
import com.revature.revshop.event.OrderEvent;
import com.revature.revshop.model.Order;
import com.revature.revshop.model.OrderLineItems;
import com.revature.revshop.repository.OrderLineItemsRepository;
import com.revature.revshop.repository.OrderRepository;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final OrderLineItemsRepository orderLineItemsRepository;
	
	private final WebClient webClient;
	
	private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
	
	@Autowired
	public OrderService(OrderRepository orderRepository, OrderLineItemsRepository orderLineItemsRepository, WebClient webClient, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
		this.orderRepository=orderRepository;
		this.orderLineItemsRepository= orderLineItemsRepository;
		this.webClient = webClient;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
		
		return OrderLineItems.builder()
				.price(orderLineItemsDto.getPrice())
				.quantity(orderLineItemsDto.getQuantity())
				.skuCode(orderLineItemsDto.getSkuCode())
				.build();
		 
		
	}

	public String placeOrder(OrderRequest orderRequest) {
		
		
		List<Boolean> itemsInStock = itemsInStock(orderRequest.getOrderLineItems());
		
		
		for(Boolean inStock: itemsInStock) {
			
			if(inStock==false) {
				
				 return "items not in stock";
				
			}
		}
		
		
		
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderLineItems(
		orderRequest.getOrderLineItems().stream()
		.map( this::mapToOrderLineItems).toList());
		order.setUserId(orderRequest.getUserId());
		
		orderRepository.save(order);
		
		
		kafkaTemplate.send("notificationTopic", new OrderEvent( this,  order.getOrderNumber(), order.getUserId()));
		
		System.out.println("notofocation sent");
		
		return "order placed successfully";
		
		
	}
	
	public List<Boolean> itemsInStock(List<OrderLineItemsDto> list) {
		
		try {
			
			Mono<List<Boolean>> itemStatus = webClient.post()
					.uri("instock/")
					.bodyValue(list)
					.retrieve()
					.bodyToMono( new ParameterizedTypeReference<List<Boolean>>() {});
			
			return itemStatus.block();
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return null;
			
		}
		
		
		
	}

}
