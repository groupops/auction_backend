package com.epam.training.auction_backend.services;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction_backend.entity.User;

@Service(value = "usersServiceImpl")
public final class UsersServiceImpl implements UsersService {

	private static final Logger logger = Logger.getLogger(UsersServiceImpl.class);
	

	private UserRepository repository;
	
	public void addUser(UserTransferObject userTransferObject) {
//		User user = new User(userTransferObject.getUsername(), userTransferObject.getPassword());
//		repository.save(user);
//		logger.info("User " + user.getUserName() + " is successfully created.");
	    System.out.println("SUCESSFUL CALL");
	}

	public Optional<UserTransferObject> getUserById(long id) {
		return repository.findOne(id);
	}

	public Optional<UserTransferObject> getUserByName(String userName) {
		return repository.findByUserName(userName);
	}
}
