package com.sparksupport.Controller;

import com.sparksupport.Entity.Product;
import com.sparksupport.Exception.InvalidInputException;
import com.sparksupport.Exception.ResourceNotFoundException;
import com.sparksupport.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        try {
            Page<Product> products = productService.getAll(page, size);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid pagination parameters.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        if (product.getName() == null || product.getPrice() <= 0) {
            throw new InvalidInputException("Product name and price must be provided and valid.");
        }
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            productService.save(updatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
    }

    // Delete product by ID
    @DeleteMapping("/removeProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        Optional<Product> product = productService.findById((long) id);
        if (product.isPresent()) {
            productService.delete(product.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<String> getTotalRevenue() {
        try {
            String revenue = productService.getTotalRevenue();
            return ResponseEntity.ok(revenue);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating total revenue.");
        }
    }

    @GetMapping("/revenue/{productId}")
    public ResponseEntity<Double> getRevenueByProduct(@PathVariable long productId) {
        double revenue = productService.getRevenueByProduct(productId);
        return revenue > 0.0 ? ResponseEntity.ok(revenue) : ResponseEntity.notFound().build();
    }
}
