package fr.dashingames.ludicode_android.activities;

import java.util.ArrayList;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.network.ChatClient;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends Activity {
	
	private User user;
	private ChatClient test;
	private ListView conversationView;
	private ListView usersView;
	private ArrayAdapter<String> conversationAdapter;
	private ArrayAdapter<String> usersAdapter;
	private ArrayList<String> sentences = new ArrayList<String>();
	private ArrayList<String> connectedUsers = new ArrayList<String>();
	private EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		conversationView = (ListView) findViewById(R.id.chatList);
		usersView = (ListView) findViewById(R.id.usersList);
		text = (EditText) findViewById(R.id.chatText);
		
		user = getIntent().getExtras().getParcelable(MainActivity.USER);
		test = new ChatClient(this, user);
		
		conversationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sentences);
		conversationView.setAdapter(conversationAdapter);
		
		usersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, connectedUsers);
		usersView.setAdapter(usersAdapter);
		
		test.connect();
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		user = bundle.getParcelable(MainActivity.USER);
	};

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putParcelable(MainActivity.USER, user);
	}

	public void addMessage(String s) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				conversationAdapter.notifyDataSetChanged();
				conversationView.setSelection(conversationAdapter.getCount() - 1);
			}

		});
		sentences.add(s);
	}
	
	public void sendMessage(View view) {
		test.sendMessage(text.getText().toString());
		text.setText("");
	}
	
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
