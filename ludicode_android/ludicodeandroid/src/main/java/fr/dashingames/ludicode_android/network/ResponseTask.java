package fr.dashingames.ludicode_android.network;

import android.app.Activity;

/**
 * Permet de préciser une méthode à executer lors d'un retour d'appel en REST
 * Pour l'utiliser, on implémente la fonction act en appellant la méthode 
 * désirée sur l'objet o en le castant, et en passant en paramètre l'objet
 * response.
 *
 */
public interface ResponseTask {
	
	public void act(Activity activity, HttpResponse[] responses);

}
