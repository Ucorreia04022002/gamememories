package model.entities;

import java.util.Date;

public class Game {

	private Integer Id, gameprice;
	private String gameName;
	private Date releaseDate;
	private User User;
	
	
	public Game() {
		
	}


	public Game(Integer id, Integer gameprice, String gameName, Date releaseDate, model.entities.User user) {
		
		this.setId(id);
		this.setGameprice(gameprice);
		this.setGameName(gameName);
		this.setReleaseDate(releaseDate);
		this.setUser(user);
	}
	
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getGameprice() {
		return gameprice;
	}
	public void setGameprice(Integer gameprice) {
		this.gameprice = gameprice;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public Date getReleaseDate() {
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
