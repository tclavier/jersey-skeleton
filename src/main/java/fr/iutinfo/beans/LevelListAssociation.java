package fr.iutinfo.beans;

/**
 * Associate one Level id with the id of the list he belongs to
 * @author Florent
 */
public class LevelListAssociation {
	private int idLevel;
	private int idList;
	
	public int getIdLevel() { return idLevel; }
	public void setIdLevel(int idLevel) { this.idLevel = idLevel; }
	
	public int getIdList() { return idList; }
	public void setIdList(int idList) { this.idList = idList; }
}
