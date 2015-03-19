package fr.iutinfo.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import fr.iutinfo.beans.WebsocketMessage;


@javax.websocket.server.ServerEndpoint("/irc/{cookie}")
public class MyServerEndpoint {
	private static Map<String, Session> connectedUsers = new HashMap<String, Session>();
	private String cookie;


	@OnOpen
	public void onOpen(Session session, @PathParam("cookie") String cookie){
		if(fr.iutinfo.utils.Session.isLogged(cookie)) {
			connectedUsers.put(cookie, session);
			this.cookie = cookie;
		} else {
			try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnMessage
	public void onMessage(String message, Session session){
		if(fr.iutinfo.utils.Session.isLogged(cookie)) {
			try {
				WebsocketMessage websocketMessage = new WebsocketMessage();
				websocketMessage.setContent(message);
				websocketMessage.setFormServer(false);
				websocketMessage.setFrom(fr.iutinfo.utils.Session.getUser(cookie).getName());

				for(Map.Entry<String, Session> entry : connectedUsers.entrySet()) {
					if(!entry.getKey().equals(cookie))
						entry.getValue().getBasicRemote().sendText(websocketMessage.toString());
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnClose
	public void onClose(Session session){
		System.out.println("Session " +session.getId()+" has ended");
		connectedUsers.remove(cookie);
	}
}
