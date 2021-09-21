package org.example;

import org.example.model.User;
import org.example.presentation.CustomerScreen;
import org.example.presentation.EmployeeScreen;
import org.example.presentation.LoginScreen;
import org.example.repository.CreditRequestRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;
import org.example.service.CreditRequestService;
import org.example.service.TransactionService;
import org.example.service.UserService;

public class App {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        CreditRequestRepository creditRequestRepository = new CreditRequestRepository();
        
        UserService userService = new UserService(userRepository);
        TransactionService transactionService = new TransactionService(userService, transactionRepository);
        CreditRequestService creditRequestService = new CreditRequestService(creditRequestRepository);
        
        transactionRepository.loadFile();

        LoginScreen loginScreen = new LoginScreen(userService);
        User user = loginScreen.login();

        if (user.getProfile().equals("Customer")) {
            CustomerScreen customerScreen = new CustomerScreen(userService, transactionService, creditRequestService);
            customerScreen.showMenu(user);
        } else if (user.getProfile().equals("Employee")) {
            EmployeeScreen employeeScreen = new EmployeeScreen(userService, creditRequestService);
            employeeScreen.showMenu();
        }
    }
    

    public static void creditApplication(User user, String amount){
        //scores from 400 to 800
        //Excellent: 720-800 (gets lowRate) ; Good: 650-719 (gets highRate);
        // Poor:550-649 (goes for review) ; Bad: under 550 (credit denied)
        int low = 400; int high = 800;
        double highRate = 0.20;  double lowRate = 0.10;
        int creditScore = (int) Math.floor(Math.random()*(high-low+1)+low);
        System.out.println("Credit score: " + creditScore);
        if(creditScore >= 720){
            System.out.println("Credit Line approved (Interest rate of 10%");
        }else if(creditScore >= 650){
            System.out.println("Credit Line approved (Interest rate of 20%)");
        }else if( creditScore >= 550){
            System.out.println("Needs review for approval");
            //call method to review application
        }else{
            System.out.println("Credit application denied");
        }
    }
    
}









