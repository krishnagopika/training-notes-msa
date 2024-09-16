package com.revature.revshop.dto;

import com.revature.revshop.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {

	private String skuCode;
	private Integer quantity;
	private String userId;
	private Status status;

}
