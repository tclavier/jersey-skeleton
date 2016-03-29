package fr.dashingames.ludicode_android.activities;

import java.util.ArrayList;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.LevelList;
import fr.dashingames.ludicode_android.beans.LevelListAssociation;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.network.HttpResponse;
import fr.dashingames.ludicode_android.network.ResponseTask;
import fr.dashingames.ludicode_android.network.RestTask;
import fr.dashingames.ludicode_android.utils.JsonUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

//	public static final String SERVER_URL = "http://iut.azae.net/Ludicode/v1";
	public static final String SERVER_URL = "http://92.222.218.92:8080/v1";
	public static final String SERVER_URL_NO_PROTOCOL = "92.222.218.92:8080";
	
	public static final String USERS_RESOURCE = "/users";
	public static final String CREATE_USER_RESOURCE = "/register";
	public static final String CONNECT_USER_RESOURCE = "/login";
	public static final String LEVEL_PROGRESS_RESOURCE = "/LevelProgress";
	public static final String ADD_PROGRESS_RESOURCE = "/putProgress";
	public static final String BEAN = "bean to send";
	public static final String RESPONSE = "response";
	public static final String RESOURCE = "resource";
	public static final String NEXT_ACTIVITY = "nextActivity";
	public static final String REQUEST_CODE = "request code";

	private Class<?> nextActivity;
	public static final String ASSOCIATIONS_LIST = "List";
	public static final String WAITS_RESULT = "waits result";
	
	public static final int CONNECTION_CODE = 1;
	public static final int REGISTRATION_CODE = 2;
	public static final int INC_PROGRESS_CODE = 3;

	private final ArrayList<HttpResponse> levelsResponses = new ArrayList<HttpResponse>();
	private LevelList levelList;
	
	private User user;

	private final String DEFAULT_LEVEL_1_1 = "{\"authorId\":1,\"content\":\"1 2 1,1 0 1,1 3 1\",\"id\":1,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"}],\"maxInstructions\":2,\"name\":\"Niveau 1\",\"structuredContent\":[{\"item\":[1,2,1]},{\"item\":[1,0,1]},{\"item\":[1,3,1]}],\"structuredInstructions\":[1]}";
	private final String DEFAULT_LEVEL_1_2 = "{\"authorId\":1,\"content\":\"1 1 1,2 0 3,1 1 1\",\"id\":2,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"}],\"maxInstructions\":3,\"name\":\"Niveau 2\",\"structuredContent\":[{\"item\":[1,1,1]},{\"item\":[2,0,3]},{\"item\":[1,1,1]}],\"structuredInstructions\":[1,3]}";
	private final String DEFAULT_LEVEL_1_3 = "{\"authorId\":1,\"content\":\"1 2 1,1 0 1,1 0 1,1 3 1\",\"id\":3,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"}],\"maxInstructions\":2,\"name\":\"Niveau 3\",\"structuredContent\":[{\"item\":[1,2,1]},{\"item\":[1,0,1]},{\"item\":[1,0,1]},{\"item\":[1,3,1]}],\"structuredInstructions\":[1,10]}";
	private final String DEFAULT_LEVEL_1_4 = "{\"authorId\":1,\"content\":\"2 1 3,0 1 0,0 0 0\",\"id\":4,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"}],\"maxInstructions\":4,\"name\":\"Niveau 4\",\"structuredContent\":[{\"item\":[2,1,3]},{\"item\":[0,1,0]},{\"item\":[0,0,0]}],\"structuredInstructions\":[1,3,10]}";
	private final String DEFAULT_LEVEL_1_5 = "{\"authorId\":1,\"content\":\"2 1 1 1,0 0 1 1,1 0 0 1,1 1 0 3\",\"id\":5,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"}],\"maxInstructions\":5,\"name\":\"Niveau 5\",\"structuredContent\":[{\"item\":[2,1,1,1]},{\"item\":[0,0,1,1]},{\"item\":[1,0,0,1]},{\"item\":[1,1,0,3]}],\"structuredInstructions\":[1,3,4,10]}";

	private final String DEFAULT_LEVEL_2_1 = "{\"authorId\":1,\"content\":\"2 1 1 1,0 1 1 1,0 1 1 1,0 0 0 3\",\"id\":6,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveBackward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":2,\"imageUrl\":\"images/doc/reculer.png\",\"name\":\"Reculer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"for (var i%line% = 0; i%line% < 3; ++i%line%)\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":5,\"imageUrl\":\"images/doc/répéter_n.png\",\"name\":\"Répeter 3 fois\"}],\"maxInstructions\":5,\"name\":\"Niveau 1\",\"structuredContent\":[{\"item\":[2,1,1,1]},{\"item\":[0,1,1,1]},{\"item\":[0,1,1,1]},{\"item\":[0,0,0,3]}],\"structuredInstructions\":[1,2,4,5]}";
	private final String DEFAULT_LEVEL_2_2 = "{\"authorId\":1,\"content\":\"3 0 0,1 1 0,0 0 0,2 1 1\",\"id\":7,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveBackward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":2,\"imageUrl\":\"images/doc/reculer.png\",\"name\":\"Reculer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"for (var i%line% = 0; i%line% < 3; ++i%line%)\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":5,\"imageUrl\":\"images/doc/répéter_n.png\",\"name\":\"Répeter 3 fois\"}],\"maxInstructions\":5,\"name\":\"Niveau 2\",\"structuredContent\":[{\"item\":[3,0,0]},{\"item\":[1,1,0]},{\"item\":[0,0,0]},{\"item\":[2,1,1]}],\"structuredInstructions\":[1,2,3,5]}";
	private final String DEFAULT_LEVEL_2_3 = "{\"authorId\":1,\"content\":\"2 1 1 1,0 1 1 1,0 1 1 1,0 0 0 3\",\"id\":8,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoLeft())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":7,\"imageUrl\":\"images/doc/si_gauche.png\",\"name\":\"Si chemin à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"}],\"maxInstructions\":4,\"name\":\"Niveau 3\",\"structuredContent\":[{\"item\":[2,1,1,1]},{\"item\":[0,1,1,1]},{\"item\":[0,1,1,1]},{\"item\":[0,0,0,3]}],\"structuredInstructions\":[1,3,7,10]}";
	private final String DEFAULT_LEVEL_2_4 = "{\"authorId\":1,\"content\":\"2 1 1 1,0 0 0 1,1 1 0 1,1 1 0 1,0 0 0 1,3 1 1 1\",\"id\":9,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoLeft())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":7,\"imageUrl\":\"images/doc/si_gauche.png\",\"name\":\"Si chemin à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoRight())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":8,\"imageUrl\":\"images/doc/si_droite.png\",\"name\":\"Si chemin à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"}],\"maxInstructions\":6,\"name\":\"Niveau 4\",\"structuredContent\":[{\"item\":[2,1,1,1]},{\"item\":[0,0,0,1]},{\"item\":[1,1,0,1]},{\"item\":[1,1,0,1]},{\"item\":[0,0,0,1]},{\"item\":[3,1,1,1]}],\"structuredInstructions\":[1,3,4,7,8,10]}";
	private final String DEFAULT_LEVEL_2_5 = "{\"authorId\":1,\"content\":\"1 1 1 1 2 1,0 0 3 1 0 1,0 1 1 1 0 0,0 1 1 1 0 1,0 0 0 0 0 0,1 1 1 0 1 1\",\"id\":10,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoLeft())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":7,\"imageUrl\":\"images/doc/si_gauche.png\",\"name\":\"Si chemin à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoRight())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":8,\"imageUrl\":\"images/doc/si_droite.png\",\"name\":\"Si chemin à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'à l'arrivée\"}],\"maxInstructions\":4,\"name\":\"Niveau 5\",\"structuredContent\":[{\"item\":[1,1,1,1,2,1]},{\"item\":[0,0,3,1,0,1]},{\"item\":[0,1,1,1,0,0]},{\"item\":[0,1,1,1,0,1]},{\"item\":[0,0,0,0,0,0]},{\"item\":[1,1,1,0,1,1]}],\"structuredInstructions\":[1,3,4,7,8,10]}";

	private final String DEFAULT_LEVEL_3_1 = "{\"authorId\":1,\"content\":\"1 1 1 1 1 1,0 0 0 0 1 1,0 1 1 0 1 1,2 1 0 0 0 3,0 1 1 1 1 1,0 1 1 1 1 1\",\"id\":11,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoForward())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":6,\"imageUrl\":\"images/doc/si_devant.png\",\"name\":\"Si chemin devant\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (!player.canGoForward())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":11,\"imageUrl\":\"images/doc/si_non_devant.png\",\"name\":\"Si PAS de chemin devant\"}],\"maxInstructions\":5,\"name\":\"Niveau 1\",\"structuredContent\":[{\"item\":[1,1,1,1,1,1]},{\"item\":[0,0,0,0,1,1]},{\"item\":[0,1,1,0,1,1]},{\"item\":[2,1,0,0,0,3]},{\"item\":[0,1,1,1,1,1]},{\"item\":[0,1,1,1,1,1]}],\"structuredInstructions\":[1,4,6,11,10]}";
	private final String DEFAULT_LEVEL_3_2 = "{\"authorId\":1,\"content\":\"1 2 1 1 0 3,1 0 1 1 0 1,1 0 0 1 0 1,0 0 1 1 0 1,1 0 1 1 0 1,0 0 0 0 0 1\",\"id\":12,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoLeft())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":7,\"imageUrl\":\"images/doc/si_gauche.png\",\"name\":\"Si chemin à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoRight())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":8,\"imageUrl\":\"images/doc/si_droite.png\",\"name\":\"Si chemin à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (!player.canGoForward())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":11,\"imageUrl\":\"images/doc/si_non_devant.png\",\"name\":\"Si PAS de chemin devant\"}],\"maxInstructions\":7,\"name\":\"Niveau 2\",\"structuredContent\":[{\"item\":[1,2,1,1,0,3]},{\"item\":[1,0,1,1,0,1]},{\"item\":[1,0,0,1,0,1]},{\"item\":[0,0,1,1,0,1]},{\"item\":[1,0,1,1,0,1]},{\"item\":[0,0,0,0,0,1]}],\"structuredInstructions\":[1,3,4,7,8,10,11]}";
	private final String DEFAULT_LEVEL_3_3 = "{\"authorId\":1,\"content\":\"0 0 0 0 0 2 1,1 0 1 1 1 1 1,1 0 0 0 0 0 1,1 0 1 1 1 0 1,1 1 0 0 0 0 1,1 3 1 0 1 1 1,1 0 0 0 1 1 1\",\"id\":13,\"instructionsList\":[{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.moveForward();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":1,\"imageUrl\":\"images/doc/avancer.png\",\"name\":\"Avancer\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnLeft();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":3,\"imageUrl\":\"images/doc/pivoter_gauche.png\",\"name\":\"Pivoter à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":0,\"category\":0,\"code\":\"player.turnRight();\",\"color\":65,\"description\":\"Description de l'instruction Avancer\",\"id\":4,\"imageUrl\":\"images/doc/pivoter_droite.png\",\"name\":\"Pivoter à droite\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":2,\"code\":\"if (player.canGoLeft())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":7,\"imageUrl\":\"images/doc/si_gauche.png\",\"name\":\"Si chemin à gauche\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":1,\"category\":1,\"code\":\"while (!player.hasArrived())\",\"color\":100,\"description\":\"Description de l'instruction Avancer\",\"id\":10,\"imageUrl\":\"images/doc/répéter_arrivée.png\",\"name\":\"Répeter jusqu'a l'arrivée\"},{\"animationUrl\":\"images/doc/avancer.gif\",\"block\":2,\"category\":2,\"code\":\"if (player.canGoForward())\",\"color\":200,\"description\":\"Description de l'instruction Avancer\",\"id\":15,\"imageUrl\":\"images/doc/si_devant_sinon.png\",\"name\":\"Si chemin devant\"}],\"maxInstructions\":6,\"name\":\"Niveau 3\",\"structuredContent\":[{\"item\":[0,0,0,0,0,2,1]},{\"item\":[1,0,1,1,1,1,1]},{\"item\":[1,0,0,0,0,0,1]},{\"item\":[1,0,1,1,1,0,1]},{\"item\":[1,1,0,0,0,0,1]},{\"item\":[1,3,1,0,1,1,1]},{\"item\":[1,0,0,0,1,1,1]}],\"structuredInstructions\":[1,10,15,3,4,7]}";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Bundle extras = this.getIntent().getExtras();
		user = extras.getParcelable(MainActivity.USER);

		boolean waitsResult = extras.getBoolean(SplashActivity.WAITS_RESULT);

		if (waitsResult) {
			getResultAndRespond(extras);
		} else {
			getResultAndLaunch(extras);
		}
	}

	/**
	 * Fais une requête et renvoie la réponse à l'activité précédente
	 * @param extras bundle où sont stockées les informations de l'activité précédente
	 */
	private void getResultAndRespond(Bundle extras) {
		final String URL = SERVER_URL + extras.getString(RESOURCE);
		Object bean = extras.getParcelable(BEAN);
		final int requestCode = extras.getInt(REQUEST_CODE);
		ResponseTask rpTask = new ResponseTask() {

			@Override
			public void act(Activity activity, HttpResponse[] responses) {
				Intent intent = new Intent();
				intent.putExtra(RESPONSE, responses[0]);
				
				setResult(requestCode, intent);
				finish();
			}
		};
		if (bean != null)
			new RestTask(this, rpTask, bean).execute(URL);
		else
			new RestTask(this, rpTask).execute(URL);
	}

	/**
	 * Fais une requête et renvoie la réponse à l'activité suivante
	 * @param extras bundle où sont stockées les informations de l'activité précédente
	 */
	private void getResultAndLaunch(Bundle extras) {
		final String nextActivity = extras.getString(SplashActivity.NEXT_ACTIVITY);
		String resource = extras.getString(SplashActivity.RESOURCE);

		try {
			if (!nextActivity.isEmpty())
				setNextActivity(Class.forName(nextActivity));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ResponseTask rpTask = new ResponseTask() {

			@Override
			public void act(Activity o, HttpResponse[] response) {
				//TODO : gérer le cas où la réponse fail
				Intent intent = new Intent((SplashActivity) o, ((SplashActivity) o).getNextActivity());

				if ("fr.dashingames.ludicode_android.activities.GameActivity".equals(nextActivity)) {
					((SplashActivity) o).getLevelsFromList(response[0], intent);
				} else if ("fr.dashingames.ludicode_android.activities.LevelChooserActivity".equals(nextActivity)){
					intent.putExtra(RESPONSE, response[0]);
					intent.putExtra(MainActivity.USER, user);
					startActivity(intent);
					finish(); 
				}
			} 
		};

		new RestTask(this, rpTask).execute(SERVER_URL + resource);
	}

	/**
	 * Réalise les requêtes pour récupérer les niveaux correspondant à la liste passée
	 * en paramètre
	 * @param listResponse Réponse du serveur contenant la liste de niveaux
	 * @param intent intent à utiliser pour envoyer le résultat
	 */
	public void getLevelsFromList(HttpResponse listResponse, final Intent intent) {
		if (listResponse.isSuccessful())
			levelList = (LevelList) JsonUtils.populateObjectFromJSON(LevelList.class, listResponse.getJSON());
		ResponseTask rpTask = new ResponseTask() {

			@Override
			public void act(Activity activity, HttpResponse[] responses) {
				((SplashActivity) activity).addLevelResponses(responses, intent);

				intent.putExtra(MainActivity.USER, user);
				startActivity(intent);
				finish();
			}
		};

		if (levelList != null) {
			String[] urls = new String[levelList.getLevelsAssociation().length];

			int i = 0;
			for (LevelListAssociation asso : levelList.getLevelsAssociation()) {
				urls[i] = SERVER_URL + "/levels/" + asso.getIdLevel();
				i++;
			}
			new RestTask(this, rpTask).execute(urls);
		} else {
			String resource = this.getIntent().getExtras().getString(SplashActivity.RESOURCE);
			int id = Integer.parseInt(resource.substring(resource.lastIndexOf('/') + 1, resource.length()));
			HttpResponse[] levelsResponses;
			if (id == 1) {
				levelsResponses = new HttpResponse[5];
				levelsResponses[0] = new HttpResponse(DEFAULT_LEVEL_1_1, true);
				levelsResponses[1] = new HttpResponse(DEFAULT_LEVEL_1_2, true);
				levelsResponses[2] = new HttpResponse(DEFAULT_LEVEL_1_3, true);
				levelsResponses[3] = new HttpResponse(DEFAULT_LEVEL_1_4, true);
				levelsResponses[4] = new HttpResponse(DEFAULT_LEVEL_1_5, true);
			} else if (id == 2) {
				levelsResponses = new HttpResponse[5];
				levelsResponses[0] = new HttpResponse(DEFAULT_LEVEL_2_1, true);
				levelsResponses[1] = new HttpResponse(DEFAULT_LEVEL_2_2, true);
				levelsResponses[2] = new HttpResponse(DEFAULT_LEVEL_2_3, true);
				levelsResponses[3] = new HttpResponse(DEFAULT_LEVEL_2_4, true);
				levelsResponses[4] = new HttpResponse(DEFAULT_LEVEL_2_5, true);
			} else {
				levelsResponses = new HttpResponse[3];
				levelsResponses[0] = new HttpResponse(DEFAULT_LEVEL_3_1, true);
				levelsResponses[1] = new HttpResponse(DEFAULT_LEVEL_3_2, true);
				levelsResponses[2] = new HttpResponse(DEFAULT_LEVEL_3_3, true);
			}
			rpTask.act(this, levelsResponses);
		}

	}

	/**
	 * Ajoute les HttpResponses contenant les niveaux à l'intent
	 * @param responses
	 * @param intent
	 */
	protected void addLevelResponses(HttpResponse[] responses, Intent intent) {
		if (responses != null) {
			for (HttpResponse resp : responses) {
				levelsResponses.add(resp);
			}
			intent.putParcelableArrayListExtra(GameActivity.LEVELS_RESPONSES, levelsResponses);
		}
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

	public Class<?> getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(Class<?> nextActivity) {
		this.nextActivity = nextActivity;
	}

}
