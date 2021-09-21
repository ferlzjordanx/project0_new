package org.example.model;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testTransactionCreation() {
        Transaction transaction = new Transaction();
        transaction.setAmount(200.0);
        transaction.setUserID(1);
        assertEquals(200.0, transaction.getAmount());
        assertEquals(1, transaction.getUserID());
    }

}