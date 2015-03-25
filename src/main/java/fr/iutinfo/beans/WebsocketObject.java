package fr.iutinfo.beans;

import com.google.gson.Gson;

public class WebsocketObject {
	private int type;
	private boolean formServer;


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
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
