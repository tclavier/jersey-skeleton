package fr.iutinfo.beans;

import com.google.gson.Gson;

public class WebsocketMessage {
	private String content;
	private String from;
	private boolean formServer;
	
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
