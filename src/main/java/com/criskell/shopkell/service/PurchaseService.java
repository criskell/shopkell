package com.criskell.shopkell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.criskell.shopkell.entity.Purchase;
import com.criskell.shopkell.repository.PurchaseRepository;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase add(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase update(Long id, Purchase updatedPurchase) {
        return purchaseRepository.findById(id)
                .map(existingPurchase -> {
                    existingPurchase.setProductName(updatedPurchase.getProductName());
                    existingPurchase.setQuantity(updatedPurchase.getQuantity());
                    existingPurchase.setIsDone(updatedPurchase.getIsDone());
                    return purchaseRepository.save(existingPurchase);
                })
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public void delete(Long id) {
        purchaseRepository.deleteById(id);
    }

    public void markAsDone(Long id) {
        var purchase = purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
        purchase.setIsDone(true);

        purchaseRepository.save(purchase);
    }

    public long getTotalPurchases() {
        return purchaseRepository.count();
    }
}
