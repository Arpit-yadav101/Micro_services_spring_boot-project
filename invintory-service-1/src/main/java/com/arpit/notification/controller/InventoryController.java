package com.arpit.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.arpit.notification.service.InventoryService;



@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    
	private final InventoryService inventoryService;
	 public InventoryController(InventoryService inventoryService) {
		 this.inventoryService = inventoryService;
	 }
	 @GetMapping
	 @ResponseStatus(HttpStatus.OK)
	 public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
	 return inventoryService.isInStock(skuCode, quantity);	 
		 
		 
		 
		 
	 }
	 
	 

}