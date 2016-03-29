package fr.dashingames.ludicode_android.gui.drawable;

import fr.dashingames.ludicode_android.activities.GameActivity;
import fr.dashingames.ludicode_android.gui.views.GameView;
import fr.dashingames.ludicode_android.utils.Animation;
import fr.dashingames.ludicode_android.utils.Drawings;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe représentant le joueur dessiné
 *
 */
public class GraphicalPlayer implements Drawable, Parcelable {

	/**
	 * Vitesse de déplacement du joueur
	 */
	private final int SPEED = 30;

	private int size;

	private float x;
	private float y;
	private float angle = 0;

	private float nextX;
	private float nextY;
	private float nextAngle = 0;

	private int directionX = 0;
	private int directionY = 1;

	private boolean isRotating = false;
	private boolean isTranslating = false;
	
	private GameView view;
	
	public GraphicalPlayer() { }

	@Override
	public void render(Canvas canvas) {
		Drawings.drawTriangle(canvas, view.getGrid(), x, y, size, (int) Math.toDegrees(angle));
	}

	@Override
	public void update(double delta) {
		if (Math.abs(nextX - x) > 2) {
			int direction = 1;
			if (nextX < x)
				direction = -1;

			x += (SPEED * delta * GameActivity.gameSpeed * direction);
			if (((nextX - this.x) * direction) < 0) 
				this.x = nextX;
		} else {
			x = nextX;

			if (Math.abs(nextY - y) > 2) {
				int direction = 1;
				if (nextY < y)
					direction = -1;

				y += (SPEED * delta * GameActivity.gameSpeed * direction);
				if (((nextY - this.y) * direction) < 0) 
					this.y = nextY;
			} else {
				y = nextY;

				if (isTranslating && view != null) {
					view.testIfFinished(tileX(), tileY());
				}
						
				isTranslating = false;
			}
		}

		if (Math.abs(nextAngle - angle) > Math.PI / 20) {
			int direction = 1;
			if (nextAngle < angle)
				direction = -1;

			angle +=  (Math.PI / 10 * direction * GameActivity.gameSpeed * delta);
			if (((nextAngle - angle) * direction) < 0) 
				angle = nextAngle;
		} else {
			angle = nextAngle;
			isRotating = false;
		}
	}

	/**
	 * Déplace le player à la position (x, y)
	 * @param x abscisse de la nouvelle position
	 * @param y ordonnée de la nouvelle position
	 */
	public void moveTo(int x, int y) {
		isTranslating = true;
		nextX = x;
		nextY = y;
	}

	/**
	 * Déplace le player à la case (x, y)
	 * @param x abscisse de la grille de la nouvelle position
	 * @param y ordonnée de la grille de la nouvelle position
	 */
	public void moveToTile(int x, int y) {
		moveTo(x * size, y * size);
	}

	/**
	 * Fais tourner le joueur jusqu'à l'angle donné en degrés
	 * @param angle angle de destination
	 */
	public void rotateTo(float angle) {
		isRotating = true;
		nextAngle = angle;
	}

	/**
	 * Renvoie la colonne de la grille à laquelle se trouve le player
	 * @return colonne
	 */
	public int tileX() {
		return (int) (x / size);
	}

	/**
	 * Renvoie la ligne de la grille à laquelle se trouve le player
	 * @return ligne
	 */
	public int tileY() {
		return (int) (y / size);
	}

	/**
	 * Détermine si le player est en train de se déplacer (rotation ou mouvement)
	 * @return true si le player se déplace, false sinon
	 */
	public boolean isDoingSomething() {
		return isRotating || isTranslating || view.getPlayerSearchingAnimation().isRunning();
	}

	/**
	 * Fait tourner le player vers la gauche
	 */
	public void turnLeft() {
		float newAngle = (float) (angle - Math.PI / 2);
		rotateTo(newAngle);

		if (directionX == 0) {
			directionX = directionY;
			directionY = 0;
		} else {
			directionY = -directionX;
			directionX = 0;
		}
	}

	/**
	 * Fait tourner le player vers la droite
	 */
	public void turnRight() {
		float newAngle = (float) (angle + Math.PI / 2);
		rotateTo(newAngle);

		if (directionX == 0) {
			directionX = -directionY;
			directionY = 0;
		} else {
			directionY = directionX;
			directionX = 0;	
		}
	}

	/**
	 * Fait avancer le joueur d'une case
	 */
	public void moveForward() {
		moveToTile(tileX() + directionX, tileY() + directionY);
	}

	/**
	 * Fait reculer le joueur d'une case
	 */
	public void moveBackward() {
		moveToTile(tileX() - directionX, tileY() - directionY);
	}

