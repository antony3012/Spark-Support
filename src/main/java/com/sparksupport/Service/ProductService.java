package com.sparksupport.Service;

import com.sparksupport.Entity.Product;
import com.sparksupport.Entity.Sale;
import com.sparksupport.Repository.ProductRepository;
import com.sparksupport.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;


    public Page<Product> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public String getTotalRevenue() {

        double totalRevenue = 0.0;
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            List<Sale> sales = product.getSales();
            for (Sale sale : sales) {
                totalRevenue += sale.getQuantity() * product.getPrice();
            }
        }
        return "Total Revenue: " +totalRevenue;
    }

    public double getRevenueByProduct(long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            double revenue = 0.0;
            List<Sale> sales = product.getSales();
            for (Sale sale : sales) {
                revenue += sale.getQuantity() * product.getPrice();
            }
            return revenue;
        }
        return 0.0;
    }
}
