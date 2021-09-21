package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreditRequestTest {


    @Test
    public void testCreditRequestCreation(){
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setAmount(50.0);
        creditRequest.setUserId(5);
        assertEquals(50.0, creditRequest.getAmount());
        assertEquals(5, creditRequest.getUserId());
    }

//
//    private Integer creditId;
//    private Double amount;
//    private Integer userId;
//    private String status; // Pending/Approved/Canceled
//
//    public Integer getCreditId() {
//        return creditId;
//    }
//    public void setCreditId(Integer creditId) {
//        this.creditId = creditId;
//    }
//    public Double getAmount() {
//        return amount;
//    }
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//    public Integer getUserId() {
//        return userId;
//    }
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//    public String getStatus() {
//        return status;
//    }
//    public void setStatus(String status) {
//        this.status = status;
//    }

}