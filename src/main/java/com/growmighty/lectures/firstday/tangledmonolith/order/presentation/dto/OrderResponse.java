package com.growmighty.lectures.firstday.tangledmonolith.order.presentation.dto;

import com.growmighty.lectures.firstday.tangledmonolith.order.application.dto.OrderResult;

public record OrderResponse(Long id) {
    public static OrderResponse from(OrderResult result) {
        return new OrderResponse(
                result.id());
    }
}
