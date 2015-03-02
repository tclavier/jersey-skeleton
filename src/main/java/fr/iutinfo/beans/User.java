package fr.iutinfo.beans;

public class User {
	
	private int id = 0;
	private long facebookId = 0;
	private String name;
	private String password;
	private String email;
	private String cookie;
	
	
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

	public String toString() {
		return id + ": " + name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean equals(Object other){
		return this.id == ((User)other).id;
	}

	public long getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(long facebookId) {
		this.facebookId = facebookId;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

}
