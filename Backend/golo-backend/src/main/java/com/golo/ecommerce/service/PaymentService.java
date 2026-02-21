package com.golo.ecommerce.service;

import com.golo.ecommerce.entity.Order;
import com.golo.ecommerce.entity.Payment;

public interface PaymentService {
    Payment processPayment(Order order);
    Payment getPaymentByOrderId(Long orderId);
    Payment getPaymentByTransactionId(String transactionId);
    void refundPayment(Long paymentId);
}