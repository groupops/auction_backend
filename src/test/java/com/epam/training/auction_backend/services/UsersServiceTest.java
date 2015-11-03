package com.epam.training.auction_backend.services;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction_backend.model.TestContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
public class UsersServiceTest {

	@Autowired
	private UsersService usersService;
	
	@Test
	public void userIsAddedToDatabase() {
		long userId = 1;
		String userName = "Vasya";
		String password = "Vasyas password";
		UserTransferObject userTransferObject = new UserTransferObject(userId, userName, password);
		usersService.addUser(userTransferObject);
		
		Optional<UserTransferObject> actualUserTransferObject = usersService.getUserByName(userName);
		assertEquals(Optional.of(userTransferObject), actualUserTransferObject);
	}
}
