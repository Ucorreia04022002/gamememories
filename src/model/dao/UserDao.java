package model.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {

	 void insert (User obj);
	 void update (User obj);
	 void deleteById (Integer Id);
	 User findById (Integer Id);
	 List<User> findAll();
}
