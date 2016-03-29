package fr.dashingames.ludicode_android.game;

import fr.dashingames.ludicode_android.gui.views.GameView;
import android.graphics.Canvas;

/**
 * Boucle de jeu. Permet de mettre à jour le player et la grid, puis de les dessiner
 *
 */
public class GameLoop extends Thread {

	private long elapsedTime = 0;
	private GameView view;
	private boolean running = false;


	public GameLoop(GameView view) {
		this.view = view;
	}

	public void setRunning(boolean run) {
		this.running = run;
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		while (running) {
			Canvas c = null;
			double delta = (System.currentTimeMillis() - elapsedTime);
			delta /= 1000;
			view.update(delta);
			elapsedTime = System.currentTimeMillis();
			try {
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.render(c);
				}
			} finally {
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
		}

	}
}
