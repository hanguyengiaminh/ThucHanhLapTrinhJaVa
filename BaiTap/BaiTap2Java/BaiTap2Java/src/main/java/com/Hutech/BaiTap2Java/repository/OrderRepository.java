package com.Hutech.BaiTap2Java.repository;

import com.Hutech.BaiTap2Java.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
