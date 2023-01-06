package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.repository.UserProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProductService {
    private final UserProductRepository userProductRepository;

    public void addNewUserProduct(UserProductEntity newUserProductEntity){
        userProductRepository.save(newUserProductEntity);
    }
}
