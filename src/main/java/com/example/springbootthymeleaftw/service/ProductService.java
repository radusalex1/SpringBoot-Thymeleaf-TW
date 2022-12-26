package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.Product;
import com.example.springbootthymeleaftw.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts(){
       return productRepository.findAll();
    }

    public void addNewProduct(Product product) {
       Optional<Product> productOptional =
               productRepository.findProductByName(product.getName());

       if(productOptional.isPresent()){
           throw  new IllegalStateException("name taken");
       }
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {

       boolean exists =  productRepository.existsById(productId);
       if(!exists){
           throw new IllegalStateException("product with id: "+productId
                   +" does note exist");
       }

       productRepository.deleteById(productId);
    }
}
