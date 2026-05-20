package com.electronics.store.service.impl;

import com.electronics.store.dto.Cart;
import com.electronics.store.dto.CartItem;
import com.electronics.store.model.ProductEntity;
import com.electronics.store.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public void addToCart(Cart cart, Long productId, int quantity) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
        CartItem item = new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity
        );
        cart.addItem(item);
    }

    public void updateQuantity(Cart cart, Long productId, int quantity) {
        cart.updateQuantity(productId, quantity);
    }

    public void removeFromCart(Cart cart, Long productId) {
        cart.removeItem(productId);
    }

    public void clearCart(Cart cart) {
        cart.clear();
    }
}