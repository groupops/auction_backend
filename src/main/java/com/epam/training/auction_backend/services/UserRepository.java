package com.epam.training.auction_backend.services;

import com.epam.training.auction_backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    User findOne(Long id);

    User findByUserName(String userName);
}
