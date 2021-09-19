package org.example;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.presentation.UserScreen;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

import java.util.List;
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
        String option = "";
        Scanner scanner = new Scanner(System.in);
        while (!option.equals("7")) {
            System.out.println("\n\n---------------------------------");
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

            if(option.equals("1")){   //<-----------------------------------------*1
                System.out.println("_______________________________________");
                System.out.println("_______      VIEW  BALANCE      _______");
                System.out.println("_______________________________________\n");
                System.out.print("Your current balance is : ");
                System.out.println(user.getBalance());
                System.out.println("______________________________________\n\n");
                promptEnterKey();
            }else if (option.equals("2")) {   //<-----------------------------------------*3
                System.out.println("_______________________________________");
                System.out.println("_______      DEPOSIT FUNDS      _______");
                System.out.println("_______________________________________\n");
                System.out.print("Enter deposit amount:");
                String amount = scanner.nextLine();

                Transaction t;
                t = new Transaction();
                t.setUserID(user.getUserID());
                t.setAmount(Double.parseDouble(amount));
                transactionRepository.save(t);
                user.setBalance(user.getBalance() + Double.parseDouble(amount));
                userRepository.save(user);
                promptEnterKey();
            }else if (option.equals("3")) {   //<-----------------------------------------*3
                System.out.println("_______________________________________");
                System.out.println("______      WITHDRAW  FUNDS      ______");
                System.out.println("_______________________________________\n");
                System.out.print("Enter withdrawal amount:");
                String amount = scanner.nextLine();
                Transaction t;
                t = new Transaction();
                if (user.getBalance() >= Double.parseDouble(amount)) {
                    t.setUserID(user.getUserID());
                    t.setAmount(Double.parseDouble(amount));
                    transactionRepository.save(t);
                    user.setBalance(user.getBalance() - Double.parseDouble(amount));
                    userRepository.save(user);

                }else{
                    System.out.println("Insufficient funds...");
                }
                System.out.println("_____________________________________\n");
                promptEnterKey();
           //TRANSFER... broke the txt files.  Creating null users  *****  not really working :(
            }else if(option.equals("4")){   //<-----------------------------------------*4
                System.out.println("_______________________________________");
                System.out.println("______      TRANSFER  FUNDS     _______");
                System.out.println("_______________________________________");

                System.out.print("Enter amount to transfer: ");
                String amount = scanner.nextLine();

                System.out.print("Enter account number to transfer funds: ");
                Integer accountID = scanner.nextInt();    scanner.nextLine();

                if(user.getBalance() <= Double.parseDouble(amount)){
                    Transaction t = new Transaction(); // origin t
                    Transaction targetTransaction = new Transaction(); //target t
                    User targetUser = new User();//target user

                    t.setUserID(user.getUserID());
                    t.setAmount(Double.parseDouble(amount));
                    transactionRepository.save(t);
                    user.setBalance(user.getBalance() - Double.parseDouble(amount));

                    userRepository.save(user);

                    //update target *****************************************************
                    targetUser.setUserID(accountID);
                    targetUser.setBalance(targetUser.getBalance() + Double.parseDouble(amount));
                    targetTransaction.setUserID(accountID);
                    targetTransaction.setAmount(Double.parseDouble(amount));
                    transactionRepository.save(targetTransaction);

                    userRepository.save(targetUser);

                }else{
                    System.out.println("msg....");
                }
                promptEnterKey();

            }else if(option.equals("5")){   //<-----------------------------------------*5
                System.out.println("_______________________________________");
                System.out.println("_____     TRANSACTION HISTORY     -----");
                System.out.println("_______________________________________");
                List<Transaction> temp = transactionRepository.findByUserId(user.getUserID());
                for(Transaction t : temp){
                    System.out.println(t.toString());
                }
                promptEnterKey();

            }else if(option.equals("6")){   //<-----------------------------------------*6
                System.out.println("_______________________________________");
                System.out.println("_____     CREDIT  APPLICATION     -----");
                System.out.println("_______________________________________");
                System.out.print("Enter credit line amount: ");
                String amount = scanner.nextLine();
                creditApplication(user, amount);
            }else{
                System.exit(0);
            }
            userRepository.saveToFile();
            transactionRepository.saveToFile();
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
    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}









