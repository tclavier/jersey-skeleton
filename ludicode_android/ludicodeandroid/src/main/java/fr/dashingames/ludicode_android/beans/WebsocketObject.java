package fr.dashingames.ludicode_android.beans;

import org.json.JSONObject;

import fr.dashingames.ludicode_android.utils.JsonUtils;

public class WebsocketObject {
	private int type;
	private boolean formServer;
	private String content;
	private String from;
	private User[] users;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isFormServer() {
		return formServer;
	}

	public void setFormServer(boolean formServer) {
		this.formServer = formServer;
	}
	
	public String toString() {
		JSONObject obj = JsonUtils.toJSON(this);
		return obj.toString();
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}
}
