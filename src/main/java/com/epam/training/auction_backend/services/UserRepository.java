package com.epam.training.auction_backend.services;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction_backend.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public UserTransferObject save(UserTransferObject user);
	public Optional<UserTransferObject> findOne(long id);
	public Optional<UserTransferObject> findByUserName(String userName);
}
