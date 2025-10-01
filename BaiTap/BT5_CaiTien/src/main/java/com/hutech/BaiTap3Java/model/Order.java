package com.hutech.BaiTap3Java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate; // Thời gian đặt hàng

    // **LƯU Ý**: Đã xóa validation (@NotNull, @DecimalMin) khỏi totalPrice
    // vì nó được tính ở backend (OrderService)
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice; // Tổng giá trị đơn hàng

    // --- Thông tin khách hàng và giao hàng ---
    @Column(name = "customer_name", length = 100, nullable = false)
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String customerName;

    @Column(name = "shipping_address", length = 255, nullable = false)
    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddress;

    @Column(name = "phone_number", length = 20, nullable = false)
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])([0-9]{8})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Column(name = "email", length = 100)
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "note", length = 500)
    private String note; // Ghi chú của khách hàng

    // --- Thông tin thanh toán và trạng thái ---
    @Column(name = "payment_method", length = 50)
    @NotBlank(message = "Vui lòng chọn phương thức thanh toán")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status; // Trạng thái đơn hàng

    // --- Mối quan hệ với chi tiết đơn hàng ---
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    // Tự động gán thời gian và trạng thái khi tạo mới
    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
}