package com.sparksupport.Controller;

import com.sparksupport.Entity.Sale;
import com.sparksupport.Service.SaleService;
import com.sparksupport.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;


    @GetMapping("/all")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        if (sale.isPresent()) {
            return new ResponseEntity<>(sale.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Sale with ID " + id + " not found");
        }
    }


    @PostMapping("/addSale")
    public ResponseEntity<Sale> addSale(@RequestBody Sale sale) {
        Sale savedSale = saleService.save(sale);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }


    @PutMapping("/updateSale/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable long id, @RequestBody Sale sale) {
        Optional<Sale> existingSale = saleService.getSaleById(id);
        if (existingSale.isPresent()) {
            Sale updatedSale = existingSale.get();
            updatedSale.setProductId(sale.getProductId());
            updatedSale.setQuantity(sale.getQuantity());
            updatedSale.setSaleDate(sale.getSaleDate());
            saleService.save(updatedSale);
            return new ResponseEntity<>(updatedSale, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Sale with ID " + id + " not found");
        }
    }


    @DeleteMapping("/removeSale/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        if (sale.isPresent()) {
            saleService.deleteSale(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("Sale with ID " + id + " not found");
        }
    }
}

