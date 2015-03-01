package fr.iutinfo.utils;

import java.util.HashMap;

import fr.iutinfo.beans.User;

public class Session {
	private static HashMap<String, User> loggedUsers = new HashMap<String, User>();
	
	private Session() {
		
	}
	
	public static void addUser(String id, User u) {
		loggedUsers.put(id, u);
	}
	
	public static void removeUser(String id) {
		loggedUsers.remove(id);
	}
	
	public static User getUser(String id) {
		return loggedUsers.get(id);
	}
	
	public static boolean isLogged(String id) {
		return loggedUsers.containsKey(id);
	}
	
	public static boolean isLogged(User u) {
		return loggedUsers.containsValue(u);
	}
}
