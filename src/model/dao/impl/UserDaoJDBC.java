package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.UserDao;
import model.entities.User;

public class UserDaoJDBC implements UserDao {

	private Connection conn;
	
	public  UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO user"
					+"(UserId, UserName, UserPassword)"
					+"VALUES"
					+"(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, obj.getId());
			st.setString(2, obj.getNameUser());
			st.setString(3, obj.getPasswordUser());
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet result = st.getGeneratedKeys();
				if (result.next()) {
					int id = result.getInt(1);
					obj.setId(id);
					}
				DB.closeResultSet(result);
				}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
			}
		catch (SQLException e) {
			throw new DbException("No rows Affected!");
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE user "
					+"SET UserName = ?, UserPassword = ? "
					+"WHERE UserId = ?"
					);
			st.setString(1, obj.getNameUser());
			st.setString(2, obj.getPasswordUser());
			st.setInt(3, obj.getId());
			
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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM user WHERE UserId = ?");
			st.setInt(1, Id);		
			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("non existent id selected");
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
	public User findById(Integer Id) {
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

	@Override
	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM user ORDER BY UserId");
				rs = st.executeQuery();

				List<User> list = new ArrayList<>();

				while (rs.next()) {
					User obj = instatiateUser(rs);
					list.add(obj);
				}
				return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	
}
