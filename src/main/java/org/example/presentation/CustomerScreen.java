package org.example.presentation;

import java.util.List;
import java.util.Scanner;

import org.example.model.CreditRequest;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.service.CreditRequestService;
import org.example.service.TransactionService;
import org.example.service.UserService;

public class CustomerScreen {

    private UserService userService;
    private TransactionService transactionService;
    private CreditRequestService creditRequestService;

    public CustomerScreen(UserService userService, TransactionService transactionService,
                          CreditRequestService creditRequestService) {

        this.userService = userService;
        this.transactionService = transactionService;
        this.creditRequestService = creditRequestService;
    }

    public void showMenu(User user) {
        String option = "";
        Scanner scanner = new Scanner(System.in);

        while (!option.equals("7")) {
            System.out.println("\nCUSTOMER MENU");
            System.out.println("-------------\n");
            System.out.println("1. View balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Transfer");
            System.out.println("5. View transaction history");
            System.out.println("6. Apply for credit");
            System.out.println("7. Exit");
            System.out.print("\nEnter option: ");

            option = scanner.nextLine();

            if (option.equals("1")) {
                viewBalance(user);
            } else if (option.equals("2")) {
                deposit(user, scanner);
            } else if (option.equals("3")) {
                withdraw(user, scanner);
            } else if (option.equals("4")) {
                transfer(user, scanner);
            } else if (option.equals("5")) {
                viewTransactionHistory(user);
            } else if (option.equals("6")) {
                applyForCredit(user, scanner);
            }

            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
        }
    }

    private void viewBalance(User user) {
        System.out.println("\nVIEW BALANCE");
        System.out.println("-------------\n");
        System.out.print("Your current balance is: $ ");
        System.out.println(user.getBalance());
    }

    private void deposit(User user, Scanner scanner) {
        System.out.println("\nDEPOSIT FUNDS");
        System.out.println("-------------\n");
        System.out.print("Enter deposit amount: $ ");
        String amount = scanner.nextLine();
        transactionService.deposit(user, Double.parseDouble(amount));
    }

    private void withdraw(User user, Scanner scanner) {
        System.out.println("\nWITHDRAW FUNDS");
        System.out.println("--------------\n");
        System.out.print("Enter withdraw amount: $ ");
        String amount = scanner.nextLine();
        transactionService.withdraw(user, Double.parseDouble(amount));
    }

    private void transfer(User user, Scanner scanner) {
        System.out.println("\nTRANSFER FUNDS");
        System.out.println("------------------\n");

        System.out.print("Enter amount to transfer: ");
        String amount = scanner.nextLine();

        System.out.print("Enter account number to transfer funds: ");
        String accountID = scanner.nextLine();
        User targetUser = userService.findById(Integer.parseInt(accountID));

        transactionService.transfer(user, Double.parseDouble(amount), targetUser);
    }

    private void viewTransactionHistory(User user) {
        System.out.println("\nTRANSACTION HISTORY");
        System.out.println("-------------------\n");

        List<Transaction> transactions = transactionService.findByUserId(user.getUserID());
        
        for (Transaction transaction : transactions) {
            System.out.println(transaction.toString());
        }
    }

    private void applyForCredit(User user, Scanner scanner) {
        System.out.println("\nAPPLY FOR CREDIT");
        System.out.println("------------------\n");

        System.out.print("Enter amount of credit: $ ");
        String amount = scanner.nextLine();

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setAmount(Double.parseDouble(amount));
        creditRequest.setUserId(user.getUserID());
        creditRequest.setStatus("Pending");
        creditRequestService.save(creditRequest);
    }
}
