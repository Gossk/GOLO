package com.golo.ecommerce.service.impl;

import com.golo.ecommerce.dto.request.CartItemRequest;
import com.golo.ecommerce.dto.response.CartItemResponse;
import com.golo.ecommerce.dto.response.CartResponse;
import com.golo.ecommerce.entity.Cart;
import com.golo.ecommerce.entity.CartItem;
import com.golo.ecommerce.entity.Product;
import com.golo.ecommerce.entity.User;
import com.golo.ecommerce.exception.BadRequestException;
import com.golo.ecommerce.exception.ResourceNotFoundException;
import com.golo.ecommerce.repository.CartItemRepository;
import com.golo.ecommerce.repository.CartRepository;
import com.golo.ecommerce.repository.ProductRepository;
import com.golo.ecommerce.service.CartService;
import com.golo.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Override
    public CartResponse getCart() {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(currentUser);
                    return cartRepository.save(newCart);
                });

        return mapToResponse(cart);
    }

    @Override
    public CartResponse addItemToCart(CartItemRequest cartItemRequest) {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(currentUser);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", cartItemRequest.getProductId()));

        if (!product.getActive()) {
            throw new BadRequestException("Product is not available");
        }

        if (product.getStock() < cartItemRequest.getQuantity()) {
            throw new BadRequestException("Insufficient stock. Available: " + product.getStock());
        }

        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + cartItemRequest.getQuantity();
            if (product.getStock() < newQuantity) {
                throw new BadRequestException("Insufficient stock. Available: " + product.getStock());
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItemRepository.save(cartItem);
        }

        return mapToResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(Long itemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", itemId));

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }

        if (cartItem.getProduct().getStock() < quantity) {
            throw new BadRequestException("Insufficient stock. Available: " + cartItem.getProduct().getStock());
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return mapToResponse(cartItem.getCart());
    }

    @Override
    public CartResponse removeItemFromCart(Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", itemId));

        Cart cart = cartItem.getCart();
        cartItemRepository.delete(cartItem);

        return mapToResponse(cart);
    }

    @Override
    public void clearCart() {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteByCartId(cart.getId());
    }

    private CartResponse mapToResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());

        List<CartItemResponse> items = cart.getItems().stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());

        response.setItems(items);

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = items.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        response.setTotalAmount(totalAmount);
        response.setTotalItems(totalItems);

        return response;
    }

    private CartItemResponse mapItemToResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setProductImage(cartItem.getProduct().getImages().isEmpty() ?
                null : cartItem.getProduct().getImages().get(0));
        response.setPrice(cartItem.getProduct().getPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setSubtotal(cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        response.setAvailableStock(cartItem.getProduct().getStock());
        return response;
    }
}