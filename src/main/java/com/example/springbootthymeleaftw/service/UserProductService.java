package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.repository.UserProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProductService {
    private final UserProductRepository userProductRepository;

    public void addNewUserProduct(UserProductEntity newUserProductEntity){
        userProductRepository.save(newUserProductEntity);
    }

    public List<UserProductEntity> getAll(){
        return userProductRepository.findAll();
    }

    public UserProductEntity getByB2bAndProduct(long b2bId,long productId){
        return userProductRepository.getByB2bAndProduct(b2bId,productId);
    }

    public UserProductEntity getByB2cAndProduct(long b2cId,long productId){
        return userProductRepository.getByB2bAndProduct(b2cId,productId);
    }

}
