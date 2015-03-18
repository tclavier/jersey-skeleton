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


@javax.websocket.server.ServerEndpoint("/echo/{name}")
public class MyServerEndpoint {
	private static Map<String, Session> connectedUsers = new HashMap<String, Session>();
	private String name;
	
	/**
	 * @OnOpen allows us to intercept the creation of a new session.
	 * The session class allows us to send data to the user.
	 * In the method onOpen, we'll let the user know that the handshake was 
	 * successful.
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("name") String name){
		System.out.println(name + " has opened a connection");
		connectedUsers.put(name, session);
		this.name = name;
	}

	/**
	 * When a user sends a message to the server, this method will intercept the message
	 * and allow us to react to it. For now the message is read as a String.
	 */
	@OnMessage
	public void onMessage(String message, Session session){
		System.out.println("Message from " + session.getId() + " : " + message);
		try {
			for(Map.Entry<String, Session> entry : connectedUsers.entrySet()) {
				entry.getValue().getBasicRemote().sendText(message);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session){
		System.out.println("Session " +session.getId()+" has ended");
		connectedUsers.remove(name);
	}
}
