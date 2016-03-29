package fr.dashingames.ludicode_android.network;

import java.net.URI;
import java.util.ArrayList;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONException;
import org.json.JSONObject;

import fr.dashingames.ludicode_android.activities.ChatActivity;
import fr.dashingames.ludicode_android.activities.SplashActivity;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.beans.WebsocketObject;
import fr.dashingames.ludicode_android.utils.JsonUtils;
import android.os.AsyncTask;

/**
 * Gestionnaire du client de chat
 *
 */
public class ChatClient {

	private User user;
	private ChatActivity activity;
	private Session session;

	public ChatClient(ChatActivity activity, User user) {
		this.user = user;
		this.activity = activity;
	}

	/**
	 * Connecte le client au serveur de chat
	 */
	public void connect() {
		final AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... voids) {
				try {
					final ClientManager client = ClientManager.createClient();

					client.connectToServer(new Endpoint() {
						@Override
						public void onOpen(Session session, EndpointConfig EndpointConfig) {
							ChatClient.this.session = session;
							initSession();
						}

					}, ClientEndpointConfig.Builder.create().build()
					, new URI("ws://" + SplashActivity.SERVER_URL_NO_PROTOCOL + "/irc/" + user.getCookie()));

				} catch (DeploymentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};

		asyncTask.execute();
	}
	
	/**
	 * Initialise la session
	 */
	private void initSession() {
		try {
			session.addMessageHandler(new MessageHandler.Whole<String>() {
				@Override
				public void onMessage(String message) {
					WebsocketObject obj = null;
					try {
						obj = (WebsocketObject) JsonUtils.populateObjectFromJSON(WebsocketObject.class, new JSONObject(message));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (obj.getType() == 2) {
						activity.addMessage(obj.getFrom() + ": " + obj.getContent());
					} else if (obj.getType() == 1) {
						User[] users = obj.getUsers();
						ArrayList<String> list = new ArrayList<String>();
						for (User user : users)
							list.add(user.getName());
						activity.updateConnectedUsers(list);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envoie un message au serveur
	 * @param ch message
	 */
	public void sendMessage(String ch) {
		String message = user.getName() + ": " + ch;
		final AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
			@Override
			protected Void doInBackground(String... strings) {
				try {
					session.getBasicRemote().sendText(strings[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		asyncTask.execute(ch);
		activity.addMessage(message);
	}
}
