package org.example.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }

    @Test
    public void getUserID() {
        User user = new User();
        assertEquals("5", user.getUserID());

    }

//    @Test
//    public void setUserID() {
//    }

//    @Test
//    public void getName() {
//    }
//
//    @Test
//    public void setName() {
//    }
//
//    @Test
//    public void getProfile() {
//    }
//
//    @Test
//    public void setProfile() {
//    }
//
//    @Test
//    public void getBalance() {
//    }
//
//    @Test
//    public void setBalance() {
//    }
//
//    @Test
//    public void testToString() {
//    }
}