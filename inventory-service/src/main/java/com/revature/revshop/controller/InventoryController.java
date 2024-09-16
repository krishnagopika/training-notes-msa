package com.revature.revshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revshop.dto.InventoryRequest;
import com.revature.revshop.dto.InventoryResponse;
import com.revature.revshop.dto.OrderLineItemsDto;
import com.revature.revshop.service.InventoryService;

@RestController
@RequestMapping("/api/inventory/")
public class InventoryController {
	
	
	private final InventoryService inventoryService;
	
	@Autowired
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
		
	}
	
	
	@PostMapping
	public ResponseEntity<InventoryResponse> createInventoryEntry(@RequestBody InventoryRequest inventoryRequest) {
		
		return new ResponseEntity<>(inventoryService.createInventoryEntry(inventoryRequest), HttpStatus.CREATED);
		
	}
	
	@PostMapping("instock/")
	public ResponseEntity<List<Boolean>> areItemsInStock(@RequestBody List<OrderLineItemsDto> orderLineItems){
		
		return new ResponseEntity<>(inventoryService.areItemsInStock(orderLineItems), HttpStatus.OK);
		
	}
	
	
	
	
	
	

}
