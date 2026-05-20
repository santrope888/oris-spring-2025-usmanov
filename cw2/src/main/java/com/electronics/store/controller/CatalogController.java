package com.electronics.store.controller;

import com.electronics.store.model.AttributeTypeEntity;
import com.electronics.store.model.CategoryEntity;
import com.electronics.store.model.ProductEntity;
import com.electronics.store.service.impl.CatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public String listCategories(Model model, Authentication auth) {
        List<CategoryEntity> categories = catalogService.getAllCategories();
        addAuthAttributes(model, auth);
        model.addAttribute("categories", categories);
        return "categories"; // шаблон со списком разделов
    }

    @GetMapping("/{categorySlug}")
    public String showProducts(@PathVariable String categorySlug,
                               @RequestParam(required = false) Map<String, String> allParams,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "12") int size,
                               @RequestParam(defaultValue = "name,asc") String sort,
                               Model model,
                               Authentication auth) {

        // Разбор параметров фильтрации: ожидаем параметры вида attr_<id>=value1,value2
        Map<Long, List<String>> filters = new HashMap<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("attr_")) {
                Long attrId = Long.parseLong(entry.getKey().substring(5));
                String[] values = entry.getValue().split(",");
                filters.put(attrId, Arrays.asList(values));
            }
        }

        // Сортировка
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));

        // Получаем данные
        CategoryEntity category = catalogService.getCategoryBySlug(categorySlug)
                .orElseThrow(() -> new RuntimeException("Категория " + categorySlug + " не найдена"));
        Page<ProductEntity> productPage = catalogService.getProductsByCategory(categorySlug, filters, pageable);
        Map<AttributeTypeEntity, List<String>> filterOptions = catalogService.getAvailableFiltersForCategory(categorySlug);

        Map<String, List<String>> selectedFiltersStr = new LinkedHashMap<>();
        for (Map.Entry<Long, List<String>> entry : filters.entrySet()) {
            selectedFiltersStr.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        model.addAttribute("selectedFilters", selectedFiltersStr);
        addAuthAttributes(model, auth);
        model.addAttribute("category", category);
        model.addAttribute("products", productPage);
        model.addAttribute("filterOptions", filterOptions);
        //model.addAttribute("selectedFilters", filters);
        model.addAttribute("currentSort", sort);
        return "products";
    }

    private void addAuthAttributes(Model model, Authentication auth) {
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"));
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated) {
            model.addAttribute("username", auth.getName());
        }
    }
}