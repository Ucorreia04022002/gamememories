package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class Test {

	public static void main(String[] args) {
		
		
		UserDao userdao = DaoFactory.createUserDao();
		List<User> list = userdao.findAll();
		for (User user : list) {
			System.out.println(user);
		}
		
		
		
	}

}
