package com.golo.ecommerce.service;

import com.golo.ecommerce.dto.request.CartItemRequest;
import com.golo.ecommerce.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart();
    CartResponse addItemToCart(CartItemRequest cartItemRequest);
    CartResponse updateCartItem(Long itemId, Integer quantity);
    CartResponse removeItemFromCart(Long itemId);
    void clearCart();
}