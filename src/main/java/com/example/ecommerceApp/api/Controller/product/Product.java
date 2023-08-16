package com.example.ecommerceApp.api.Controller;

import com.example.ecommerceApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class Product {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<com.example.ecommerceApp.model.Product> getProducts() {
        return productService.getProducts();
    }

}
