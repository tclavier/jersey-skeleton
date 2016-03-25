var levelList;
var levelId;
var currentLevel = urlParam("level");
var currentList = urlParam("list");

function levelFinished() {
	
	if(levelList == undefined) {
		
		// le niveau n'appartient pas à une liste
		// On ne redirige pas et on affiche un message 
		$("#congratsModal .modal-body").html("Vous avez fini le niveau avec succés !");
		$("#congratsModal").modal("show");
	} else {
		// Le niveau appartient à une liste
		console.log(levelList.levelsAssociation.length - 1);
		console.log(currentLevel);
		if(currentLevel < levelList.levelsAssociation.length - 1){
			$("#endLevelModal").modal("show");
		} else {
			//$("#congratsModal .modal-body").html("");
			$("#congratsModal").modal("show");
		}
	}
	
	saveProgress();
}

function saveProgress() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/levelProgress/putProgress/"+Cookies["id"]+"/"+levelId,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			if (data.success) {
				console.log("Niveau sauvegarde !");
			} else {
				console.log(data.message);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}

function goToNextLevel() {
	location.href = "game.html?list=" + currentList + "&level=" + (parseInt(currentLevel) + 1);
}



$(document).ready(function() {

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/

	
	function createLevelPagination() {
		var nav = $("#levels_pagination");
		nav.html("");
		for(var i = 0 ; i < levelList.levelsAssociation.length ; i++) {
			var activeClass = (i == currentLevel ? "active" : "");
			nav.append('<li class="' + activeClass + '">'
						+'<a href="game.html?list=' + currentList + '&level=' + i +'">' + (i+1) + '</a>'
						+'</li>');
		}
	}
	
	function createLevelTitle(data) {
		var listTitle = data.levelList == undefined ? "" : data.levelList.name + " : ";
		$("#level_title").html(listTitle + data.name);
	}

	function handleLevel(data) {
		window.levelData = data;
		sessionStorage.levelType = data.levelType;
		
		levelList = data.levelList;
		levelId = data.id;
		
		$("#max_instruction").html(data.maxInstructions);
		createLevelTitle(data);
		if(levelList != null)
			createLevelPagination();
	}
	
	
	// charge le niveau d'id "levelId"
	function loadLevel(levelId) {
		$.getJSON("v1/levels/" + levelId, function(data) {
			handleLevel(data);
		});
	}
	
	
	// charge le niveau numéro "position" dans la liste d'id "idList"
	function loadLevelInList(position, idList) {
		$.getJSON("v1/levels/list/" + idList + "/level/" + position, function(data) {
			handleLevel(data);
		});
	}
	
	
	if( currentLevel != null) {
		if(currentList == null) {
			loadLevel(currentLevel);
		} else {
			loadLevelInList(currentLevel, currentList);
		}
	} else {
		location.replace("levels.html")
	}

	/*$("#skip_level").click(function() {
		if(nextLevelId > 0){
			goToNextLevel();
		} else {
			// Afficher une fenêtre de félicitation ?
		}
	});*/
	
	$("#nextLevelButton").click(function() {
		goToNextLevel();
	});
	
	$("#levelPageButton").click(function() {
		location.href = "levels.html";
	});





});

