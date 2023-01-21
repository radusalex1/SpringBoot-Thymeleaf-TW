package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.CargoRequest;
import com.example.springbootthymeleaftw.repository.CargoRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoRequestService {
    private final CargoRequestRepository cargoRequestRepository;

    public void add(CargoRequest cargoRequest){
        cargoRequestRepository.save(cargoRequest);
    }

    public List<CargoRequest> getAllUnacceptedForB2b(Long id){
        return cargoRequestRepository.getAllUnacceptedForB2b(id);
    }

}
