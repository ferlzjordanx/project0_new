package org.example.presentation;

import java.util.Scanner;

import org.example.model.User;
import org.example.service.UserService;

public class LoginScreen {

    private UserService userService;

    public LoginScreen(UserService userService) {
        this.userService = userService;
    }

    public User login() {
        System.out.println("\nLOGIN");
        System.out.println("-----\n");
        User user = null;
        Scanner scanner = new Scanner(System.in);

        while (user == null) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            user = userService.findByUsername(username);
        }

        return user;
    }
}
