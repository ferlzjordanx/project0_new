package org.example.repository;

import org.example.model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {
    List<User> userList = new ArrayList<>();

    public void save (User user){
        if(user.getUserID() == null){
            user.setUserID(userList.size());
            userList.add(user);
        }else{
            userList.set(user.getUserID(), user);
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public void saveToFile(){

        try(FileWriter writer = new FileWriter("user.txt", false)){
            for(User user : userList){
                writer.write(user.getUserID() + "," + user.getName() + "," + user.getProfile()
                        + "," + user.getBalance() + "\n");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void loadFile(){

        try(FileReader reader = new FileReader("user.txt")){
            Scanner scanner = new Scanner(reader);
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                User user = new User();
                user.setUserID(Integer.parseInt(fields[0]));
                user.setName(fields[1]);
                user.setProfile(fields[2]);
                user.setBalance(Double.parseDouble(fields[3]));
                userList.add(user);
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public User findByUsername(String username){
        for(User user : userList){
            if(user.getName().equals(username)){
                return user;
            }
        }   return null;
    }

}

