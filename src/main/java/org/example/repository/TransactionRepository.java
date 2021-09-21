package org.example.repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.model.Transaction;

public class TransactionRepository {

    private List<Transaction> transactionList = new ArrayList<>();

    public void save(Transaction transaction) {
        if (transaction.getTransactionID() == null) {
            transaction.setTransactionID(transactionList.size());
            transactionList.add(transaction);
        } else {
            transactionList.set(transaction.getTransactionID(), transaction);// transactions always inserted, line may
                                                                             // not run
        }
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("transaction.txt", false)) {
            for (Transaction transaction : transactionList) {
                writer.write(transaction.getTransactionID() + "," + transaction.getAmount() + ","
                        + transaction.getUserID() + "\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadFile() {

        try (FileReader reader = new FileReader("transaction.txt")) {
            Scanner scanner = new Scanner(reader);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                Transaction transaction = new Transaction();
                transaction.setTransactionID(Integer.parseInt(fields[0]));
                transaction.setAmount(Double.parseDouble(fields[1]));
                transaction.setUserID(Integer.parseInt(fields[2]));
                transactionList.add(transaction);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Transaction> findByUserId(Integer userId) {
        List<Transaction> tempTransaction = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            if (transaction.getUserID() == userId) {
                tempTransaction.add(transaction);
            }
        }

        return tempTransaction;
    }
}
