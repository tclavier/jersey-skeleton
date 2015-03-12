$(document).ready(function() {
	/**/

	
	function loadLevelList() {
		$.getJSON("v1/levelList", function(data) {
			showLevels(data);
		});
	}
	
	function showLevels(data) {
		for(var i = 0 ; i < data.length ; i++) {
			var thumbnail = $('<div class="thumbnail level-element"></div>');
			var caption = $('<div class="caption"></div>');
			caption.append($('<h3>' + data[i].name + '</h3> <h5>' + data[i].levelCount + ' niveaux</h5>'));
			caption.append($('<p><a href="game.html?level=0&list=' + data[i].id + '" class="btn btn-primary level_preview" role="button">Jouer !</a></p>'));
			thumbnail.append(caption);
			console.log(thumbnail);
			$("#tutorial_lists").append(thumbnail);
		}
	}
	
	
	loadLevelList();
});