var lockedInstructions;
var instrNum;
var gridWidth;
var gridHeight;
var grid = [[]];

function loadInstructions() {
	$.getJSON("v1/instructions", function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			var label = data[i].name;
			if (data[i].block === 2) {
				label += " ... sinon";
			}
			var item = $('<li style="margin: 5px; " value="' 
					+ data[i].id + '">' + label + '</li>');
			if ($.inArray(data[i].id, lockedInstructions) == -1)
				item.appendTo("#instructions");
			else
				item.appendTo("#lockedInstructions");
			updateCSS(item);
		}
	});
}

function loadSessionInfo() {
	instrNum = parseInt(window.sessionStorage.instructionsNumber) || 0;

	$('#instructionsNumber').prop("min", instrNum);
	$('#instructionsNumber').val(instrNum);
}

function updateCSS(item) {
	var parentId = item.parent().attr("id");
	if (parentId === "instructions") {
		item.prop("class", "list-group-item list-group-item-danger");
	} else if (parentId === "lockedInstructions"){
		item.prop("class", "list-group-item list-group-item-warning");
	} else {
		item.prop("class", "list-group-item list-group-item-success");
	}
}

function loadLockedInstructions() {
	lockedInstructions = JSON.parse(window.sessionStorage.usedInstructions);	
}

function getInstructions() {
	var out = [];
	var children = $("#selectedInstructions").children();

	for (var i = 0; i < children.length; i++) {
		out.push(children[i].value);
	}

	return out.concat(lockedInstructions);
}

function saveLevel() {
	var validity = checkLevel();
	if (validity) {
		gridWidth = parseInt(window.sessionStorage.width);
		gridHeight = parseInt(window.sessionStorage.height);
		initGrid(gridWidth, gridHeight);
		parseSessionLevel();

		sendLevel();
	}
	else 
		alert("Niveau invalide");
}

function parseSessionLevel() {
	var tiles = window.sessionStorage.level.split(",");

	for (var i = 0; i < gridWidth; i++) {
		for (var j = 0; j < gridHeight; j++) {
			grid[i][j] = parseInt(tiles[j * gridWidth + i]);
		}
	}
}

function initGrid(width, height) {
	grid = [[]];

	for (var i = 0; i < width; i++) {
		grid[i] = [];
		for (var j = 0; j < height; j++) {
			grid[i][j] = 0;
		}
	}

	grid[0][0] = 2;
	grid[width - 1][height - 1] = 3;
}

function sendLevel() {
	var structuredContent = [];

	var transpo = [[]];

	for (var i = 0; i < grid.length; i++) {
		for (var j = 0; j < grid[0].length; j++) {
			if (transpo[j] === undefined) {
				transpo[j] = [];
			}
			transpo[j][i] = grid[i][j];
		}
	}

	for(var i = 0 ; i < transpo.length ; i++) {
		structuredContent[i] = {item : transpo[i]};
	}

	var json = JSON.stringify({
		"structuredContent": structuredContent,
		"structuredInstructions": getInstructions(),
		"name": window.sessionStorage.name,
		"maxInstructions": $("#instructionsNumber").val()
	});

	console.log(json);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/levels/add/" + Cookies["id"] + "/" + sessionStorage.list,
		dataType : "json",
		data : json ,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.success) {
				console.log("success");
				doSaveSuccessful();
			} else {
				// TODO : Afficher mesage d'erreur
				alert("Oh mince, une erreur est survenue :(");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}

function authorizeInstructions() {
	$('#instructions').children().prop("class", "list-group-item list-group-item-success")
	.appendTo('#selectedInstructions');
	$('#instructions').empty();
}

function forbidInstructions() {
	$('#selectedInstructions').children().prop("class", "list-group-item list-group-item-danger")
	.appendTo('#instructions');
	$('#selectedInstructions').empty();
}

function checkLevel() {

	if ($("#instructionsNumber").val() < instrNum || !sessionStorage.solutionValidity) {
		$("#save_button").prop("disabled", true);
		return false;
	}

	$("#save_button").prop("disabled", false);
	return true;
}

function doSaveSuccessful() {
	window.sessionStorage.clear();
	$('#saveSuccessfulModal').modal('show');
	console.log("leleelleleleleelellelelele");
}

$(document).ready(function() {

	loadLockedInstructions();
	loadInstructions();
	loadSessionInfo();

	$( "#instructions, #selectedInstructions" ).sortable({
		connectWith: ".instructionsList",
		receive: function(event, ui) {
			updateCSS(ui.item);
			checkLevel();
		}
	}).disableSelection();

	$('#legalizeEverything').click(function() {
		authorizeInstructions();
	});

	$('#forbidEverything').click(function() {
		forbidInstructions();
	});

	$("#save_button").click(function(e) {
		e.preventDefault();
		saveLevel();
	});

	$('input').keyup(function() {
		checkLevel();
	});

	$('input').change(function() {
		checkLevel();
	});

	$('#saveSuccessfulModal').on('hidden.bs.modal', function () {
		window.location.assign("editor.html");
	});
});