package org.example.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {


    @Test
    public void testUserCreation() {
        User user = new User();
        user.setUserID(5);
        user.setName("Anna");
        user.toString();
        assertEquals(5, user.getUserID().intValue());
        assertEquals("Anna", user.getName());
    }






}