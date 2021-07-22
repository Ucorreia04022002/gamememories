package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class UserService {
	
	private UserDao userDao = DaoFactory.createUserDao();
	
	
	public void saveOrUpdate(User obj) {
		List<User> userList = new ArrayList<>();
		userList = userDao.findAll();
		for(User user : userList) {
			if(user.getId() == obj.getId()) {
				userDao.update(obj);
			}
			else {
				userDao.insert(obj);
			}
		}
		
	}
	public void saveUser(User obj) {
		userDao.insert(obj);
	}
	public void updateUser(User obj) {
		userDao.update(obj);
	}
	public List<User> findAll() {
		return userDao.findAll();
	}
	public void remove(User obj) {
		userDao.deleteById(obj.getId());
	}
}
