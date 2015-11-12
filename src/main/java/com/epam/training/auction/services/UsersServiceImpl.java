package com.epam.training.auction.services;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction.entity.User;
import com.epam.training.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserTransferObject getUserById(long id) {
        return convertToTransferObject(repository.findOne(id));
    }

    public UserTransferObject getUserByName(String userName) {
        return convertToTransferObject(repository.findByName(userName));
    }

    private UserTransferObject convertToTransferObject(User user) {
        return user == null ? null : new UserTransferObject(user.getId(), user.getUserName(), user.getPassword());
    }
}
