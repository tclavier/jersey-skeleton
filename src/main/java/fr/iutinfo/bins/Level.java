package fr.iutinfo.bins;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String author;
	private String content;
	
	public Level() {
		this(0);
	}
	
	public Level(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String level) {
		this.content = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
