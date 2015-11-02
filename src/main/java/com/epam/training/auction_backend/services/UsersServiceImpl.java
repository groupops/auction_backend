package com.epam.training.auction_backend.services;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public final class UsersServiceImpl implements UsersService {

    private static final Logger logger = Logger.getLogger(UsersServiceImpl.class);

    @Autowired
    private UserRepository repository;

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public void addUser(UserTransferObject userTransferObject) {
        User user = new User(userTransferObject.getUsername(), userTransferObject.getPassword());
        repository.save(user);
        logger.info("User " + user.getUserName() + " is successfully created.");
    }

    public Optional<UserTransferObject> getUserById(long id) {
        User user = repository.findOne(id);
        Optional<UserTransferObject> userTransferObject;
        if (user == null) {
            userTransferObject = Optional.empty();
        } else {
            userTransferObject = Optional.of(new UserTransferObject(user.getId(), user.getPassword(), user.getUserName()));
        }
        return userTransferObject;
    }

    public Optional<UserTransferObject> getUserByName(String userName) {
        User user = repository.findByUserName(userName);
        Optional<UserTransferObject> userTransferObject;
        if (user == null) {
            userTransferObject = Optional.empty();
        } else {
            userTransferObject = Optional.of(new UserTransferObject(user.getId(), user.getPassword(), user.getUserName()));
        }
        return userTransferObject;
    }
}
