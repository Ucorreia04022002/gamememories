package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.GameDao;
import model.entities.Game;

public class GameService {
	
	private GameDao gameDao = DaoFactory.createGameDao();
	
	
	public void saveOrUpdate(Game obj) {
		List<Game> userList = new ArrayList<>();
		userList = gameDao.findAll();
		for(Game user : userList) {
			if(user.getId() == obj.getId()) {
				gameDao.update(obj);
			}
			else {
				gameDao.insert(obj);
			}
		}
		
	}
	public void saveGame(Game obj) {
		gameDao.insert(obj);
	}
	public void updateGame(Game obj) {
		gameDao.update(obj);
	}
	public List<Game> findAll() {
		return gameDao.findAll();
	}
	public List<Game> findAllCondition(){
		List<Game> listCondition = new ArrayList<Game>();
		List<Game> normalList = new ArrayList<Game>();
		normalList = gameDao.findAll();
		for(Game game : normalList) {
			if(game.getCondition() == true) {
				listCondition.add(game);
			}
		}
		return listCondition;
	}
	public List<Game> findAllNoCondition(){
		List<Game> listCondition = new ArrayList<Game>();
		List<Game> normalList = new ArrayList<Game>();
		normalList = gameDao.findAll();
		for(Game game : normalList) {
			if(game.getCondition() == false) {
				listCondition.add(game);
			}
		}
		return listCondition;
	}
	public void remove(Game obj) {
		gameDao.deleteById(obj.getId());
	}
}
