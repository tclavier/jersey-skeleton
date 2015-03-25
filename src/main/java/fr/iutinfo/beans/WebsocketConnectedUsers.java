package fr.iutinfo.beans;

import java.util.List;

public class WebsocketConnectedUsers extends WebsocketObject {
	private List<User> users;
	
	public WebsocketConnectedUsers() {
		setType(1);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
