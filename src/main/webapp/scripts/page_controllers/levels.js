$(document).ready(function() {
	/**/

	
	function loadLevelList(userNames) {
		$.getJSON("v1/levelLists", function(data) {
			showLevels(data, userNames);
		});
	}
	
	function loadUserNames() {
		var userNames = [];
		$.getJSON("v1/users", function(data) {			
			for (var i = 0; i < data.length; i++) {
				userNames[data[i].id] = data[i].name;
			}
			loadLevelList(userNames);
		});
	}
	
	function showLevels(data, userNames) {
		for(var i = 0 ; i < data.length ; i++) {
			var thumbnail = $('<div class="thumbnail level-element"></div>');
			var caption = $('<div class="caption"></div>');
			caption.append($('<h3>' + data[i].name + '</h3> <h5>' + data[i].levelCount + ' niveaux</h5>'));
			caption.append($('<h6>Auteur: <a href="profile.html?id=' + data[i].idAuthor +'" >' 
					+ userNames[data[i].idAuthor] + '</a></h6>'));
			caption.append($('<p><a href="game.html?level=0&list=' + data[i].id + '" class="btn btn-primary level_preview" role="button">Jouer !</a></p>'));
			thumbnail.append(caption);
			$("#tutorial_lists").append(thumbnail);
			
		}
	}
	
	
	loadUserNames();
});