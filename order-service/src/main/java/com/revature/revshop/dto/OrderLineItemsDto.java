package com.revature.revshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemsDto {
	private Long id;
	private String skuCode;
	private Double price;
	private Integer quantity;

}
