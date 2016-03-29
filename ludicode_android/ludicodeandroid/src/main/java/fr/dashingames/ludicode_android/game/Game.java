package fr.dashingames.ludicode_android.game;

import fr.dashingames.ludicode_android.activities.GameActivity;
import fr.dashingames.ludicode_android.beans.Level;
import fr.dashingames.ludicode_android.gui.drawable.GraphicalPlayer;
import fr.dashingames.ludicode_android.gui.drawable.Grid;
import fr.dashingames.ludicode_android.gui.views.GameView;
import fr.dashingames.ludicode_android.utils.JavaScriptParser;
import fr.dashingames.ludicode_android.R;

/**
 * Classe représentant une partie
 *
 */
public class Game {

	private GameView view;
	private GameActivity gameActivity;
	private JavaScriptParser jsParser;
	private Grid grid;
	private GraphicalPlayer gPlayer;
	
	public Game(GameView view, GameActivity gameActivity, Level level) {
		this.gameActivity = gameActivity;
		this.jsParser = new JavaScriptParser();
		this.setView(view);
		view.setGame(this);
		view.setJSParser(jsParser);
		
		loadScripts();
	}
	
	private void loadScripts() {
		jsParser.eval(gameActivity.getString(R.string.js_interpreter));
		jsParser.eval(gameActivity.getString(R.string.js_player));
		jsParser.eval(gameActivity.getString(R.string.js_game));
		
		jsParser.eval("var game = new Game();");
	}

	public GameView getView() {
		return view;
	}

	public void setView(GameView view) {
		this.view = view;
		jsParser.setVariable("gameView", view);
	}

	public GameActivity getGameActivity() {
		return gameActivity;
	}

	public void setGameActivity(GameActivity context) {
		this.gameActivity = context;
	}

	public JavaScriptParser getJsParser() {
		return jsParser;
	}
	
	/**
	 * Execute un algorithme pour résoudre le niveau
	 * @param algo L'algorithme à executer
	 */
	public void executeAlgo(String algo) {
		// On remet les elements a leurs etats initiaux
		//view.generate();
		jsParser.eval("game.interpreter.setup();");
		
		// On definit une variable "raccourci" pour simplifier les algos
		jsParser.eval("var player = game.player");
		
		// On execute l'algorithme
		jsParser.eval(algo);
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public GraphicalPlayer getgPlayer() {
		return gPlayer;
	}

	public void setgPlayer(GraphicalPlayer gPlayer) {
		this.gPlayer = gPlayer;
	}
}	
