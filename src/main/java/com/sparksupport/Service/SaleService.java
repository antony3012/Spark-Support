package com.sparksupport.Service;

import com.sparksupport.Entity.Sale;
import com.sparksupport.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(long id) {
        return saleRepository.findById(id);
    }

    public void deleteSale(long id) {
        saleRepository.deleteById(id);
    }

}
