package com.hutech.BaiTap3Java.service;

import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.repository.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile; // Thêm import

import java.io.IOException; // Thêm import
import java.nio.file.Files; // Thêm import
import java.nio.file.Path; // Thêm import
import java.nio.file.Paths; // Thêm import
import java.util.List;
import java.util.Optional;
import java.util.UUID; // Thêm import

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    // Thêm đường dẫn tới thư mục lưu ảnh
    private final String uploadPath = "src/main/resources/static/images/";

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // SỬA: Thay đổi phương thức addProduct để nhận thêm MultipartFile
    public Product addProduct(Product product, MultipartFile imageFile) {
        // Xử lý upload file ảnh
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Tạo tên file duy nhất để tránh trùng lặp
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

                // Tạo đường dẫn đầy đủ tới file
                Path filePath = Paths.get(uploadPath + fileName);

                // Lưu file vào thư mục
                Files.copy(imageFile.getInputStream(), filePath);

                // Lưu đường dẫn của ảnh vào product (chỉ lưu phần truy cập được từ web)
                product.setImageUrl("/images/" + fileName);
            } catch (IOException e) {
                // Xử lý lỗi nếu có, ví dụ: throw exception hoặc log lỗi
                e.printStackTrace();
            }
        }
        return productRepository.save(product);
    }

    public Product updateProduct(@NotNull Product product) {
        // Logic cập nhật sản phẩm cần được sửa đổi tương tự để xử lý ảnh
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        // existingProduct.setImageUrl(product.getImageUrl()); // Thêm dòng này nếu có cập nhật ảnh
        return productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
}