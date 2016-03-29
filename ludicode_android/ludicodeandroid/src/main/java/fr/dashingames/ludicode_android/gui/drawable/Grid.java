package fr.dashingames.ludicode_android.gui.drawable;

import fr.dashingames.ludicode_android.utils.Drawings;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Grille de jeu
 *
 */
public class Grid implements Drawable {

	private int x;
	private int y;

	private int[][] tiles;

	private final int NB_LINES;
	private final int NB_COLUMNS;
	
	/**
	 * Taille d'un côté de la grille
	 */
	private int gridWidth;

	// Différents types de tiles possibles sur la grille
	public static final byte TYPE_EMPTY = 0;
	public static final byte TYPE_WALL = 1;
	public static final byte TYPE_PLAYER = 2;
	public static final byte TYPE_FINISH = 3;

	private final int [] TYPE_COLORS = new int[] {
			Color.parseColor("#EEEEEE"),
			Color.parseColor("#222222"),
			Color.parseColor("#EEEEEE"),
			Color.parseColor("#FFFF00")};

	public Grid(int x, int y, Integer[][] tiles, int width) {
		this.x = x;
		this.y = y;
		initTiles(tiles);
		NB_LINES = tiles.length;
		NB_COLUMNS = tiles[0].length;
		gridWidth = width;
	}

	private void initTiles(Integer[][] array) {
		tiles = new int[array.length][array[0].length];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = array[i][j];
			}
		}
	}

	public int getColor(int tileX, int tileY) {
		return TYPE_COLORS[tiles[tileY][tileX]];
	}

	@Override
	public void render(Canvas canvas) {
		Drawings.drawGrid(canvas, x, y, NB_LINES, 
				NB_COLUMNS, gridWidth, this);
	}

	@Override
	public void update(double delta) {	}

	/**
	 * Determine si un carreau est solide ou non
	 * @param tileX Position x du carreaux sur la grille
	 * @param tileY Position y du carreaux sur la grille
	 * @return Vrai si le carreau est solide, faux sinon
	 */
	public boolean isTileSolid(int tileX, int tileY) {
		if (tileX < 0 || tileY < 0 || tileX >= tiles[0].length || tileY >= tiles.length
				|| tiles[tileY][tileX] == TYPE_WALL) {
			return true;
		}

		return false;
	}

	public int[][] getTiles() {
		return tiles;
	}

	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
	}
	
	public int getNbLines() {
		return NB_LINES;
	}

	public int getNbColumns() {
		return NB_COLUMNS;
	}

	public int getGridWidth() {
		return gridWidth;
	}
	
	public void setGridWidth(int width) {
		gridWidth = width;
	}

	public int getTileSize() {
		if (gridWidth == 0)
			return 0;
		return Math.min(gridWidth / NB_LINES, gridWidth / NB_COLUMNS) ;
	}
	
	public int[] getPlayerCoords() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++)
				if (tiles[i][j] == TYPE_PLAYER)
					return new int[] {i, j};
		}
		return null;
	}
	
	public boolean isFinish(int tileX, int tileY) {
		return tiles[tileY][tileX] == TYPE_FINISH;
	}
	

}
