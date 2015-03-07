package fr.iutinfo.beans;

import java.util.List;

public class ProfileInfo {
	private User user;
	private List<LevelInfo> levelsInfo;
	
	public ProfileInfo() {}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<LevelInfo> getLevelsInfo() {
		return levelsInfo;
	}
	
	public void setLevelsInfo(List<LevelInfo> levelsInfo) {
		this.levelsInfo = levelsInfo;
	}
	
	
}
