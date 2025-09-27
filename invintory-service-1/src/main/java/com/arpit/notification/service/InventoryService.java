package com.arpit.notification.service;

import org.springframework.stereotype.Service;

import com.arpit.notification.repository.InventoryRepository;



@Service
public class InventoryService {
	private final InventoryRepository inventoryRepository;
	public InventoryService(InventoryRepository inventoryRepository) {
		this.inventoryRepository=inventoryRepository;
	}
	public boolean isInStock(String skuCode,Integer quantity ) {
		return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode,quantity);
		
	}
	

}