package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.CargoRequest;
import com.example.springbootthymeleaftw.repository.CargoRequestRepository;
import com.example.springbootthymeleaftw.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoRequestService {
    private final CargoRequestRepository cargoRequestRepository;

    public void add(CargoRequest cargoRequest){
        cargoRequestRepository.save(cargoRequest);
    }
}
