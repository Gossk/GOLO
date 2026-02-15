package com.golo.ecommerce.dto.request;

import com.golo.ecommerce.entity.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Shipping city is required")
    private String shippingCity;

    @NotBlank(message = "Shipping country is required")
    private String shippingCountry;

    @NotBlank(message = "Shipping postal code is required")
    private String shippingPostalCode;

    @NotNull(message = "Payment method is required")
    private Order.PaymentMethod paymentMethod;
}