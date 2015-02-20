package fr.iutinfo.bins;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;

	private int levelId;
	private String name;
	private String author;
	private ArrayList<ArrayList<Integer>> content;
	
	public Level() {
		this(0);
	}
	
	public Level(int levelId) {
		this.levelId = levelId;
		setContent(new ArrayList<ArrayList<Integer>>());
		for(int i = 0 ; i < 4 ; i++) {
			content.add(new ArrayList<Integer>());
			for(int j = 0 ; j < 4 ; j++) {
				content.get(i).add(0);
			}
		}
	}

	public ArrayList<ArrayList<Integer>> getContent() {
		return content;
	}

	public void setContent(ArrayList<ArrayList<Integer>> level) {
		this.content = level;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
