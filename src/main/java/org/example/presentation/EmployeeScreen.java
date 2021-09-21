package org.example.presentation;

import org.example.model.CreditRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.CreditRequestService;
import org.example.service.UserService;

import java.util.Scanner;

public class EmployeeScreen {

    private UserService userService;
    private CreditRequestService creditRequestService;

    public EmployeeScreen(UserService userService, CreditRequestService creditRequestService) {
        this.userService = userService;
        this.creditRequestService = creditRequestService;
    }

    public void showMenu() {
        String option = "";

        while (!option.equals("5")) {
            System.out.println("\nEMPLOYEE MENU");
            System.out.println("-----------------\n");

            System.out.println("1. Print customers");
            System.out.println("2. Insert new customer");
            System.out.println("3. Insert new employee");
            System.out.println("4. Analyse credit requests");
            System.out.println("5. Exit");
            System.out.print("\n Enter option: ");

            Scanner scanner = new Scanner(System.in);
            option = scanner.nextLine();

            if (option.equals("1")) {
                printCustomers();
            } else if (option.equals("2")) {
                insertCustomer(scanner);
            } else if (option.equals("3")) {
                insertEmployee(scanner);
            } else if (option.equals("4")) {
                analyseCredit(scanner);
            }

            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
        }
    }

    private void printCustomers() {
        System.out.println("\nCUSTOMERS LIST");
        System.out.println("------------------\n");

        userService.getUserList().forEach(user -> {
            if (user.getProfile().equals("Customer")) {
                System.out.println(user);
            }
        });
    }

    private void insertCustomer(Scanner scanner) {
        System.out.println("\nINSERT CUSTOMER");
        System.out.println("-------------------\n");

        User user = new User();
        System.out.print("Name: ");
        user.setName(scanner.nextLine());
        user.setProfile("Customer");
        userService.save(user);
    }

    private void insertEmployee(Scanner scanner) {
        System.out.println("\nINSERT EMPLOYEE");
        System.out.println("-------------------\n");

        User user = new User();
        System.out.print("Name: ");
        user.setName(scanner.nextLine());
        user.setProfile("Employee");
        userService.save(user);
    }

    private void analyseCredit(Scanner scanner) {
        System.out.println("\nANALYSE CREDIT");
        System.out.println("------------------\n");

        System.out.print("Inform the user ID: ");
        String userId = scanner.nextLine();

        CreditRequest creditRequest = creditRequestService.findByUserId(Integer.parseInt(userId));

        if (creditRequest != null) {
            // risk score : Excellet 700-800; Good: 550-699; Poor: 450-549; Bad: 300-449
            double risk = Math.random()*800.0;
            System.out.println("This user has a risk score of: " + risk);
            if(risk >=700){
                System.out.println("Credit approved automatically with a Low Rate");
                creditRequest.setStatus("Approved");
                creditRequestService.save(creditRequest);
                User user = userService.findById(Integer.parseInt(userId));
                user.setBalance(user.getBalance() + creditRequest.getAmount());
                userService.save(user);
            }else if(risk >=550){
                System.out.println("Credit approved automatically with a High Rate");
                creditRequest.setStatus("Approved");
                creditRequestService.save(creditRequest);
                User user = userService.findById(Integer.parseInt(userId));
                user.setBalance(user.getBalance() + creditRequest.getAmount());
                userService.save(user);
            }else if(risk >=450){
                System.out.println("Credit requested: " + creditRequest.getAmount());
                System.out.println("Approve credit?");
                System.out.println("1. Yes");
                System.out.println("2. No");

                System.out.print("Enter option: ");
                String approval = scanner.nextLine();

                if (approval.equals("1")) {
                    creditRequest.setStatus("Approved");
                    creditRequestService.save(creditRequest);

                    User user = userService.findById(Integer.parseInt(userId));
                    user.setBalance(user.getBalance() + creditRequest.getAmount());
                    userService.save(user);
                } else if (approval.equals("2")) {
                    creditRequest.setStatus("Canceled");
                    creditRequestService.save(creditRequest);
                }
            }
            else{
                System.out.println("Credit could not be approved");
                creditRequest.setStatus("Canceled");
                creditRequestService.save(creditRequest);
            }

        } else {
            System.out.println("No credit request found.");
        }
    }
}
