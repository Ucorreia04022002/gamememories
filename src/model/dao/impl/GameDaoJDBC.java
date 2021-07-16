package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.GameDao;
import model.entities.Game;
import model.entities.User;

public class GameDaoJDBC implements GameDao{

	private Connection conn;
	
	
	public  GameDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Game obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO game "
					+"(GameId, GamePrice, GameName, ReleaseDate, GameUserId, GameCondition) "
					+"VALUES "
					+"(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getId());
			st.setDouble(2, obj.getGameprice());
			st.setString(3, obj.getGameName());
			st.setDate(4, new java.sql.Date(obj.getReleaseDate().getTime()));
			st.setInt(5, obj.getUser().getId());
			st.setBoolean(6, obj.getCondition());
			
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows Affected!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
		
	}

	@Override
	public void update(Game obj) {
		PreparedStatement st =  null;
		try {
			st = conn.prepareStatement(
					"UPDATE game "
					+"SET GameId = ?, GamePrice = ?, GameName = ?, ReleaseDate = ?, GameUserId, GameCondition = ? "
					+"WHERE GameId = ? "
					);
			st.setInt(1, obj.getId());;
			st.setDouble(2, obj.getGameprice());
			st.setString(3, obj.getGameName());
			st.setDate(4, new java.sql.Date(obj.getReleaseDate().getTime()));
			st.setInt(5, obj.getUser().getId());
			st.setBoolean(6, obj.getCondition());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public void deleteById(Integer Id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game findById(Integer Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM game ORDER BY GameId "
					);
			rs = st.executeQuery();
			List<Game> list = new ArrayList<>();
			
			while(rs.next()) {
				Game game = instatiateGame(rs, findById(rs.getInt("GameUserId")));
				list.add(game);
				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	private Game instatiateGame(ResultSet rs,User user) throws SQLException {
		Game game = new Game();
		game.setId(rs.getInt("GameId"));
		game.setGameprice(rs.getDouble("GamePrice"));
		game.setGameName(rs.getString("GameName"));
		game.setReleaseDate(rs.getDate("ReleaseDate"));
		game.setUser(user);
		game.setCondition(rs.getBoolean("GameCondition"));
		return game;
		
	}
	@Override
	public User findByIdHelp(Integer Id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st= conn.prepareStatement(
					"SELECT * FROM user WHERE UserId = ? "
					);
			st.setInt(1, Id);
			rs = st.executeQuery();
			if(rs.next()) {
				User user = instatiateUser(rs);
				return user;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}

	private User instatiateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("UserId"));
		user.setNameUser(rs.getString("UserName"));
		user.setPasswordUser(rs.getString("UserPassword"));
		return user;
	}

}
