var levelList;
var currentLevel = urlParam("level");
var currentList = urlParam("list");

function levelFinished() {
	$("#endLevelModal").modal("show");
}

function goToNextLevel() {
	location.href = "game.html?list=" + currentList + "&level=" + (parseInt(currentLevel) + 1);
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
	sessionStorage.solutionValidity = false;

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/

    var instructions;

	function setLevelTitle(data) {
		var listTitle = data == undefined ? "" : "[TEST] " + data;
		$("#level_title").html(listTitle);
	}

	function handleLevel(data) {
        window.levelData = data;
        
        $("#max_instruction").html(window.levelData.maxInstructions);
		setLevelTitle(window.levelData.name);
	}

    function createLevelData(instructionList) {
        var obj = {};
        // TODO: Mettre vraiment l'auteur
        obj.authorId = -1;

        // Creation du structuredContent
        var structuredContent = [];
        var tiles = sessionStorage.level.split(",");
        var width = parseInt(sessionStorage.width);
        var height = parseInt(sessionStorage.height);

        for (var y = 0; y < height; ++y) {
            for (var x = 0; x < width; ++x) {
                var tileId = parseInt(tiles[y * width + x]);
                if (structuredContent[y] == null)
                    structuredContent[y] = {item: []};

                structuredContent[y].item[x] = tileId;
            }
        }
        
        obj.structuredContent = structuredContent;
        instructions = obj.instructionsList = instructionList;
        obj.maxInstructions = Infinity;
        obj.name = sessionStorage.name;

        return obj;
    }
	
	// Charge toutes les instructions
	function loadInstructions() {
		$.getJSON("v1/instructions", function(instructionList) {
            handleLevel(createLevelData(instructionList));
		});
	}


    if (sessionStorage.level != undefined) {
        loadInstructions();
    }

    $("#saveLevelBtn").click(function() {
       
    });


    $("#editLevelBtn").click(function() {
        window.location.assign("/editor.html");
    });
	
});

