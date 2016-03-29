package fr.dashingames.ludicode_android.network;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.AsyncTask;
import fr.dashingames.ludicode_android.network.HttpResponse;

/**
 * Classe permettant d'effectuer des appels REST sur le serveur.
 * Pour l'utiliser :
 * 
 * On crée un objet RestTask en passant en paramètre l'objet sur lequel
 * sera appellée la méthode de retour de la requête, et la tâche à effectuer
 * lors du retour. La tâche doit appeller une méthode de l'objet sus-cité en passant
 * en paramètre l'objet HttpResponse.
 * 
 * Ensuite, on appelle la méthode execute de la RestTask en passant en paramètre les 
 * diverses URL sur lesquelles on souhaite faire des appels.
 *
 */
public class RestTask extends AsyncTask<String, Void, HttpResponse[]> {

	private Activity responseHandler;
	private ResponseTask rpTask;
	
	public static final byte HTTP_GET = 0;
	public static final byte HTTP_POST = 1;
	
	private byte httpMethod;
	private Object bean;

	public RestTask(Activity responseHandler, ResponseTask rpTask) {
		super();
		this.responseHandler = responseHandler;
		this.rpTask = rpTask;
		httpMethod = HTTP_GET;
	}
	
	public RestTask(Activity responseHandler, ResponseTask rpTask, Object bean) {
		this(responseHandler, rpTask);
		httpMethod = HTTP_POST;
		this.bean = bean;
	}

	@Override
	protected HttpResponse[] doInBackground(String... params) {
		RestClient instance = RestClient.getInstance();
		HttpResponse[] responses = new HttpResponse[params.length];
		for (int i = 0; i < params.length; i++) {
			String s = params[i];
			if (httpMethod == HTTP_GET)
				responses[i] = doGet(instance, s);
			else if (httpMethod == HTTP_POST)
				responses[i] = doPost(instance, s);
		}
		return responses;
	}

	protected void onPostExecute(HttpResponse[] response) {
		rpTask.act(responseHandler, response);
	}

	/**
	 * Execute un appel REST en méthode GET
	 * @param instance instance de l'appel
	 * @param params URL à appeller
	 * @return la réponse de l'appel
	 * 
	 */
	private HttpResponse doGet(RestClient instance, String... param) {
		HttpResponse resp = instance.httpGET(param[0]);
		return resp;
	}

	/**
	 * Execute un appel REST en méthode POST
	 * @param instance instance de l'appel
	 * @param param URL à appeller
	 * @return la réponse de l'appel
	 * 
	 */
	private HttpResponse doPost(RestClient instance, String s) {
		HttpResponse resp;
		try {
			resp = instance.httpPOST(s, bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new HttpResponse("", false);
		}
		return resp;
	}

}
