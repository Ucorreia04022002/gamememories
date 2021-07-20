package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class UserService {
	
	private UserDao userDao = DaoFactory.createUserDao();

	public List<User> findAll() {
		return userDao.findAll();
	}
}
