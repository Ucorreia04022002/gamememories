package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.User;

public class UserService {

	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		list.add(new User(1, "Fred", "fred123"));
		list.add(new User(2, "João", "joao123"));
		list.add(new User(3, "Pedro", "pedro123"));
		return list;
		
	}
}
