package fr.iutinfo;

public class User {
	private String name;
	private int id = 0;

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
