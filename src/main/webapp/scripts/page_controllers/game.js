$(document).ready(function() {

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/

	// charge le niveau d'id "levelId" et renvoie le niveau
	function loadLevel(levelId) {
		$.getJSON("v1/levels/" + levelId, function(data) {
			// TODO : Load level into canevas and html here
			console.log(data);
		});
	}
	
	loadLevel(location.hash.substring(1));
	
});

