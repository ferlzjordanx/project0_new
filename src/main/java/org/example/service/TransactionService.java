package org.example.service;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.repository.TransactionRepository;


public class TransactionService {

    private UserService userService;
    private TransactionRepository transactionRepository;
    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    public TransactionService(UserService userService, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        transactionRepository.loadFile();
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
        transactionRepository.saveToFile();
    }

    public void deposit(User user, double amount) {
        Transaction transaction = new Transaction();
        transaction.setUserID(user.getUserID());
        transaction.setAmount(amount);
        save(transaction);

        user.setBalance(user.getBalance() + amount);
        userService.save(user);

        System.out.println("Deposit successful.");
        logger.error("Error in deposit");
    }

    public void withdraw(User user, double amount) {
        if (user.getBalance() >= amount) {
            Transaction transaction = new Transaction();
            transaction.setUserID(user.getUserID());
            transaction.setAmount(amount * -1.0);
            save(transaction);

            user.setBalance(user.getBalance() - amount);
            userService.save(user);

            System.out.println("Withdraw successful.");
        } else {
            System.out.println("Insufficient funds.");
            logger.warn("Insufficient funds");
        }
    }

    public void transfer(User user, double amount, User targetUser) {
        withdraw(user, amount);
        deposit(targetUser, amount);
        System.out.println("Transfer successful.");
        logger.info("Transfer successful");
    }

    public List<Transaction> findByUserId(int userId) {
        return transactionRepository.findByUserId(userId);
    }
}
