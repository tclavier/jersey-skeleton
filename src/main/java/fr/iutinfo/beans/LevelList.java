package fr.iutinfo.beans;

import java.util.List;

public class LevelList {
	private int id;
	private String name;
	private List<LevelListAssociation> levelsAssociation;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public List<LevelListAssociation> getLevelsAssociation() {
		return levelsAssociation;
	}

	public void setLevelsAssociation(List<LevelListAssociation> levelsAssociation) {
		this.levelsAssociation = levelsAssociation;
	}
	
}
