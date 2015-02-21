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

	public void setContent(String content) {
		this.content = content;
	}
	
	private Integer[][] parseLevel(String content) {
		ArrayList<ArrayList<Integer>> structuredContent = new ArrayList<ArrayList<Integer>>();
		String[] lines = content.split(",");
		for(String line : lines) {
			String[] cells = line.split("\\s+");
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(String cell : cells) {
				list.add(Integer.parseInt(cell));
			}
			structuredContent.add(list);
		}
		Integer[][] array = new Integer[structuredContent.size()][structuredContent.get(0).size()];
		for(int i = 0 ; i < structuredContent.size() ; i++) {
			structuredContent.get(i).toArray(array[i]);
		}
		return array;
	}
	
	private String serializeContent(Integer[][] structuredContent) {
		String content = "";
		for(int i = 0 ; i < structuredContent.length ; i++) {
			if(i != 0)
				content += ",";
			for(int j = 0 ; j < structuredContent.length ; j++) {
				if(j != 0)
					content += " ";
				content += structuredContent[i][j];
			}
		}
		return content;
	}
	
	public void setStructuredContent(Integer[][] structuredContent) {
		content = serializeContent(structuredContent);
	}

	public Integer[][] getStructuredContent() {
		return parseLevel(content);
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
