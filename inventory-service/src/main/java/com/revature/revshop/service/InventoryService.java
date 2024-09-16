package com.revature.revshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revshop.dto.InventoryRequest;
import com.revature.revshop.dto.InventoryResponse;
import com.revature.revshop.dto.OrderLineItemsDto;
import com.revature.revshop.model.Inventory;
import com.revature.revshop.model.Status;
import com.revature.revshop.repository.InventoryRepository;

@Service
public class InventoryService {
	
	private final InventoryRepository inventoryRepository;
	
	
	@Autowired
	public InventoryService(InventoryRepository inventoryRepository) {
		
		this.inventoryRepository = inventoryRepository;
		
	}
	
	public Inventory mapToInventory(InventoryRequest inventoryRequest) {
		return Inventory.builder()
				.quantity(inventoryRequest.getQuantity())
				.skuCode(inventoryRequest.getSkuCode())
				.status(inventoryRequest.getStatus())
				.userId(inventoryRequest.getUserId())
				.build();
	}
	
	public InventoryResponse mapToInventoryResponse(Inventory inventory) {
		return InventoryResponse.builder()
				.id(inventory.getId())
				.quantity(inventory.getQuantity())
				.skuCode(inventory.getSkuCode())
				.status(inventory.getStatus())
				.userId(inventory.getUserId())
				.build();
				
	}


	public InventoryResponse createInventoryEntry(InventoryRequest inventoryRequest) {
		
		Inventory inventory = inventoryRepository.save(mapToInventory(inventoryRequest));
		return mapToInventoryResponse(inventory);
	}

	public List<Boolean> areItemsInStock(List<OrderLineItemsDto> orderLineItems) {
		
		List<Boolean> itemsInSock = new ArrayList<>();
		
		for(OrderLineItemsDto olidto: orderLineItems) {
			
			Boolean status = inventoryRepository.findInventoryBySkuCode(olidto.getSkuCode()).getStatus().equals(Status.AVAILABLE);
			
			itemsInSock.add(status);
			
		}
		
		return itemsInSock;
	}
	
	
	
}
