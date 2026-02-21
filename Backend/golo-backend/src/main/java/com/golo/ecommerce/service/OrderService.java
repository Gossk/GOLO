package com.golo.ecommerce.service;

import com.golo.ecommerce.dto.request.OrderRequest;
import com.golo.ecommerce.dto.response.OrderResponse;
import com.golo.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
    OrderResponse getOrderByOrderNumber(String orderNumber);
    Page<OrderResponse> getUserOrders(Pageable pageable);
    List<OrderResponse> getUserOrderHistory();
    Page<OrderResponse> getAllOrders(Pageable pageable);
    Page<OrderResponse> getOrdersByStatus(Order.OrderStatus status, Pageable pageable);
    List<OrderResponse> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    OrderResponse updateOrderStatus(Long id, Order.OrderStatus status);
    OrderResponse updatePaymentStatus(Long id, Order.PaymentStatus paymentStatus);
    OrderResponse addTrackingNumber(Long id, String trackingNumber);
    void cancelOrder(Long id);
}