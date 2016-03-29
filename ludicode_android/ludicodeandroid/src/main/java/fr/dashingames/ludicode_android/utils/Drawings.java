package fr.dashingames.ludicode_android.utils;

import fr.dashingames.ludicode_android.gui.drawable.Grid;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

/**
 * Classe contenant les outils de dessin
 *
 */
public class Drawings {
	
	private static Paint paint = new Paint();
	private static Path path = new Path();
	private static final int PADDING = 10;
	
	public Drawings() { }
	
	/**
	 * Dessine un triangle
	 * @param canvas canvas sur lequel dessiner
	 * @param grid grille de jeu
	 * @param x abscisse de la case dans laquelle on dessine le triangle
	 * @param y ordonnée de la case dans laquelle on dessine le triangle
	 * @param vertex dimension de la case de la grille
	 * @param angle angle de rotation du triangle
	 */
	public static void drawTriangle(Canvas canvas, Grid grid, float x, float y, int vertex, int angle) {
		path.reset();
		configurePaint(Color.RED, Style.FILL);
		
		x = x + PADDING;
		y = y + PADDING;
		vertex -= PADDING * 2;
		
		float x1 = x, y1 = y,
				x2 = x + vertex, y2 = y,
				x3 = x + vertex / 2, y3 = y + vertex;
		
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x3, y3);
		path.lineTo(x1, y1);
		path.close();
		
		canvas.save();
		canvas.translate((grid.getGridWidth() - (grid.getTileSize() * grid.getNbColumns()))/2, 0);
		canvas.rotate(angle, x + vertex / 2, y + vertex / 2);
		canvas.drawPath(path, paint);
		canvas.restore();
	}
	
	/**
	 * Dessine la grille du jeu
	 * @param canvas canvas sur lequel dessiner
	 * @param x abscisse du départ de la grille (point en haut à gauche)
	 * @param y ordonnée du départ de la grille (point en haut à gauche)
	 * @param nbLines nombre de lignes
	 * @param nbColumns nombre de colonnes
	 * @param width largeur de la grille
	 * @param size taille d'une case
	 */
	public static void drawGrid(Canvas canvas, int x, int y, int nbLines, int nbColumns, 
			int width, Grid grid) {
		int size = grid.getTileSize();
		canvas.save();
		canvas.translate((width - (size * nbColumns))/2, 0);

		for (int i = 0; i < nbColumns; i++) {
			for (int j = 0; j < nbLines; j++) {
				int color = grid.getColor(i, j);
				configurePaint(color, Style.FILL);
				canvas.drawRect(i * size, j * size, i * size + size, j * size + size, paint);
				
				configurePaint(Color.BLACK, Style.STROKE);
				canvas.drawRect(i * size, j * size, i * size + size, j * size + size, paint);
			}
		}
		canvas.restore();
	}
	
	/**
	 * Configure la peinture
	 * @param color couleur du dessin
	 * @param style style de dessin : seulement les traits ou remplit (Stroke ou Fill)
	 */
	private static void configurePaint(int color, Style style) {
		configurePaint(color, style, 1);
	}
	
	/**
	 * Configure la peinture
	 * @param color couleur du dessin
	 * @param style style de dessin : seulement les traits ou remplit (Stroke ou Fill ou les deux)
	 * @param strokeWidth largeur du trait
	 */
	private static void configurePaint(int color, Style style, int strokeWidth) {
		paint.reset();
		paint.setColor(color);
		paint.setStyle(style);
		paint.setStrokeWidth(strokeWidth);
	}

}
