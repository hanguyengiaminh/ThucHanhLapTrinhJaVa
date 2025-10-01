package com.hutech.BaiTap3Java.repository;

import com.hutech.BaiTap3Java.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

