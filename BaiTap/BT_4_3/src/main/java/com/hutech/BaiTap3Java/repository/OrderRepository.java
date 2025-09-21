package com.hutech.BaiTap3Java.repository;

import com.hutech.BaiTap3Java.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
