package fr.dashingames.ludicode_android.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.dashingames.ludicode_android.utils.JsonUtils;

/**
 * Created by Nico on 25/03/2016.
 */
//ajout perso
public class UserList {

    WebsocketObject obj = null;
    private User[] usersList = obj.getUsers();


    public UserList(){
        obj = (WebsocketObject) JsonUtils.populateObjectFromJSON(WebsocketObject.class, new JSONObject());
    }

    public ArrayList<User> getUsersList(){
        ArrayList<User> list = new ArrayList<User>();
        for (User user : usersList){
            list.add(user);
        }
        return list;
    }

    public ArrayList<String> getUsersListName(){
        ArrayList<String> list = new ArrayList<String>();
        for (User user : usersList){
            list.add(user.getName());
        }
        return list;
    }



}
