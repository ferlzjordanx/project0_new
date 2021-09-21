package org.example.service;

import java.util.List;

import org.example.model.User;
import org.example.repository.UserRepository;

public class UserService {
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        userRepository.loadFile();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
        userRepository.saveToFile();
    }

    public User findById(int id) {
        return userRepository.getUserList().get(id);
    }

    public List<User> getUserList() {
        return userRepository.getUserList();
    }
}
