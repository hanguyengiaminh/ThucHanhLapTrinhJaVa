package com.hutech.BaiTap3Java.API;

import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Thêm import này

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // SỬA: Thay đổi phương thức createProduct
    @PostMapping
    public Product createProduct(@Valid @ModelAttribute Product product,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        // Gọi service để xử lý việc lưu sản phẩm và ảnh
        return productService.addProduct(product, imageFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return ResponseEntity.ok(product);
    }

    // Bạn cũng sẽ cần cập nhật phương thức này tương tự nếu muốn sửa ảnh sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(productDetails); // Cần sửa service cho updateProduct
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}