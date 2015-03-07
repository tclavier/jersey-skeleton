$(document).ready(function() {
	
	function showProfileInfo(data) {
		$("#info_player").html("");
		$("#info_player").append("<h1>" + data.user.name + "</h1><br />");
		$("#info_player").append("Email :" + data.user.email + "<br />");
		
		
		$("#creations_list").html("");
		for(var i = 0 ; i < data.levelsInfo.length ; i++) {
			var levelInfo = $('<div class="level_info"></div>');
			levelInfo.append('<a href="game.html#' + data.levelsInfo[i].id + '">' + data.levelsInfo[i].name + '</a>');
			$("#creations_list").append(levelInfo);
		}
	}
	
	
	
	if(location.hash == "") {
		$.getJSON("v1/profile/me/" + document.cookie, function(data) {
			console.log(data);
			showProfileInfo(data);
		})
		.error(function() {
			// Utilisateur non loggé
			location.replace("/");
		});
	} else {
		$.getJSON("v1/profile/" + location.hash.substring(1), function(data) {
			console.log(data);
			showProfileInfo(data);
		});
	}
	
});