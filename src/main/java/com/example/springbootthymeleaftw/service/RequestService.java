package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.Request;
import com.example.springbootthymeleaftw.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public void addNewAccountRequest(Request request){
        requestRepository.save(request);
    }

    public List<Request> getUnacceptedRequests(){

        List<Request> unacceptedRequests = requestRepository.findAllByAcceptedIsFalse();

        return  unacceptedRequests;
    }

    public void updateRequestByEmail(String email){
        requestRepository.updateByEmail(email);
    }

    public Request getById(Long valueOf) {
        return requestRepository.getById(valueOf);
    }

    public boolean accepted(String userMail){
        return requestRepository.checkiFAccepted(userMail);
    }
}
