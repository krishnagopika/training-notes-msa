package com.revature.revshop.event;

import org.springframework.context.ApplicationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent extends ApplicationEvent{
	
	private String orderNumber;
	private String userId;

	public OrderEvent(Object source, String orderNumber, String userId) {
        super(source);
        this.orderNumber = orderNumber;
		this.userId = userId;
    }

}
