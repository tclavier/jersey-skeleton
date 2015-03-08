$(document).ready(function() {

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/

	var nextLevelId = 0;
	
	// charge le niveau d'id "levelId" et renvoie le niveau
	function loadLevel(levelId) {
		$.getJSON("v1/levels/" + levelId, function(data) {
            window.levelData = data;
            nextLevelId = data.nextLevelId;
		});
	}
	if(location.hash != "")
		loadLevel(location.hash.substring(1));
	else
		location.replace("levels.html")
	
	$("#skip_level").click(function() {
		if(nextLevelId > 0){
			location.replace("game.html#" + nextLevelId);
			location.reload();
		} else {
			// Afficher une fenêtre de félicitation ?
		}
	});
	
});

