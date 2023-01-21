package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.CargoRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CargoRequestRepository extends JpaRepository<CargoRequest,Long> {

}
