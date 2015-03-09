var nextLevelId = 0;

function levelFinished() {
	$("#endLevelModal").modal("show");
}

function goToNextLevel() {
	if(nextLevelId > 0){
		location.replace("game.html?level=" + nextLevelId);
		//location.reload();
	} else {
		// Afficher une fenêtre de félicitation ?
	}
}

function urlParam(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
       return null;
    } else {
       return results[1] || 0;
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
	
	var level = urlParam("level");
	console.log(level);
	if( level != null) {
		loadLevel(level);
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

