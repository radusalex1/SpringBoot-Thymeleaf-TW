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

    public List<UserProductEntity> getByFilter(String category,String companyName,Integer quantity)
    {

        if(!category.equals("All") && !companyName.equals("All") && quantity!=-1) {
            return userProductRepository.getByFilter(category,companyName,quantity);
        }

        if(!category.equals("All") && !companyName.equals("All")) {
            return userProductRepository.getByCategoryAndCompany(category,companyName);
        }

        if(!category.equals("All") && quantity!=-1){
            return userProductRepository.getByCategoryAndQuantity(category,quantity);
        }

        if(!companyName.equals("All") && quantity!=-1){
            return userProductRepository.getByCompanyAndQuantity(companyName,quantity);
        }

        if(!category.equals("All") ) {
            return userProductRepository.getByCategory(category);
        }

        if(quantity!=-1){
            return userProductRepository.getByQuantity(quantity);
        }

        if(!companyName.equals("All")){
            return userProductRepository.getByCompany(companyName);
        }

        return userProductRepository.findAll();
    }

    public List<UserProductEntity> getAllByB2bId(Long id) {
        return userProductRepository.findAllByB2b(id);
    }

    public UserProductEntity getByUserAndProduct(Long userId, Long productId) {
        return userProductRepository.getByUserAndProduct(userId,productId);
    }
}
