package com.electronics.store.controller;

import com.electronics.store.model.*;
import com.electronics.store.service.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Дашборд
    @GetMapping
    public String dashboard() {
        return "admin-dashboard";
    }

    // === Управление категориями ===
    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin-categories-list";
    }

    @GetMapping("/categories/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "admin-categories-form";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        CategoryEntity category = adminService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        model.addAttribute("category", category);
        return "admin-categories-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute CategoryEntity category, RedirectAttributes ra) {
        adminService.saveCategory(category);
        ra.addFlashAttribute("success", "Категория сохранена");
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        try {
            adminService.deleteCategory(id);
            ra.addFlashAttribute("success", "Категория удалена");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Нельзя удалить категорию, у которой есть товары или атрибуты");
        }
        return "redirect:/admin/categories";
    }

    // === Управление товарами ===
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", adminService.getAllProducts());
        return "admin-products-list";
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new ProductEntity());
        model.addAttribute("categories", adminService.getAllCategories());
        model.addAttribute("allAttributeTypes", adminService.getAllAttributeTypes());
        return "admin-products-form";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductEntity product = adminService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
        model.addAttribute("product", product);
        model.addAttribute("categories", adminService.getAllCategories());
        model.addAttribute("allAttributeTypes", adminService.getAllAttributeTypes());
        // группируем существующие значения по типам
        model.addAttribute("existingValues", product.getAttributeValues());
        return "admin-products-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute ProductEntity product,
                              @RequestParam(required = false) List<Long> attrTypeIds,
                              @RequestParam(required = false) List<String> attrValues,
                              RedirectAttributes ra) {
        // Сохраняем товар
        ProductEntity saved = adminService.saveProduct(product);

        // Обрабатываем характеристики
        if (attrTypeIds != null && attrValues != null && attrTypeIds.size() == attrValues.size()) {
            List<AttributeValueEntity> attributeValues = new ArrayList<>();
            for (int i = 0; i < attrTypeIds.size(); i++) {
                Long typeId = attrTypeIds.get(i);
                String val = attrValues.get(i);
                if (val != null && !val.isBlank()) {
                    AttributeTypeEntity type = adminService.getAttributeTypeById(typeId)
                            .orElseThrow(() -> new RuntimeException("Тип характеристики не найден"));
                    AttributeValueEntity av = new AttributeValueEntity();
                    av.setAttributeType(type);
                    av.setValue(val);
                    av.setProduct(saved);
                    attributeValues.add(av);
                }
            }
            adminService.updateProductAttributes(saved.getId(), attributeValues);
        }
        ra.addFlashAttribute("success", "Товар сохранён");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes ra) {
        adminService.deleteProduct(id);
        ra.addFlashAttribute("success", "Товар удалён");
        return "redirect:/admin/products";
    }

    // === Управление типами характеристик ===
    @GetMapping("/attribute-types")
    public String listAttributeTypes(Model model) {
        model.addAttribute("attributeTypes", adminService.getAllAttributeTypes());
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin-attributetypes-list";
    }

    @GetMapping("/attribute-types/create")
    public String createAttributeTypeForm(Model model) {
        model.addAttribute("attributeType", new AttributeTypeEntity());
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin-attributetypes-form";
    }

    @GetMapping("/attribute-types/edit/{id}")
    public String editAttributeTypeForm(@PathVariable Long id, Model model) {
        AttributeTypeEntity attrType = adminService.getAttributeTypeById(id)
                .orElseThrow(() -> new RuntimeException("Тип характеристики не найден"));
        model.addAttribute("attributeType", attrType);
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin-attributetypes-form";
    }

    @PostMapping("/attribute-types/save")
    public String saveAttributeType(@ModelAttribute AttributeTypeEntity attributeType, RedirectAttributes ra) {
        adminService.saveAttributeType(attributeType);
        ra.addFlashAttribute("success", "Тип характеристики сохранён");
        return "redirect:/admin/attribute-types";
    }

    @GetMapping("/attribute-types/delete/{id}")
    public String deleteAttributeType(@PathVariable Long id, RedirectAttributes ra) {
        try {
            adminService.deleteAttributeType(id);
            ra.addFlashAttribute("success", "Тип характеристики удалён");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/attribute-types";
    }
}