package com.hutech.BaiTap3Java.service;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.model.Order;
import com.hutech.BaiTap3Java.model.OrderDetail;
import com.hutech.BaiTap3Java.repository.OrderDetailRepository;
import com.hutech.BaiTap3Java.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartService cartService;

    // Tạo đơn hàng từ giỏ hàng
    public Order createOrder(String customerName, List<CartItem> cartItems) {
        // Tạo đơn hàng mới
        Order order = new Order();
        order.setCustomerName(customerName);
        order = orderRepository.save(order);

        // Tạo chi tiết đơn hàng từ từng mục trong giỏ
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        // Xóa giỏ hàng sau khi đặt hàng
        cartService.clearCart();

        return order;
    }
}