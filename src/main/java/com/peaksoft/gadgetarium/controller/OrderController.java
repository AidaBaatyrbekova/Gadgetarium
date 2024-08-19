package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.OrderRequest;
import com.peaksoft.gadgetarium.model.dto.response.OrderFinishResponse;
import com.peaksoft.gadgetarium.model.dto.response.OrderOverviewResponse;
import com.peaksoft.gadgetarium.model.dto.response.OrderResponse;
import com.peaksoft.gadgetarium.model.enums.PaymentType;
import com.peaksoft.gadgetarium.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "The Order")
@RequiredArgsConstructor
@RequestMapping("/api/order")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @Operation(summary = "Create a new order")
    @PostMapping("/create/{productId}")
    public ResponseEntity<OrderResponse> orderCreate(@RequestBody OrderRequest request, @PathVariable Long productId, Principal principal) {
        OrderResponse response = orderService.createOrder(request, principal, productId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add payment type to an order")
    @PostMapping("/payment/{type}/{orderId}")
    public ResponseEntity<String> paymentAdd(@PathVariable PaymentType type, @PathVariable Long orderId, Principal principal) {
        return orderService.paymentMethod(type, orderId, principal);
    }

    @Operation(summary = "Get order overview")
    @GetMapping("/overview/{orderId}")
    public ResponseEntity<OrderOverviewResponse> orderOverview(@PathVariable Long orderId, Principal principal) {
        OrderOverviewResponse response = orderService.orderOverview(principal, orderId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Finish an order")
    @PostMapping("/finish/{orderId}")
    public ResponseEntity<OrderFinishResponse> orderFinish(@PathVariable Long orderId, @RequestParam Boolean success, Principal principal) {
        return orderService.orderFinish(orderId, success, principal);
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/find-all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Delete an order by Id")
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}