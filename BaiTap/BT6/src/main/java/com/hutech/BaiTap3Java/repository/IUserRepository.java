package com.hutech.BaiTap3Java.repository;

import com.hutech.BaiTap3Java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
// SỬA Ở ĐÂY: JpaRepository<User, String> -> JpaRepository<User, Long>
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
