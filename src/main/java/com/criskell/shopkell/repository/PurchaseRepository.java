package com.criskell.shopkell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criskell.shopkell.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
