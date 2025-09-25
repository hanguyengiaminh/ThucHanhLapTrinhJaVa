package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.service.CategoryService;
import com.hutech.BaiTap3Java.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;
    @Autowired
    private final CategoryService categoryService;

    // Hien thi trang danh sach san pham
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/products-list";
    }

    // Hien thi form them moi san pham
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // Can de hien thi dropdown
        return "products/add-product";
    }

    // Hien thi form cap nhat san pham
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); // Can de hien thi dropdown
        return "products/update-product";
    }
}
