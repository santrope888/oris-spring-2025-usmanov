package com.electronics.store.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Long, CartItem> items = new LinkedHashMap<>();

    public Map<Long, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Long, CartItem> items) {
        this.items = items;
    }

    // добавить товар (или увеличить количество)
    public void addItem(CartItem newItem) {
        Long id = newItem.getProductId();
        if (items.containsKey(id)) {
            CartItem existing = items.get(id);
            existing.setQuantity(existing.getQuantity() + newItem.getQuantity());
        } else {
            items.put(id, newItem);
        }
    }

    // удалить товар
    public void removeItem(Long productId) {
        items.remove(productId);
    }

    // обновить количество
    public void updateQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
        } else {
            CartItem item = items.get(productId);
            if (item != null) {
                item.setQuantity(quantity);
            }
        }
    }

    // полная стоимость корзины
    public BigDecimal getTotalPrice() {
        return items.values().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // общее количество товаров (штук)
    public int getTotalItems() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    // очистить корзину
    public void clear() {
        items.clear();
    }
}