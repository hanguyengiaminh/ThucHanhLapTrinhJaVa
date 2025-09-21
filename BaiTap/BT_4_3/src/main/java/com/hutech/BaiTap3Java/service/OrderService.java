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

    public Order createOrder(String customerName, String address, String phone,
                             String email, String note, String paymentMethod,
                             List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setPhone(phone);
        order.setEmail(email);
        order.setNote(note);
        order.setPaymentMethod(paymentMethod);
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        cartService.clearCart();
        return order;
    }
}