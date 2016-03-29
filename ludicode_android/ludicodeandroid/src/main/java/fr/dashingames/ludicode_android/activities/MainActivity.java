package fr.dashingames.ludicode_android.activities;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.Feedback;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.beans.UserList;
import fr.dashingames.ludicode_android.gui.adapters.UserBaseAdapter;
import fr.dashingames.ludicode_android.network.HttpResponse;
import fr.dashingames.ludicode_android.utils.IdentificationHandler;
import fr.dashingames.ludicode_android.utils.JsonUtils;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Activité principale lancée au démarrage de l'application
 *
 */
public class MainActivity extends ActionBarActivity {

	public static final String USER = "User";
	private Dialog connectionDialog;
	private Dialog registrationDialog;

	private IdentificationHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handler = new IdentificationHandler(this);

		initConnectionDialog();
		initRegistrationDialog();
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		handler.setUser((User) bundle.getParcelable(USER));
	};

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putParcelable(USER, handler.getUser());
	}

	private void initConnectionDialog() {
		connectionDialog = new Dialog(this);
		connectionDialog.setContentView(R.layout.activity_login);
		connectionDialog.setTitle("Connexion");
		connectionDialog.setCancelable(true);

		Button connect = (Button) connectionDialog.findViewById(R.id.connectionButton);
		connect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String login = ((EditText) connectionDialog.findViewById(R.id.login)).getText().toString();
				String password = ((EditText) connectionDialog.findViewById(R.id.password)).getText().toString();

				int connectionResult = handler.tryToConnect(login, password);

				if (connectionResult == IdentificationHandler.CONNECTION_SUCCESS) {
					connectionDialog.dismiss();
				} else {
					handler.showMessage(connectionResult);
				}
			}

		});

		Button back = (Button) connectionDialog.findViewById(R.id.backButton);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				connectionDialog.dismiss();
			}
		});
	}

	private void initRegistrationDialog() {
		registrationDialog = new Dialog(this);
		registrationDialog.setContentView(R.layout.activity_register);
		registrationDialog.setTitle("Inscription");
		registrationDialog.setCancelable(true);

		Button register = (Button) registrationDialog.findViewById(R.id.registerButton);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String login = ((EditText) registrationDialog.findViewById(R.id.login)).getText().toString();
				String password = ((EditText) registrationDialog.findViewById(R.id.mdp)).getText().toString();
				String checkPassword = ((EditText) registrationDialog.findViewById(R.id.verif_mdp)).getText().toString();
				String mail = ((EditText) registrationDialog.findViewById(R.id.mail)).getText().toString();

				int resultCode = handler.tryToRegister(login, password, checkPassword, mail);

				if (resultCode == IdentificationHandler.REGISTRATION_SUCCESS) {
					registrationDialog.dismiss();
				} else {
					handler.showMessage(resultCode);
				}
			}
		});

		Button back = (Button) registrationDialog.findViewById(R.id.backButton);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				registrationDialog.dismiss();
			}
		});
	}
	//ajout perso
	public void onSuiviClick(View view){
		Intent intent = new Intent(view.getContext(), SuiviActivity.class);
		//if (handler.getUser() != null)
		//	intent.putExtra(USER, handler.getUser());
		//intent.putExtra(SplashActivity.USERS_RESOURCE, "/users");
		startActivity(intent);
	}

	public void onGameClick(View view) {
		Intent intent = new Intent(view.getContext(), SplashActivity.class);
		if (handler.getUser() != null)
			intent.putExtra(USER, handler.getUser());
		intent.putExtra(SplashActivity.RESOURCE, "/levelLists");
		intent.putExtra(SplashActivity.NEXT_ACTIVITY, LevelChooserActivity.class.getName());
		startActivity(intent);
	}

	public void onChatClick(View view) {
		Intent intent = new Intent(this, ChatActivity.class);
		intent.putExtra(USER, handler.getUser());
		startActivityForResult(intent, 2);
	}

	public void onConnectionClick(View view) {
		connectionDialog.show();
	}

	public void onRegisterClick(View view) {
		registrationDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			HttpResponse response = data.getParcelableExtra(SplashActivity.RESPONSE);

			Feedback feedback = null;
			if (response.isSuccessful())
				feedback = (Feedback) JsonUtils.populateObjectFromJSON(Feedback.class, response.getJSON());

			if (requestCode == SplashActivity.REGISTRATION_CODE) {
				handler.handleRegistrationResult(response.isSuccessful(), feedback);
			} else if (requestCode == SplashActivity.CONNECTION_CODE) {
				handler.handleConnectionResult(response.isSuccessful(), feedback);
			}
		}
	}

	public void showConnectedView() {
		setContentView(R.layout.activity_main_connected);
	}
	public void showDisconnectedView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}




}
