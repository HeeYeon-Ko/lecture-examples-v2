package com.growmighty.lectures.firstday.tangledmonolith.order.presentation;

import com.growmighty.lectures.firstday.tangledmonolith.common.response.ApiResponse;
import com.growmighty.lectures.firstday.tangledmonolith.order.application.OrderService;
import com.growmighty.lectures.firstday.tangledmonolith.order.application.dto.OrderConsistencyView;
import com.growmighty.lectures.firstday.tangledmonolith.order.application.dto.OrderResult;
import com.growmighty.lectures.firstday.tangledmonolith.order.presentation.dto.OrderConsistencyResponse;
import com.growmighty.lectures.firstday.tangledmonolith.order.presentation.dto.OrderResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders() {
        List<OrderResponse> responses = orderService.getOrders().stream()
                .map(OrderResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@RequestBody OrderCreationRequest request) {
        OrderResult result = orderService.placeOrder(request.userId(), request.requests());
        return ResponseEntity.ok(ApiResponse.success(OrderResponse.from(result)));
    }

    @GetMapping("/{orderId}/inspect")
    public ResponseEntity<ApiResponse<OrderConsistencyResponse>> inspectOrder(@PathVariable Long orderId) {
        OrderConsistencyView orderConsistencyView = orderService.inspectOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success(OrderConsistencyResponse.from(orderConsistencyView)));
    }

    @PatchMapping("/{orderId}/orderItems/{orderItemId}/price")
    public ResponseEntity<ApiResponse<Void>> changeOrderItemPrice(@PathVariable Long orderId, @PathVariable Long orderItemId, @RequestParam BigDecimal price) {
        orderService.changeItemPrice(orderId, orderItemId, price);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/{orderId}/orderItems/{orderItemId}/quantity")
    public ResponseEntity<ApiResponse<Void>> changeOrderItemQuantity(@PathVariable Long orderId, @PathVariable Long orderItemId, @RequestParam int quantity) {
        orderService.changeItemQuantity(orderId, orderItemId, quantity);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    public record OrderCreationRequest(@NonNull Long userId, @NonNull List<OrderItemRequest> requests) {

    }

    public record OrderItemRequest(@NonNull Long productId, @NonNull Integer quantity) {

    }
}
