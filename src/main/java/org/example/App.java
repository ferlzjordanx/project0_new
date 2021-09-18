package org.example;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.presentation.UserScreen;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        userRepository.loadFile();
        transactionRepository.loadFile();

        User user = login(userRepository);
        if (user.getProfile().equals("Customer")) {
            showCustomerMenu(user, userRepository, transactionRepository);
        }
    }

    public static User login(UserRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*****     ENTER USER CREDENTIALS     *****");
        System.out.println("------------------------------------------");
        User user = null;
        while (user == null) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            user = repository.findByUsername(username);
        }
        return user;
    }

    public static void showCustomerMenu(User user, UserRepository userRepository, TransactionRepository transactionRepository) {
        String option = " ";
        Scanner scanner = new Scanner(System.in);
        while (!option.equals("7")) {
            System.out.println("*****     CUSTOMER MENU     *****");
            System.out.println("---------------------------------");
            System.out.println("1. View balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Transfer");
            System.out.println("5. View transaction history");
            System.out.println("6. Apply for credit");
            System.out.println("7. Exit");
            System.out.print("\n Enter option: ");
            option = scanner.nextLine();

            if (option.equals("2")) {
                System.out.println("Enter deposit amount:");
                String amount = scanner.nextLine();
                Transaction t;
                t = new Transaction();
                t.setUserID(user.getUserID());
                t.setAmount(Double.parseDouble(amount));
                transactionRepository.save(t);
                user.setBalance(user.getBalance() + Double.parseDouble(amount));
                userRepository.save(user);
            }
            userRepository.saveToFile();
            transactionRepository.saveToFile();
        }

    }
}






