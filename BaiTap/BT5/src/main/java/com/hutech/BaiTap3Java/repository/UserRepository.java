package com.hutech.BaiTap3Java.repository;

import com.hutech.BaiTap3Java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
