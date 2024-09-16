package com.revature.revshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.revshop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
