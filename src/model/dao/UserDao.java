package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.entities.User;

public interface UserDao {

	 void insert (User obj);
	 User instatiateUser(ResultSet rs) throws SQLException;
	 void update (User obj);
	 void deleteById (Integer Id);
	 User findById (Integer Id);
	 List<User> findAll();
}
