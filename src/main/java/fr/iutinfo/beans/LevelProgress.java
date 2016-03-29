package fr.iutinfo.beans;

/**
 * Associate a User with the id of a level he suceeded in
 * @author Florent
 */
public class LevelProgress {

    private int idUser;
    private int idLevel;

    /**
     * Empty constructor
     */
    public LevelProgress() { }
	
    /**
     * Constructor of LevelProgress
     * @param idUser
     * @param idLevel 
     */
    public LevelProgress(int idUser, int idLevel) {
    	super();
	this.idUser = idUser;
	this.idLevel = idLevel;
    }
        
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
	
    public int getIdLevel() { return idLevel; }
    public void setIdLevel(int idLevel) { this.idLevel = idLevel; }
	
}
