package com.criskell.shopkell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.criskell.shopkell.entity.Purchase;
import com.criskell.shopkell.service.PurchaseService;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        model.addAttribute("total", purchaseService.getTotalPurchases());
        return "purchases/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        return "purchases/add";
    }

    @PostMapping
    public String add(Purchase purchase, RedirectAttributes redirect) {
        purchaseService.add(purchase);
        redirect.addFlashAttribute("message", "Compra adicionada com sucesso!");
        return "redirect:/purchases";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        var purchase = purchaseService.findById(id).orElseThrow(() -> new RuntimeException("Compra não encontrada"));
        model.addAttribute("purchase", purchase);
        return "purchases/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, Purchase purchase, RedirectAttributes redirect) {
        purchaseService.update(id, purchase);
        redirect.addFlashAttribute("message", "Compra editada com sucesso!");
        return "redirect:/purchases";
    }

    @PostMapping("/{id}/mark-as-done")
    public String markAsDone(@PathVariable Long id) {
        purchaseService.markAsDone(id);

        return "redirect:/purchases";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        purchaseService.delete(id);
        return "redirect:/purchases";
    }
}
