package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.service.CategoryService;
import com.hutech.BaiTap3Java.service.ProductService;
import com.hutech.BaiTap3Java.service.FileStorageService; // <-- Import service mới
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // <-- Import MultipartFile

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileStorageService fileStorageService; // <-- Tiêm FileStorageService

    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products/products-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product,
                             BindingResult result,
                             @RequestParam("imageFile") MultipartFile imageFile, // <-- Nhận file từ form
                             Model model) {
        if (result.hasErrors()) {
            // Nếu có lỗi, trả lại danh sách categories cho form
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/add-product";
        }

        // Lưu file ảnh và lấy đường dẫn
        String imageUrl = fileStorageService.saveFile(imageFile);
        product.setImageUrl(imageUrl); // <-- Gán đường dẫn ảnh cho sản phẩm

        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/products/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product,
                                BindingResult result,
                                // Cho phép imageFile không bắt buộc (optional)
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/update-product";
        }

        Product existingProduct = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // Kiểm tra xem người dùng có tải lên file mới không
        if (imageFile != null && !imageFile.isEmpty()) {
            // Nếu có file mới -> Xóa file cũ và lưu file mới
            fileStorageService.deleteFile(existingProduct.getImageUrl());
            String newImageUrl = fileStorageService.saveFile(imageFile);
            product.setImageUrl(newImageUrl);
        } else {
            // Nếu không có file mới -> Giữ lại đường dẫn ảnh cũ
            product.setImageUrl(existingProduct.getImageUrl());
        }

        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        // Lấy thông tin sản phẩm trước khi xóa
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // Xóa file ảnh liên quan
        fileStorageService.deleteFile(product.getImageUrl());

        // Xóa sản phẩm khỏi DB
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}