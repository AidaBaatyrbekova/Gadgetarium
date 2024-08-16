package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.dto.request.OrderRequest;
import com.peaksoft.gadgetarium.model.dto.response.OrderFinishResponse;
import com.peaksoft.gadgetarium.model.dto.response.OrderOverviewResponse;
import com.peaksoft.gadgetarium.model.dto.response.OrderResponse;
import com.peaksoft.gadgetarium.model.entities.Order;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.model.enums.DeliveryStatus;
import com.peaksoft.gadgetarium.model.enums.DeliveryType;
import com.peaksoft.gadgetarium.model.enums.PaymentType;
import com.peaksoft.gadgetarium.repository.OrderRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    public OrderResponse createOrder(OrderRequest request, Principal principal, Long productId) {

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));
        Order order = mapToEntity(request);

        if (order.getDeliveryType() == DeliveryType.DELIVERY_BY_COURIER) {
            order.setAddress(request.getAddress());
        } else {
            order.setAddress("");
            order.setFirstName(user.getName());
            order.setLastName(user.getLastName());
            order.setPhoneNumber(user.getPhoneNumber());
            order.setEmail(user.getEmail());
        }

        List<Product> products = List.of(product);
        order.setProducts(products);
        order.setAmount((double) product.getPrice());
        order.setUser(user);
        user.getOrders().add(order);

        orderRepository.save(order);
        userRepository.save(user);

        return OrderResponse.builder()
                .id(order.getId())
                .delivery(order.getDeliveryType())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .email(order.getEmail())
                .created(order.getCreated())
                .address(order.getAddress())
                .build();
    }

    public ResponseEntity<String> paymentMethod(PaymentType type, Long orderId, Principal principal) {

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NO_ORDERS));

        order.setPaymentType(type);
        orderRepository.save(order);

        return ResponseEntity.ok("Payment type successfully saved!");
    }

    public OrderOverviewResponse orderOverview(Principal principal, Long orderId) {

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NO_ORDERS));

        return OrderOverviewResponse.builder()
                .totalSum(String.valueOf(order.getAmount()))
                .delivery(order.getAddress() != null ? order.getAddress() : "No delivery address")
                .payment(order.getPaymentType())
                .build();
    }

    public ResponseEntity<OrderFinishResponse> orderFinish(Long orderId, Boolean success, Principal principal) {

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NO_ORDERS));

        if (success) {
            order.setStatus(DeliveryStatus.ON_THE_WAY);
            orderRepository.save(order);

            return ResponseEntity.ok(OrderFinishResponse.builder()
                    .message("Thank you! Your application has been successfully completed!")
                    .applicationNumber(order.getApplicationNumber())
                    .status(order.getStatus().toString())
                    .localDate(order.getCreated().toLocalDate().toString())
                    .email(order.getEmail())
                    .build());
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new OrderFinishResponse(ExceptionMessage.ORDER_PROCESSING_ERROR, null, null, null, null));
        }
    }

    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .delivery(order.getDeliveryType())
                        .firstName(order.getFirstName())
                        .lastName(order.getLastName())
                        .email(order.getEmail())
                        .created(order.getCreated())
                        .address(order.getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionMessage.ORDER_NOT_FOUND);
        }
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok("Order successfully deleted!");
    }

    public Order mapToEntity(OrderRequest orderRequest) {
        return Order.builder()
                .deliveryType(DeliveryType.valueOf(orderRequest.getDeliveryOptions()))
                .firstName(orderRequest.getName())
                .lastName(orderRequest.getLastName())
                .email(orderRequest.getEmail())
                .phoneNumber(orderRequest.getPhoneNumber())
                .status(DeliveryStatus.PENDING)
                .applicationNumber(generateUniqueApplicationNumber())
                .build();
    }

    private String generateUniqueApplicationNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(1000000, 9000000));
    }
}