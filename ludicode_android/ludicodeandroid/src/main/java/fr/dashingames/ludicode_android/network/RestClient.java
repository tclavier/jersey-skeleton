package fr.dashingames.ludicode_android.network;

import android.net.http.AndroidHttpClient;
import android.os.Build;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import fr.dashingames.ludicode_android.utils.JsonUtils;

/**
 * Classe permettant d'effectuer des requêtes REST
 */

public class RestClient {
	private AndroidHttpClient client;
	private static RestClient instance;

	private final int OK_200 = 200;

	private RestClient() {
		client = AndroidHttpClient.newInstance(Build.MODEL);
	}

	/**
	 * Permet de récupérer l'instance du client
	 * @return l'instance
	 */
	public static RestClient getInstance() {
		if (instance == null) {
			instance = new RestClient();
		}

		return instance;
	}

	/**
	 * Effectue une requête GET avec l'url passée en paramètre
	 * @param url url de la méthode visée
	 * @return HttpResponse contenant le résultat de la requête
	 */
	public HttpResponse httpGET(String url) {
		return getHttpResponse(new HttpGet(url));
	}

	/**
	 * Effectue une requête POST avec l'url passée en paramètre,
	 * et en y ajoutant l'objet passé en paramètre
	 * @param url url de la méthode visée
	 * @param obj objet à transférer
	 * @return HttpResponse contenant le résultat de la requête
	 * @throws UnsupportedEncodingException
	 */
	public HttpResponse httpPOST(String url, Object obj) throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(url);

		StringEntity se = new StringEntity(JsonUtils.toJSON(obj).toString());

		request.setEntity(se);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		return getHttpResponse(request);
	}

	/**
	 * Exécute la requête passée en paramètre
	 * @param request requête à exécuter
	 * @return HttpResponse contenant le résultat de la requête
	 */
	private HttpResponse getHttpResponse(HttpRequestBase request) {
		org.apache.http.HttpResponse transmission;
		String jSon;
		try {
			transmission = client.execute(request);
			if (transmission.getStatusLine().getStatusCode() == OK_200) {
				HttpEntity responseEntity = transmission.getEntity();
				jSon = EntityUtils.toString(responseEntity, HTTP.UTF_8);
			} else {
				return new HttpResponse(null, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new HttpResponse(null, false);
		}
		return new HttpResponse(jSon, true);
	}

}
