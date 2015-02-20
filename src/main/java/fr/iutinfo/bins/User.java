package fr.iutinfo.bins;

public class User {
	
	private int id = 0;
	private String name;
	
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public User() {
		
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

	public boolean equals(Object u) {
		return name.equals(((User) u).name);
	}

	public String toString() {
		return id + ": " + name;
	}

}
