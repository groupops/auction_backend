package com.epam.training.auction.services;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction.model.TestContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
public class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    @Test
    public void userIsAddedToDatabase() {
        String userName = "Vasya";
        String password = "Vasyas password";
        UserTransferObject userTransferObject = new UserTransferObject(userName, password);
        usersService.addUser(userTransferObject);

        UserTransferObject actualUserTransferObject = usersService.getUserByName(userName);
        assertEquals(userTransferObject.getPassword(), actualUserTransferObject.getPassword());
        assertEquals(userTransferObject.getUsername(), actualUserTransferObject.getUsername());
        assertNotNull(actualUserTransferObject.getId());
    }
}
