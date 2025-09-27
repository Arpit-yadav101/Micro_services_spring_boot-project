package com.arpit.order.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.arpit.order.client.InventoryClient;
import com.arpit.order.dto.OrderRequest;
import com.arpit.order.event.OrderPlacedEvent;
import com.arpit.order.model.Order;
import com.arpit.order.repository.OrderRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate; 
	
	 public OrderService(OrderRepository orderRepository,KafkaTemplate kafkaTemplate) {
		 this.orderRepository=orderRepository;
		this.kafkaTemplate = kafkaTemplate;
		}
	 
	 @Autowired
	 private  InventoryClient inventoryClient;
	 
	
	

	public void placeOrder(OrderRequest orderRequest) {
		var isProductInStock =inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());
				if(isProductInStock) {
		Order order=new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setSkuCode(orderRequest.skuCode());
		order.setPrice(orderRequest.price());
		order.setQuantity(orderRequest.quantity());
		orderRepository.save(order);
		OrderPlacedEvent orderPlacedEvent=new OrderPlacedEvent(order.getOrderNumber(),orderRequest.userDetails().email());
		log.info("Start-Sending OrderPlacedEvent{} to Kafka topic order-placed ",orderPlacedEvent);
		
		kafkaTemplate.send("order-placed",orderPlacedEvent);
		log.info("End-Sending OrderPlacedEvent{} to Kafka topic order-placed ",orderPlacedEvent);
		
				}else {
				throw new RuntimeException("Product with skuCode "+orderRequest.skuCode()+"is not in stock   ");	
					
				}
		
		
	}
	

}
