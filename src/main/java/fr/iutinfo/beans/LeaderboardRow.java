package fr.iutinfo.beans;

/**
 * Associate the name of something with the number of level succeeded by a user
 * @author Florent
 */
public class LeaderboardRow {
	private String name;
	private int countLevel;
	private int idUser;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
        
	public int getCountLevel() { return countLevel; }
	public void setCountLevel(int countLevel) { this.countLevel = countLevel; }
	
        public int getIdUser() { return idUser; }
	public void setIdUser(int idUser) { this.idUser = idUser; }

}
