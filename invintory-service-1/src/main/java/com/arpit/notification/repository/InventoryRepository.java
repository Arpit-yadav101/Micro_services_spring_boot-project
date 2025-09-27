package com.arpit.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arpit.notification.model.Inventory;



public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);
}

