package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.service.CategoryService;
import com.hutech.BaiTap3Java.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

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
    public String addProduct(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "/products/add-product";
        }
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
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            product.setId(id);
            return "/products/update-product";
        }
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}