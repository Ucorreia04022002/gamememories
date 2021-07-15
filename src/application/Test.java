package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.GameDao;
import model.dao.UserDao;
import model.entities.Game;
import model.entities.User;

public class Test {

	public static void main(String[] args) {
		
		
		UserDao userdao = DaoFactory.createUserDao();
		User user = userdao.findById(3);
		
		GameDao gameDao = DaoFactory.createGameDao();
		List<Game> list = gameDao.findAll();
		for(Game game : list) {
			System.out.println(game);
		}
		 
		
		
	}

	

}
