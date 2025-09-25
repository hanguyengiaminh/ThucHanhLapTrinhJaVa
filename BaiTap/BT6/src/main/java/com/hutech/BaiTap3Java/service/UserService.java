package com.hutech.BaiTap3Java.service;

import com.hutech.BaiTap3Java.Role;
import com.hutech.BaiTap3Java.model.User;
import com.hutech.BaiTap3Java.repository.IRoleRepository;
import com.hutech.BaiTap3Java.repository.IUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private final IUserRepository userRepository;

    @Autowired
    private final IRoleRepository roleRepository;

    // --- CÁC HÀM CŨ CHO ĐĂNG NHẬP / ĐĂNG KÝ ---

    /**
     * Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.
     */
    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Gán vai trò mặc định (USER) cho người dùng mới.
     */
    public void setDefaultRole(String username) {
        userRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                    userRepository.save(user);
                },
                () -> { throw new UsernameNotFoundException("User not found with username: " + username); }
        );
    }

    /**
     * Tải thông tin chi tiết người dùng để Spring Security xác thực.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    // --- CÁC PHƯƠNG THỨC MỚI DÀNH CHO API QUẢN TRỊ ---

    /**
     * Lấy danh sách tất cả người dùng.
     * @return List<User>
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Xóa một người dùng dựa trên ID.
     * @param id ID của người dùng cần xóa.
     */
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Cập nhật vai trò (phân quyền) cho một người dùng.
     * @param userId ID của người dùng.
     * @param roleIds Danh sách các ID của vai trò mới.
     * @return User đã được cập nhật.
     */
    @Transactional
    public User updateUserRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Set<com.hutech.BaiTap3Java.model.Role> newRoles = roleIds.stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId)))
                .collect(Collectors.toSet());

        user.setRoles(newRoles);
        return userRepository.save(user);
    }
}