	/**
	 * Définit la taille du joueur
	 * @param size taille
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * Dessine l'animation de détection des murs vers l'avant
	 */
	public void scanForward() {
		Animation anim = view.getPlayerSearchingAnimation();
		anim.setAngle((float) Math.toDegrees(angle));
		anim.setFramesColor(canGoForward());
		anim.start();
	}
	
	/**
	 * Dessine l'animation de détection des murs vers l'arrière
	 */
	public void scanBackward() {
		Animation anim = view.getPlayerSearchingAnimation();
		anim.setAngle((float) Math.toDegrees(-angle));
		anim.setFramesColor(canGoBackward());
		anim.start();
	}
	
	/**
	 * Dessine l'animation de détection des murs vers la gauche
	 */
	public void scanLeft() { 
		Animation anim = view.getPlayerSearchingAnimation();
		anim.setAngle((float) Math.toDegrees(angle - Math.PI / 2));
		anim.setFramesColor(canGoLeft());
		anim.start();
	}
	
	/**
	 * Dessine l'animation de détection des murs vers la droite
	 */
	public void scanRight() {
		Animation anim = view.getPlayerSearchingAnimation();
		anim.setAngle((float) Math.toDegrees(angle + Math.PI / 2));
		anim.setFramesColor(canGoRight());
		anim.start();
	}
	
	/**
	 * @return true si le player peut aller en avant
	 */
	public boolean canGoForward() {
		return !view.getGrid().isTileSolid(tileX() + directionX, tileY() + directionY);
    }

	/**
	 * @return true si le player peut aller en arrière
	 */
	public boolean canGoBackward () {
        return !view.getGrid().isTileSolid(tileX() - directionX, tileY() - directionY);
    }

	/**
	 * @return true si le player peut aller à gauche
	 */
	public boolean canGoLeft() {
        return !view.getGrid().isTileSolid(tileX() + directionY, tileY() - directionX);
    }

	/**
	 * @return true si le player peut aller à droite
	 */
	public boolean canGoRight() {
        return !view.getGrid().isTileSolid(tileX() - directionY, tileY() + directionX);
    }
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		int[] values = new int[3];
		values[0] = directionX;
		values[1] = directionY;
		values[2] = size;

		float[] floats = new float[6];
		floats[0] = x;
		floats[1] = y;
		floats[2] = angle;
		floats[3] = nextX;
		floats[4] = nextY;
		floats[5] = nextAngle;

		boolean[] booleans = new boolean[2];
		booleans[0] = isRotating;
		booleans[1] = isTranslating;

		dest.writeIntArray(values);
		dest.writeFloatArray(floats);
		dest.writeBooleanArray(booleans);
	}

	public GraphicalPlayer(Parcel in) {
		int[] ints = new int[3];
		float[] floats = new float[6];
		boolean[] booleans = new boolean[2];

		in.readIntArray(ints);
		in.readFloatArray(floats);
		in.readBooleanArray(booleans);

		directionX = ints[0];
		directionY = ints[1];
		size = ints[2];

		x = floats[0];
		y = floats[1];
		angle = floats[2];
		nextX = floats[3];
		nextY = floats[4];
		nextAngle = floats[5];

		isRotating = booleans[0];
		isTranslating = booleans[1];
	}

	public static final Parcelable.Creator<GraphicalPlayer> CREATOR
	= new Parcelable.Creator<GraphicalPlayer>() {
		public GraphicalPlayer createFromParcel(Parcel in) {
			return new GraphicalPlayer(in);
		}

		public GraphicalPlayer[] newArray(int size) {
			return new GraphicalPlayer[size];
		}
	};

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getNextX() {
		return nextX;
	}

	public void setNextX(float nextX) {
		this.nextX = nextX;
	}

	public float getNextY() {
		return nextY;
	}

	public void setNextY(float nextY) {
		this.nextY = nextY;
	}

	public float getNextAngle() {
		return nextAngle;
	}

	public void setNextAngle(float nextAngle) {
		this.nextAngle = nextAngle;
	}

	public int getDirectionX() {
		return directionX;
	}

	public void setDirectionX(int directionX) {
		this.directionX = directionX;
	}

	public int getDirectionY() {
		return directionY;
	}

	public void setDirectionY(int directionY) {
		this.directionY = directionY;
	}

	public boolean isRotating() {
		return isRotating;
	}

	public void setRotating(boolean isRotating) {
		this.isRotating = isRotating;
	}

	public boolean isTranslating() {
		return isTranslating;
	}

	public void setTranslating(boolean isTranslating) {
		this.isTranslating = isTranslating;
	}

	public int getSize() {
		return size;
	}

	public GameView getView() {
		return view;
	}

	public void setView(GameView view) {
		this.view = view;
	}

}
