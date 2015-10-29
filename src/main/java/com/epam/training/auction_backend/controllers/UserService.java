package com.epam.training.auction_backend.controllers;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.epam.training.auction_backend.entity.User;

public class UserService {

	public static final Logger logger = Logger.getLogger(UserService.class);
	
	public long addUser(String userName, String password) {
		long id = 0;
		try(Session session = SessionProvider.getSession()) {
			User user = new User(userName, password);
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			id = user.getId();
			logger.info("User " + user.getUserName() + " is successfully created.");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return id;
	}
	
	public Optional<User> getUser(String userName, String password) {
		Optional<User> user = Optional.empty();
		try(Session session = SessionProvider.getSession()) {
			Query query = session.createQuery("FROM User U WHERE U.USERNAME = :userName and U.PASSWORD = :password");
			query.setParameter("userName", userName);
			query.setParameter("password", password);
			user = Optional.of((User) query.uniqueResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return user;
	}
}
