package fr.dashingames.ludicode_android.activities;

import java.util.ArrayList;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.Instruction;
import fr.dashingames.ludicode_android.beans.Level;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.game.Game;
import fr.dashingames.ludicode_android.gui.adapters.AlgorithmAdapter;
import fr.dashingames.ludicode_android.gui.views.FittingTextView;
import fr.dashingames.ludicode_android.gui.views.GameView;
import fr.dashingames.ludicode_android.network.HttpResponse;
import fr.dashingames.ludicode_android.utils.AlgorithmHandler;
import fr.dashingames.ludicode_android.utils.JsonUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

/**
 * Activité représentant l'écran de jeu
 *
 */
public class GameActivity extends Activity {

	/**
	 * Partie courante
	 */
	private Game game;

	private ArrayList<Instruction> chosenInstructions = new ArrayList<Instruction>();
	private GameView gameView;
	private ListView algorithmView;
	private AlgorithmAdapter<Instruction> adapter;
	private AlgorithmHandler handler;

	public static final String POSSIBLE_INSTRUCTIONS = "Possible Instructions";
	public static final String LEVELS_RESPONSES = "Level responses";

	private ArrayList<HttpResponse> responses;
	private Level level;

	private Button executeButton;
	private FittingTextView nbInstructions;
	
	private User user;
	
	private boolean newLevel = true;

	/**
	 * Vitesse du jeu
	 */
	public static int gameSpeed = 15;

	private int currentLevel = 0;

	private AlertDialog alertFinishLevelDialog;
	private AlertDialog alertChooseDialog;
	private AlertDialog alertFinishListDialog;

	private int selectedLine = -1;

	private final String CURRENT_LEVEL = "Current level";
	private final String PARCEL_LEVEL = "Parcel level";
	private final String PARCEL_RESPONSES = "Parcel responses";
	private final String PARCEL_HANDLER = "Parcel handler";
	
