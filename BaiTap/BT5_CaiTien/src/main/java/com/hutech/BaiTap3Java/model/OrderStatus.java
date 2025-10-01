package com.hutech.BaiTap3Java.model;

public enum OrderStatus {
    PENDING,        // Chờ xác nhận
    PROCESSING,     // Đang xử lý
    SHIPPED,        // Đã giao cho đơn vị vận chuyển
    DELIVERED,      // Đã giao thành công
    CANCELLED       // Đã hủy
}