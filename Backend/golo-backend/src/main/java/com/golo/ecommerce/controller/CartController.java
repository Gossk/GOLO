package com.golo.ecommerce.controller;

import com.golo.ecommerce.dto.request.CartItemRequest;
import com.golo.ecommerce.dto.response.ApiResponse;
import com.golo.ecommerce.dto.response.CartResponse;
import com.golo.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart() {
        CartResponse cart = cartService.getCart();
        return ResponseEntity.ok(new ApiResponse(true, "Cart retrieved successfully", cart));
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody CartItemRequest cartItemRequest) {
        CartResponse cart = cartService.addItemToCart(cartItemRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Item added to cart successfully", cart));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Integer quantity
    ) {
        CartResponse cart = cartService.updateCartItem(itemId, quantity);
        return ResponseEntity.ok(new ApiResponse(true, "Cart item updated successfully", cart));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long itemId) {
        CartResponse cart = cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok(new ApiResponse(true, "Item removed from cart successfully", cart));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(new ApiResponse(true, "Cart cleared successfully"));
    }
}