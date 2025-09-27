package com.arpit.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arpit.order.model.Order;

public interface  OrderRepository extends JpaRepository<Order ,Long> {

}
