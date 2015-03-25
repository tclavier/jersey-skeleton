package fr.iutinfo.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import fr.iutinfo.beans.User;
import fr.iutinfo.beans.WebsocketConnectedUsers;
import fr.iutinfo.beans.WebsocketMessage;
import fr.iutinfo.beans.WebsocketObject;


@javax.websocket.server.ServerEndpoint("/irc/{cookie}")
public class MyServerEndpoint {
	private static Map<String, Session> connectedUsers = new HashMap<String, Session>();
	private String cookie;


	@OnOpen
	public void onOpen(Session session, @PathParam("cookie") String cookie){
		if(fr.iutinfo.utils.Session.isLogged(cookie)) {
			connectedUsers.put(cookie, session);
			this.cookie = cookie;
			
			broadcastObject(getConnectedUsers());
		} else {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private WebsocketConnectedUsers getConnectedUsers() {
		List<User> users = new ArrayList<User>();
		for(Map.Entry<String, Session> entry : connectedUsers.entrySet()) {
			users.add(fr.iutinfo.utils.Session.getUser(entry.getKey()));
		}
		WebsocketConnectedUsers wcu = new WebsocketConnectedUsers();
		wcu.setFormServer(true);
		wcu.setUsers(users);
		return wcu;
	}

	private void broadcastObject(WebsocketObject wo) {
		for(Map.Entry<String, Session> entry : connectedUsers.entrySet())
			sendObjectTo(wo, entry.getValue());
	}
	
	private void broadcastExceptUserObject(WebsocketObject wo) {
		for(Map.Entry<String, Session> entry : connectedUsers.entrySet())
			if(!entry.getKey().equals(cookie))
				sendObjectTo(wo, entry.getValue());
	}

	private void sendObjectTo(WebsocketObject o, Session session) {
		try {
			session.getBasicRemote().sendText(o.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, Session session){
		if(fr.iutinfo.utils.Session.isLogged(cookie)) {
			WebsocketMessage websocketMessage = new WebsocketMessage();
			websocketMessage.setContent(message);
			websocketMessage.setFormServer(false);
			websocketMessage.setFrom(fr.iutinfo.utils.Session.getUser(cookie).getName());

			broadcastExceptUserObject(websocketMessage);
		} else {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@OnClose
	public void onClose(Session session){
		connectedUsers.remove(cookie);
		
		broadcastObject(getConnectedUsers());
	}
}
