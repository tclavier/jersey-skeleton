package fr.dashingames.ludicode_android.utils;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.activities.MainActivity;
import fr.dashingames.ludicode_android.activities.SplashActivity;
import fr.dashingames.ludicode_android.beans.Feedback;
import fr.dashingames.ludicode_android.beans.User;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Classe gérant les activités de connexion et inscription
 *
 */
public class IdentificationHandler {
	
	private MainActivity activity;
	
	public static final int CONNECTION_SUCCESS = 200;
	public static final int REGISTRATION_SUCCESS = 201;
	
	private final int CREDENTIALS_OK = 0;
	private final int ERROR_LOGIN = R.string.invalidLogin;
	private final int ERROR_PSWD = R.string.invalidPassword;
	private final int ERROR_DIFFERENT_PSWD = R.string.differentPasswords;
	private final int ERROR_INVALID_MAIL = R.string.invalidMail;
	
	private User user = null;
	
	public IdentificationHandler(MainActivity activity) {
		this.activity = activity;
	}
	
	/**
	 * Lance la connexion
	 * @param login login de l'utilisateur
	 * @param password mot de passe de l'utilisateur
	 * @return int représentant la réussite de la tentative (login et mot de passe au bon format)
	 */
	public int tryToConnect(String login, String password) {
		int resultCode = credentialsAreValid(login, password); 
		if (resultCode == CREDENTIALS_OK) {
			sendConnectionRequest(login, password);
			return CONNECTION_SUCCESS;
		} else
			return resultCode;
	}
	
	/**
	 * Envoie la requête de connexion au serveur
	 * @param login 
	 * @param password
	 */
	private void sendConnectionRequest(String login, String password) {
		user = new User();
		user.setName(login);
		user.setPassword(password);
		
		final String URL = SplashActivity.USERS_RESOURCE + SplashActivity.CONNECT_USER_RESOURCE;
		
		Intent intent = new Intent(activity, SplashActivity.class);
		intent.putExtra(SplashActivity.RESOURCE, URL);
		intent.putExtra(SplashActivity.BEAN, user);
		intent.putExtra(SplashActivity.WAITS_RESULT, true);
		intent.putExtra(SplashActivity.REQUEST_CODE, SplashActivity.CONNECTION_CODE);
		activity.startActivityForResult(intent, SplashActivity.CONNECTION_CODE);
	}
	
	/**
	 * Lance l'inscription
	 * @param login login de l'utilisateur
	 * @param password mot de passe de l'utilisateur
	 * @param checkPassword mot de passe de vérification
	 * @param mail mail de l'utilisateur
	 * @return int représentant la réussite de la tentative
	 */
	public int tryToRegister(String login, String password, String checkPassword, String mail) {
		int resultCode = registrationInformationsAreValid(login, password, checkPassword, mail);
		if (resultCode == CREDENTIALS_OK)
			sendRegistrationRequest(login, password, mail);
		else
			return resultCode;
		
		return REGISTRATION_SUCCESS;
	}
	
	/**
	 * Envoie la requête d'inscription au serveur
	 * @param login
	 * @param password
	 * @param mail
	 */
	private void sendRegistrationRequest(String login, String password, String mail) {
		user = new User();
		user.setName(login);
		user.setPassword(password);
		user.setEmail(mail);

		final String URL = SplashActivity.USERS_RESOURCE + SplashActivity.CREATE_USER_RESOURCE;

		Intent intent = new Intent(activity, SplashActivity.class);
		intent.putExtra(SplashActivity.RESOURCE, URL);
		intent.putExtra(SplashActivity.BEAN, user);
		intent.putExtra(SplashActivity.WAITS_RESULT, true);
		intent.putExtra(SplashActivity.REQUEST_CODE, SplashActivity.REGISTRATION_CODE);
		activity.startActivityForResult(intent, SplashActivity.REGISTRATION_CODE);
	}
	
	/**
	 * Gère le résultat de la tentative de connexion
	 * @param requestSuccess résultat de la requête http
	 * @param feedback feedback décrivant le résultat de la connexion (réussie ou non)
	 */
	public void handleConnectionResult(boolean requestSuccess, Feedback feedback) {
		if (!requestSuccess) {
			user = null;
			showMessage(R.string.serverConnectionFailed);
		} else if (!feedback.isSuccess()) {
			user = null;
			showMessage(feedback.getMessage());
		} else {
			user.setCookie(feedback.getMessage());
			showMessage(R.string.connectionSuccess);
			activity.showConnectedView();
		}
	}

	/**
	 * Gère le résultat de la tentative d'inscription
	 * @param requestSuccess résultat de la requête http
	 * @param feedback feedback décrivant le résultat de l'inscription (réussie ou non)
	 */
	public void handleRegistrationResult(boolean requestSuccess, Feedback feedback) {
		if (!requestSuccess) {
			user = null;
			showMessage(R.string.serverConnectionFailed);
		} else if (!feedback.isSuccess()) {
			user = null;
			showMessage(feedback.getMessage());
		} else {
			showMessage(R.string.registrationSuccess);
			tryToConnect(user.getName(), user.getPassword());
		}
	}
	
	/**
	 * Détermine si les informations de l'utilisateur ont un format valide
	 * @param login
	 * @param password
	 * @param checkPassword
	 * @param mail
	 * @return un int représentant la réussite ou l'échec, et si échec le type d'échec
	 */
	private int registrationInformationsAreValid(String login, String password, String checkPassword, String mail) {
		int resultCode = credentialsAreValid(login, password);
		if (resultCode != CREDENTIALS_OK)
			return resultCode;
		if (!password.equals(checkPassword))
			return ERROR_DIFFERENT_PSWD;
		if (!mail.matches("^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$"))
			return ERROR_INVALID_MAIL;
		return CREDENTIALS_OK;
	}
	
	/**
	 * Détermine si les informations de l'utilisateur ont un format valide
	 * @param login
	 * @param password
	 * @return un int représentant la réussite ou l'échec, et si échec le type d'échec
	 */
	private int credentialsAreValid(String login, String password) {
		if (login.length() < 3)
			return ERROR_LOGIN;
		if (password.length() < 6)
			return ERROR_PSWD;
		return CREDENTIALS_OK;
	}

	public void showMessage(int messageId) {
		showMessage(getMessageFromId(messageId));
	}
	
	private void showMessage(String message) {
		Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	private String getMessageFromId(int id) {
		return activity.getResources().getString(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
