package model.dao;

import java.util.List;

import model.entities.Game;
import model.entities.User;

public interface GameDao {

	 void insert (Game obj);
	 void update (Game obj);
	 void deleteById (Integer Id);
	 User findById (Integer Id);
	 List<Game> findAll();
	User findByIdHelp(Integer Id);
}
