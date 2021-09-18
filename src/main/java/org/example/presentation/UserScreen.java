package org.example.presentation;

import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.Scanner;

public class UserScreen {

    private UserRepository repository;

    public UserScreen(UserRepository repository) {
        this.repository = repository;
    }

    public void insertCustomer() {
        String option = "";
        while (!option.equals("3")) {
            System.out.println("CUSTOMER MENU");
            System.out.println("1. Print customer");
            System.out.println("2. Insert new customer");
            System.out.println("3. Exit");

            Scanner scanner = new Scanner(System.in);

            option = scanner.nextLine();

            if (option.equals("1")) {
                repository.getUserList().forEach(user -> System.out.println(user));
            } else if (option.equals("2")) {
                User user = new User();
                System.out.print("Name: ");
                user.setName(scanner.nextLine());
                user.setProfile("Customer");
                repository.save(user);//user is in list
            }

        }
        repository.saveToFile();


    }

}
