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
	
	public void saveOrUpdate(User obj) {
		if (obj.getId() == null) {
			userDao.insert(obj);
		}
		else {
			userDao.update(obj);
		}
	}
	public void saveUser(User obj) {
		userDao.insert(obj);
	}
	public void updateUser(User obj) {
		userDao.update(obj);
	}
}
