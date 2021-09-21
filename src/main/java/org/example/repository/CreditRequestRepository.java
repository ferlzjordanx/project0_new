package org.example.repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.model.CreditRequest;

public class CreditRequestRepository {

    private List<CreditRequest> creditRequestList = new ArrayList<>();

    public void save(CreditRequest creditRequest) {
        if (creditRequest.getCreditId() == null) {
            creditRequest.setCreditId(creditRequestList.size());
            creditRequestList.add(creditRequest);
        } else {
            creditRequestList.set(creditRequest.getCreditId(), creditRequest);
        }
    }

    public List<CreditRequest> getCreditRequestList() {
        return creditRequestList;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("credit.txt", false)) {
            for (CreditRequest creditRequest : creditRequestList) {
                writer.write(creditRequest.getCreditId() + "," + creditRequest.getAmount() + ","
                        + creditRequest.getUserId() + "," + creditRequest.getStatus() + "\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadFile() {
        try (FileReader reader = new FileReader("credit.txt")) {
            Scanner scanner = new Scanner(reader);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                CreditRequest creditRequest = new CreditRequest();
                creditRequest.setCreditId(Integer.parseInt(fields[0]));
                creditRequest.setAmount(Double.parseDouble(fields[1]));
                creditRequest.setUserId(Integer.parseInt(fields[2]));
                creditRequest.setStatus(fields[3]);
                creditRequestList.add(creditRequest);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<CreditRequest> findByUserId(Integer userId) {
        List<CreditRequest> tempTransaction = new ArrayList<>();

        for (CreditRequest creditRequest : creditRequestList) {
            if (creditRequest.getUserId() == userId) {
                tempTransaction.add(creditRequest);
            }
        }

        return tempTransaction;
    }    
}
