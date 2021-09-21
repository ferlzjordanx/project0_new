package org.example.repository;

import org.example.model.User;


import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    @Test
    public void testSaveUser(){
        UserRepository userRepository = new UserRepository();
        User user = new User();
        user.setBalance(200.0);
        userRepository.save(user);
        userRepository.saveToFile();
        assertNotNull(user.getUserID());
        assertEquals(200.0, user.getBalance());
        userRepository.loadFile();

    }




}