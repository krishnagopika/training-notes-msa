package com.revature.revshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.revshop.model.OrderLineItems;


@Repository
public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long>{

}
