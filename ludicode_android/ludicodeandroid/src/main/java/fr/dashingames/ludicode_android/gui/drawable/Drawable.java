package fr.dashingames.ludicode_android.gui.drawable;

import android.graphics.Canvas;

/**
 * Classe implémentée par les objets devant être dessinés
 *
 */
public interface Drawable {

	/**
	 * Méthode permettant de dessiner la vue
	 * @param buffer canvas sur lequel dessiner la vue
	 */
	public void render(Canvas canvas);
	
	/**
	 * Fonction permettant de mettre à jour l'objet courant
	 * @param delta temps écoulé depuis la dernière mise à jour
	 */
	public void update(double delta);
}
