package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("Select p from Product p where p.name = ?1")
    Optional<Product> findProductByName(String name);

}