	private boolean buttonIsExecute = true;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_game);
		
		initViews();
		
		if (bundle == null) {
			
			user = getIntent().getExtras().getParcelable(MainActivity.USER);

			setExecuteButtonText(true);
			SeekBar seek = (SeekBar) findViewById(R.id.seekBar1);
			seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) { }

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) { }

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					gameSpeed = progress;
					if (gameSpeed == 0)
						gameSpeed = 1;
				}
			});

			initializeGame();
		} 
	}

	private void setExecuteButtonText(boolean buttonIsExecute) {
		this.buttonIsExecute = buttonIsExecute;
		int id = buttonIsExecute ? R.string.execute : R.string.reset;
		executeButton.setText(getResources().getString(id));
	}
	private void initViews() {
		gameView = (GameView) findViewById(R.id.GameView);
		algorithmView = (ListView) findViewById(R.id.AlgorithmView);
		nbInstructions = (FittingTextView) findViewById(R.id.nbInstructions);
		executeButton = (Button) findViewById(R.id.executeButton);
	}

	@Override
	public void onResume() {
		super.onResume();
		gameView.resetLoop();
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		initViews();
		currentLevel = bundle.getInt(CURRENT_LEVEL);
		level = bundle.getParcelable(PARCEL_LEVEL);
		responses = bundle.getParcelableArrayList(PARCEL_RESPONSES);
		handler = bundle.getParcelable(PARCEL_HANDLER);
		user = bundle.getParcelable(MainActivity.USER);
		restartGame();
	};

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		highlightLine(-1);
		bundle.putInt(CURRENT_LEVEL, currentLevel);
		bundle.putParcelable(PARCEL_LEVEL, level);
		bundle.putParcelableArrayList(PARCEL_RESPONSES, responses);
		bundle.putParcelable(PARCEL_HANDLER, handler);
		bundle.putParcelable(MainActivity.USER, user);
		gameView.stopLoop();
	}

	/**
	 * Initialise une liste de niveaux et lance le premier
	 */
	private void initializeGame() {
		if (this.getIntent().getExtras() != null)
			responses = this.getIntent().getExtras().getParcelableArrayList(LEVELS_RESPONSES);
		newLevel = true;
		initLevel();
		initFinishLevelDialog();
		initFinishListDialog();
	}

	private void restartGame() {
		initLevel();
		initFinishLevelDialog();
		initFinishListDialog();
	}

	public void finishLevel() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (currentLevel == responses.size() - 1)
					alertFinishListDialog.show();
				else
					alertFinishLevelDialog.show();
			}
		});
	}

	public void launchNextLevel() {
		currentLevel++;
		newLevel = true;
		initLevel();
		gameView.resetLoop();
	}

	/**
	 * Lance le prochain niveau de la liste
	 */
	public void initLevel() {
		level = (Level) JsonUtils.populateObjectFromJSON(Level.class, responses.get(currentLevel).getJSON());

		level.addElseInConditionsNames();
		if (handler == null || newLevel)
			handler = new AlgorithmHandler(this, level.getMaxInstructions(), level.getInstructionsList());
		newLevel = false;
		initChooseDialog();
		initAlgorithmView();
		updateList();
		initGameView();
		setExecuteButtonText(true);
		nbInstructions.updateNbInstructions(this, level.getMaxInstructions() - handler.countChosenInstructions());
	}

	private void initGameView() {
		game = new Game(gameView, this, level);
		gameView.setGame(game);
		gameView.generate(level);
	}

	private void initAlgorithmView() {
		adapter = new AlgorithmAdapter<Instruction>(this, R.layout.algolist_row, 
				chosenInstructions, algorithmView, handler);
		algorithmView.setAdapter(adapter);
	}

	public void clickItem(int position) {
		if (!buttonIsExecute)
			execute(null);
		if (!handler.isLineEmpty(position)) {
			handler.addEmptyLine(position);
			updateList();
		} else {
			alertChooseDialog.show();
			selectedLine = position;
		}
	}

	private void initFinishLevelDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		Resources resources = this.getResources();
		dialogBuilder.setTitle(resources.getString(R.string.congrats))
		.setMessage(resources.getString(R.string.finishLevel))
		.setCancelable(false)
		.setPositiveButton(resources.getString(R.string.nextLevel), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				launchNextLevel();
			}
		})
		.setNegativeButton(resources.getString(R.string.leaveList), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				GameActivity.this.finish();
			}
		});

		alertFinishLevelDialog = dialogBuilder.create();
		alertFinishLevelDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}

	private void initFinishListDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		Resources resources = getResources();
		dialogBuilder.setTitle(resources.getString(R.string.bravo))
		.setMessage(resources.getString(R.string.finishList))
		.setCancelable(false)
		.setPositiveButton(getResources().getString(R.string.leaveList), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				GameActivity.this.finish();
			}
		});

		alertFinishListDialog = dialogBuilder.create();
		alertFinishListDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}

	private void initChooseDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		final ArrayAdapter<Instruction> adapter = new ArrayAdapter<Instruction>(this, 
				android.R.layout.simple_list_item_1, level.getInstructionsList());
		dialogBuilder.setTitle(getResources().getString(R.string.pickInstruction))
		.setCancelable(true)
		.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				handler.addInstruction(adapter.getItem(which), selectedLine);
				updateList();
				nbInstructions.updateNbInstructions(GameActivity.this, level.getMaxInstructions() - handler.countChosenInstructions());
				dialog.dismiss();
			}
		});
		alertChooseDialog = dialogBuilder.create();
	}

	/**
	 * Synchronise la liste d'instructions de la listView avec celle de l'handler
	 */
	private void syncLists() {
		ArrayList<Instruction> instructions = handler.getChosenInstructions();
		chosenInstructions.clear();
		chosenInstructions.addAll(instructions);
	}

	/**
	 * Met à jour la liste des instructions choisies avec celles venant du handler
	 */
	private void updateList() {
		syncLists();
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	/**
	 * Efface toutes les instructions choisies par l'utilisateur
	 * @param view
	 */
	public void resetInstructions(View view) {
		handler.resetInstructions();
		updateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public Game getGame() {
		return game;
	}

	public void execute(View view) {
		if (buttonIsExecute) {
			game.executeAlgo("function code() {\ngame.interpreter.setup();\n" + handler.getAlgorithm() + " \n}\ncode();");
			setExecuteButtonText(false);
		} else {
			highlightLine(-1);
			game.executeAlgo("game.interpreter.setup();");
			gameView.generate(level);
			setExecuteButtonText(true);
		}
	}

	public void removeLine(int position) {
		if (!buttonIsExecute)
			execute(null);
		handler.removeLine(position);
		nbInstructions.updateNbInstructions(this, level.getMaxInstructions() - handler.countChosenInstructions());
		updateList();
	}

	public void highlightLine(final int position) {
		// On surligne le nouveau bloc
		handler.setHighlightedBlock(position);

		// TODO: Invalidate l'ancien et le nouveau seulement pour optimiser
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				adapter.notifyDataSetChanged();
			}

		});
	}


}
