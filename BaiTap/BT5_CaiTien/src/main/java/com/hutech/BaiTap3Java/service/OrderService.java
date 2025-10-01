package com.hutech.BaiTap3Java.service;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.model.Order;
import com.hutech.BaiTap3Java.model.OrderDetail;
import com.hutech.BaiTap3Java.repository.OrderDetailRepository;
import com.hutech.BaiTap3Java.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository; // <-- Nên inject OrderDetailRepository

    @Transactional // Đảm bảo tất cả các thao tác đều thành công hoặc không có gì cả
    public Order createOrder(Order order, List<CartItem> cartItems) {

        // Tính toán tổng giá trị đơn hàng
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Gán các giá trị đã được tính toán hoặc mặc định cho Order
        order.setTotalPrice(totalPrice);
        // Các giá trị mặc định như orderDate và status đã được xử lý bằng @PrePersist trong Entity

        // Lưu Order vào DB để lấy ID, cần thiết cho OrderDetail
        Order savedOrder = orderRepository.save(order);

        // Tạo và lưu các chi tiết đơn hàng (OrderDetail)
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            orderDetailRepository.save(detail); // Lưu từng chi tiết
        }

        return savedOrder;
    }
}