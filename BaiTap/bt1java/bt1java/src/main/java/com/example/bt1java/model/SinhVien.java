package com.example.bt1java.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SinhVien {
    @NotBlank(message = "Tên là bắt buộc")
    private String ten;

    @Min(value = 18, message = "Tuổi phải lớn hơn hoặc bằng 18")
    @Max(value = 100, message = "Tuổi phải nhỏ hơn hoặc bằng 100")
    private int tuoi;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Khoa phải là chữ")
    private String khoa;

    // --- Bổ sung ---
    @NotBlank(message = "Địa chỉ là bắt buộc")
    private String diaChi;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có 10 chữ số")
    private String soDienThoai;

    private String anhDaiDien; // URL to the student's photo

    // Getters and Setters for all fields
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }
}