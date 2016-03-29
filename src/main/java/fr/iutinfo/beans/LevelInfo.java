package fr.iutinfo.beans;

public class LevelInfo {
	private int id;
	private int idAuthor;
	private String name;
	private String levelType;
	private boolean orientation;
	
	public LevelInfo() {
		
	}
	
	public String getlevelType() {return levelType;}
	public void setlevelType(String type) {this.levelType = type;}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public int getIdAuthor() {return idAuthor;}
	public void setIdAuthor(int idAuthor) {this.idAuthor = idAuthor;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public boolean getOrientation() {return this.orientation;}
	public void setOrientation(boolean orientation) {this.orientation=orientation;}
}
