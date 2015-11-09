package com.epam.training.auction.services;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction.entity.User;
import com.epam.training.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "usersServiceImpl")
public final class UsersServiceImpl implements UsersService {
    private static final long serialVersionUID = -338971724443291928L;
    private final UserRepository repository;

    @Autowired
    public UsersServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public Long addUser(UserTransferObject userTransferObject) {
        User user = new User(userTransferObject.getUsername(), userTransferObject.getPassword());
        //TODO: check if user exist
        user = repository.save(user);
        return user.getId();
    }

    public Optional<UserTransferObject> getUserById(long id) {
        return convertToTransferObject(repository.findOne(id));
    }

    public Optional<UserTransferObject> getUserByName(String userName) {
        return convertToTransferObject(repository.findByUserName(userName));
    }

    private Optional<UserTransferObject> convertToTransferObject(User user) {
        Optional<UserTransferObject> userTransferObject;
        if (user == null) {
            userTransferObject = Optional.empty();
        } else {
            userTransferObject = Optional.of(new UserTransferObject(user.getId(), user.getUserName(), user.getPassword()));
        }
        return userTransferObject;
    }
}
