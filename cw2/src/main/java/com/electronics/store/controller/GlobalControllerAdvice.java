package com.electronics.store.controller;

import com.electronics.store.dto.Cart;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("cartTotalItems")
    public int addCartTotalItems(Cart cart) {
        return cart.getTotalItems();
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated(Authentication auth) {
        return auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"));
    }

    @ModelAttribute("username")
    public String username(Authentication auth) {
        if (isAuthenticated(auth)) {
            return auth.getName();
        }
        return null;
    }
}