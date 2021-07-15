package model.entities;

import java.util.Date;

public class Game {

	private Integer Id;
	private double gameprice;
	private String gameName;
	private Date releaseDate;
	private User User;
	private boolean condition;
	
	
	public Game() {
		
	}


	public Game(Integer id, double gameprice, String gameName, Date releaseDate, model.entities.User user, boolean condition) {
		
		this.setId(id);
		this.setGameprice(gameprice);
		this.setGameName(gameName);
		this.setReleaseDate(releaseDate);
		this.setUser(user);
		this.setCondition(condition);
	}
	
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public double getGameprice() {
		return gameprice;
	}
	public void setGameprice(double gameprice2) {
		this.gameprice = gameprice2;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public  Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public User getUser() {
		return User;
	}
	public void setUser(User user) {
		User = user;
	}
	public boolean getCondition() {
		return condition;
	}
	public void setCondition(boolean condition) {
		this.condition = condition;
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
	
	
	
	
	
}
