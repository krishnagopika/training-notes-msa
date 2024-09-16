package com.revature.revshop.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEvent {
	
	private String orderNumber;
	private String userId;

}
