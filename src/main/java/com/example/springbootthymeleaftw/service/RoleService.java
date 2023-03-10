package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleEntity> getAllRoles(){
        return roleRepository.findAll();
    }

    public RoleEntity getRoleById(Long Id){
        return roleRepository.getRoleById(Id);
    }

    public RoleEntity getCRoleByName(String name){
        return  roleRepository.getRoleByName(name);
    }


}
