package fr.dashingames.ludicode_android.beans;

public class WebsocketConnectedUsers extends WebsocketObject {
	private User[] users;
	
	public WebsocketConnectedUsers() {
		setType(1);
	}

	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}
	
}
