package org.example.service;

import org.example.model.CreditRequest;
import org.example.repository.CreditRequestRepository;

public class CreditRequestService {
    
    private CreditRequestRepository creditRequestRepository;

    public CreditRequestService(CreditRequestRepository creditRequestRepository) {
        this.creditRequestRepository = creditRequestRepository;
        creditRequestRepository.loadFile();
    }

    public void save(CreditRequest creditRequest) {
        for (CreditRequest tempRequest : creditRequestRepository.findByUserId(creditRequest.getUserId())) {
            if (tempRequest.getStatus().equals("Pending")) {
                System.out.println("Customer already have a pending credit request.");
                return;
            }
        }

        creditRequestRepository.save(creditRequest);
        creditRequestRepository.saveToFile();
    }

    public CreditRequest findByUserId(int userId) {
        for (CreditRequest tempRequest : creditRequestRepository.findByUserId(userId)) {
            if (tempRequest.getStatus().equals("Pending")) {
                return tempRequest;
            }
        }

        return null;
    }
}
