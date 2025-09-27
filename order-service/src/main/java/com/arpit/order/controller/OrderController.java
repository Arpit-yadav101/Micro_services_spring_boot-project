package com.arpit.order.controller;

import com.arpit.order.OrderServiceApplication;
import com.arpit.order.dto.OrderRequest;
import com.arpit.order.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    
private final OrderService orderService;
public OrderController( OrderService orderService) {
	this.orderService=orderService;
}
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public String placeOrder(@RequestBody OrderRequest orderRequest) {
	
	orderService.placeOrder(orderRequest);
	return "Order Placed Successfully";
}






}
