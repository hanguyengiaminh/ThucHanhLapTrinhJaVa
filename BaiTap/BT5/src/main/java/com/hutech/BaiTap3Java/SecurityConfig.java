package com.hutech.BaiTap3Java;

import com.hutech.BaiTap3Java.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        // Trả về đối tượng userService đã được Spring tiêm vào, không tạo mới.
        return userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/", "/oauth/**", "/register", "/error")
                        .permitAll() // Cho phép tất cả truy cập vào các tài nguyên tĩnh, trang chủ, đăng ký...
                        .requestMatchers("/products", "/cart", "/cart/**")
                        .permitAll() // Cho phép tất cả xem sản phẩm và giỏ hàng
                        .requestMatchers("/products/edit/**", "/products/add", "/products/delete", "/categories/**")
                        .hasAnyAuthority("ADMIN") // Chỉ ADMIN mới có quyền thêm/sửa/xóa
                        .anyRequest().authenticated() // Mọi yêu cầu khác đều cần phải đăng nhập
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // Chuyển hướng về trang login sau khi đăng xuất
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Trang đăng nhập tùy chỉnh
                        .loginProcessingUrl("/login") // URL xử lý form đăng nhập
                        .defaultSuccessUrl("/products") // Chuyển hướng về trang chủ sau khi đăng nhập thành công
                        .failureUrl("/login?error") // Chuyển hướng về trang login với tham số lỗi
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("hutech")
                        .rememberMeCookieName("hutech")
                        .tokenValiditySeconds(24 * 60 * 60)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403") // Trang hiển thị khi không có quyền truy cập
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1)
                        .expiredUrl("/login")
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("hutech")
                )
                .build();
    }
}