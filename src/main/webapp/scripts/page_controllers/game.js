$(document).ready(function() {

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/

	// charge le niveau d'id "levelId" et renvoie le niveau
	function loadLevel(levelId) {
		$.getJSON("v1/levels/" + levelId, function(data) {
            window.levelData = data;
		});
	}
	
	loadLevel(location.hash.substring(1));
	
});

