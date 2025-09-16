package com.mercado_challenge.MercadoAdventure.application.port.in.command;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.domain.model.OrderItem;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationCommand {
    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Order items cannot be null")
    private List<OrderItem> orderItems;

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

    @Positive(message = "Total price must be a positive value")
    @NotNull(message = "Total price cannot be null")
    private Double totalPrice;

}
