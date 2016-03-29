package fr.dashingames.ludicode_android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.gui.adapters.UserBaseAdapter;
import fr.dashingames.ludicode_android.network.ChatClient;

/**
 * Created by Nico on 29/03/2016.
 */
public class SuiviActivity extends Activity {

    private ArrayList<String> connectedUsers = new ArrayList<String>();
    private ArrayAdapter<String> usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi);

        ajoutUsersSuivi();

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
    };

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }



    public void onRetourSuiviClick(View view) { setContentView(R.layout.activity_main); }

    public void ajoutUsersSuivi(){


        ArrayList<User> userList = new ArrayList<User>();//GetSearchResults();
        for(String s : connectedUsers){
            User u = new User();
            u.setName(s);
            userList.add(u);
        }

        final ListView lv1 = (ListView) findViewById(R.id.listViewSuivi);
        lv1.setAdapter(new UserBaseAdapter(this, userList));

    }
    /*
    private ArrayList<User> GetSearchResults(){
        ArrayList<User> results = new ArrayList<User>();


        User sr1 = new User();
        sr1.setName("John Smith");
        results.add(sr1);

        sr1 = new User();
        sr1.setName("Hugo Alder");
        results.add(sr1);

        sr1 = new User();
        sr1.setName("Matthieu Cassin");
        results.add(sr1);

        sr1 = new User();
        sr1.setName("Nicolas Brulard");
        results.add(sr1);


        return results;
    }
    */

    public void updateConnectedUsers(ArrayList<String> users) {
        connectedUsers.clear();
        connectedUsers.addAll(users);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                usersAdapter.notifyDataSetChanged();
            }

        });
    }

}
