package fr.dashingames.ludicode_android.gui.views;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.Level;
import fr.dashingames.ludicode_android.game.Game;
import fr.dashingames.ludicode_android.game.GameLoop;
import fr.dashingames.ludicode_android.gui.drawable.GraphicalPlayer;
import fr.dashingames.ludicode_android.gui.drawable.Grid;
import fr.dashingames.ludicode_android.utils.Animation;
import fr.dashingames.ludicode_android.utils.JavaScriptParser;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Vue représentant le niveau (grille et player)
 *
 */
public class GameView extends SurfaceView {

	/**
	 * Taille d'un côté de la vue. Sachant que la vue 
	 * est toujours de forme carrée, correspond à la taille de tous
	 * les côtés.
	 */
	private int dimension;

	private Game game;
	private GraphicalPlayer gPlayer;
	private SurfaceHolder holder;
	private GameLoop loop;
	private Grid grid;
	private Bitmap scannerBitmap;

	private JavaScriptParser parser;
	
	private Animation animation;

	private final int BACKGROUND_COLOR = Color.parseColor("#fff3f3f3");

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				loop.setRunning(false);
				while (retry) {
					try {
						loop.join();
						retry = false;
					} catch (InterruptedException e) {	}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				resetLoop();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {	}
		});
		
		scannerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
	}

	public void setJSParser(JavaScriptParser parser) {
		this.parser = parser;
	}

	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
		rescaleGraphicalElements(height);
		super.onSizeChanged(width, height, oldWidth, oldHeight);
	}

	/**
	 * Redimensionne les éléments contenus dans la vue
	 * @param width largeur disponible
	 */
	private void rescaleGraphicalElements(int width) {
		if (grid != null && gPlayer != null && game != null) {
			grid.setGridWidth(width - 1);
			int tileSize = grid.getTileSize();
			int[] coords = grid.getPlayerCoords();

			gPlayer.setSize(tileSize);
			gPlayer.setX(coords[1] * tileSize);
			gPlayer.setNextX(coords[1] * tileSize);
			gPlayer.setY(coords[0] * tileSize);
			gPlayer.setNextY(coords[0] * tileSize);
			gPlayer.setDirectionX(0);
			gPlayer.setDirectionY(1);

			game.getJsParser().call("game", "generate", new Object[]{grid, gPlayer});
		}
	}

	/**
	 * Méthode permettant de dessiner cette vue. Permet de contourner
	 * la visibilité protected de onDraw
	 * @param canvas canvas sur lequel on dessine
	 */
	@SuppressLint("WrongCall")
	public void render(Canvas canvas) {
		onDraw(canvas);
	}

	/**
	 * Méthode appelée lorsque l'on redessine la vue. 
	 * Ne pas override cette méthode, si l'on veut modifier le comportement
	 * de dessin, il faut plutôt implémenter la méthode render.
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas != null) {
			if (game != null && game.getGameActivity() != null) {
				canvas.drawColor(BACKGROUND_COLOR);
			}
			if (grid != null)
				grid.render(canvas);
			if (gPlayer != null) {
				gPlayer.render(canvas);
				if (animation != null)
					animation.draw(canvas, gPlayer.getX(), gPlayer.getY(), grid.getTileSize()/2);
			}
		}
	}

	public void update(double delta) {
		if (grid != null && gPlayer != null && animation != null) {
			grid.update(delta);
			gPlayer.update(delta);
			animation.update(delta);
			if (loop.isRunning())
				game.getJsParser().call("game", "update", new Object[]{delta}); 
		}
	}

	/**
	 * Génère la vue
	 * @param level niveau courant
	 */
	public void generate(Level level) {
		grid = new Grid(0, 0, level.getStructuredContent(), 0);
		gPlayer = new GraphicalPlayer();
		gPlayer.setView(this);
		parser.setVariable("gameView", this);
		game.setgPlayer(gPlayer);
		game.setGrid(grid);
		
		rescaleGraphicalElements(getHeight());
		animation = new Animation(scannerBitmap, 6, 2, 0.5f);
	}
	/**
	 * Affiche un message a l'ecran
	 * @param message Le message a afficher
	 */
	public void showMessage(final String message) {
		this.post(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(game.getGameActivity(), message, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		});
	}
	
	/**
	 * Surligne une ligne dans l'algo
	 * @param position La position de la ligne a surligner
	 */
	public void highlightLine(int position) {
		game.getGameActivity().highlightLine(position);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GraphicalPlayer getGraphicalPlayer() {
		return gPlayer;
	}

	public void setGraphicalPlayer(GraphicalPlayer gPlayer) {
		this.gPlayer = gPlayer;
	}

	public int getDimension() {
		return dimension;
	}

	public void resetLoop() {
		loop = new GameLoop(this);

		loop.setRunning(true);
		loop.start();
	}

	public void stopLoop() {
		loop.setRunning(false);
	}

	/**
	 * Détermine si le player est arrivé à l'arrivée
	 * @param tileX abscisse du player
	 * @param tileY ordonnée du player
	 */
	public void testIfFinished(int tileX, int tileY) {
		if (grid.isFinish(tileX, tileY)) {
			stopLoop();
			game.getGameActivity().finishLevel();
		}
	}

	public Animation getPlayerSearchingAnimation() {
		return animation;
	}

	public void setPlayerSearchingAnimation(Animation animation) {
		this.animation = animation;
	}
	public Grid getGrid() {
		return grid;
	}

}
