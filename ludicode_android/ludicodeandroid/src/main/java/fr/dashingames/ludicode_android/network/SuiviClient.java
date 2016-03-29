package fr.dashingames.ludicode_android.network;

import android.os.AsyncTask;

import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import fr.dashingames.ludicode_android.activities.ChatActivity;
import fr.dashingames.ludicode_android.activities.SplashActivity;
import fr.dashingames.ludicode_android.activities.SuiviActivity;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.beans.WebsocketObject;
import fr.dashingames.ludicode_android.utils.JsonUtils;

/**
 * Created by Nico on 29/03/2016.
 */
public class SuiviClient {

    private User user;
    private SuiviActivity activity;
    private Session session;

    public SuiviClient(SuiviActivity activity, User user) {
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
                            SuiviClient.this.session = session;
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
                    User[] users = obj.getUsers();
                    ArrayList<String> list = new ArrayList<String>();
                    for (User user : users)
                        list.add(user.getName());
                    activity.updateConnectedUsers(list);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
