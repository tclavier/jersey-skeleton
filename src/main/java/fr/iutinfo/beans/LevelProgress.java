package fr.iutinfo.beans;

public class LevelProgress {

	private int idUser;
	private int idLevel;
	
	public LevelProgress() {
		
	}
	
	public LevelProgress(int idUser, int idLevel) {
		super();
		this.idUser = idUser;
		this.idLevel = idLevel;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdLevel() {
		return idLevel;
	}
	public void setIdLevel(int idLevel) {
		this.idLevel = idLevel;
	}
	
}
