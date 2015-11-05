package com.epam.training.auction_backend.util;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction_backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserTransferObject map(User user) {
        return new UserTransferObject(user.getId(), user.getUserName(), user.getPassword());
    }

    public static List<UserTransferObject> map(List<User> users) {
        return users.stream().map(UserMapper::map).collect(Collectors.toList());
    }
}
