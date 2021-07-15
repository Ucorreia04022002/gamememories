package model.dao;

import db.DB;
import model.dao.impl.GameDaoJDBC;
import model.dao.impl.UserDaoJDBC;

public class DaoFactory {

	public static UserDao createUserDao() {
		return new UserDaoJDBC(DB.getConnection());
	}
	public static GameDao createGameDao() {
		return new GameDaoJDBC(DB.getConnection());
	}
}
