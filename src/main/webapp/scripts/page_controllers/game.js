var nextLevelId = 0;

function levelFinished() {
	$("#endLevelModal").modal("show");
}

function goToNextLevel() {
	if(nextLevelId > 0){
		location.replace("game.html#" + nextLevelId);
		location.reload();
	} else {
		// Afficher une fenêtre de félicitation ?
	}
}

$(document).ready(function() {

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/



	// charge le niveau d'id "levelId" et renvoie le niveau
	function loadLevel(levelId) {
		$.getJSON("v1/levels/" + levelId, function(data) {
			window.levelData = data;
			nextLevelId = data.nextLevelId;
			$("#level_title").html(data.name);
			$("#max_instruction").html(data.maxInstructions);
		});
	}
	if(location.hash != "") {
		loadLevel(location.hash.substring(1));
	} else {
		location.replace("levels.html")
	}

	$("#skip_level").click(function() {
		if(nextLevelId > 0){
			goToNextLevel();
		} else {
			// Afficher une fenêtre de félicitation ?
		}
	});
	
	$("#nextLevelButton").click(function() {
		goToNextLevel();
	});





});

