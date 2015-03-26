/**
 * 
 */

var TILE_WIDTH = 42;
var TILE_HEIGHT = 42;

var MIN_GRID_WIDTH = 3;
var MAX_GRID_WITH = 10;
var MIN_GRID_HEIGHT = 3;
var MAX_GRID_HEIGHT = 10;

var PICKER_TILE_WIDTH = 80;
var PICKER_TILE_HEIGHT = 80;
var FONT_SIZE = 14;
var PICKER_HIGHLIHT_OFFSET = 5;
var HIGHLIGHT_COLOR = "#0000FF";

var COLORS = ["#EEEEEE", "#000000", "#FF0000", "#FFFF00"];
var NAMES = ["Vide", "Mur", "Départ", "Arrivée"];

var gridWidth = 6;
var gridHeight = 7;
var grid = [[]];
var selectedType = 0;
var modified = 0;

var gridCanvas;
var gridCtx;

var pickerCanvas;
var pickerCtx;

var canvasContainer;

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

	modified = 0;

	resizeGridCanvas();
}

function drawGrid() {
	gridCtx.strokeStyle = "#000000";

	for (var i = 0; i < gridWidth; i++) {
		for (var j = 0; j < gridHeight; j++) {
			gridCtx.fillStyle = COLORS[grid[i][j]];
			gridCtx.fillRect(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
			gridCtx.strokeRect(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		}
	}
}

function setTileType(x, y, type) {
	if (x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) 
		return;

	grid[x][y] = type;
	modified = 1;
	checkLevel();
}

function doGridClick(e) {
	// position sur le gridCanvas
	var canvasX = e.pageX - $(e.target).offset().left;
	var canvasY = e.pageY - $(e.target).offset().top;

	// position dans la grille
	var gridX = Math.floor(canvasX / TILE_WIDTH);
	var gridY = Math.floor(canvasY / TILE_HEIGHT);

	setTileType(gridX, gridY, selectedType);
	drawGrid();
}

function doPickerClick(e) {
	// position sur le canvas
	var canvasY = e.pageY - $(e.target).offset().top;
	// position dans la grille
	var gridY = Math.floor(canvasY / PICKER_TILE_HEIGHT);

	selectedType = gridY;
	highLightSelectedTile(selectedType, HIGHLIGHT_COLOR);
}

function initPicker() {
	pickerCanvas.addEventListener("mousedown", doPickerClick, false);

	pickerCanvas.width = PICKER_TILE_WIDTH;
	pickerCanvas.height = PICKER_TILE_HEIGHT * COLORS.length;
	pickerCtx.strokeStyle = "#000000";
	pickerCtx.font = FONT_SIZE + "px Arial";
	pickerCtx.textAlign = "center";

	for (var i = 0; i < COLORS.length; i++) {
		var rectX = (PICKER_TILE_WIDTH - TILE_WIDTH) / 2;
		var verticalMargin = (PICKER_TILE_HEIGHT - TILE_HEIGHT - FONT_SIZE) / 3;
		var rectY = i * PICKER_TILE_HEIGHT + verticalMargin;
		var textY = rectY + verticalMargin + TILE_HEIGHT + FONT_SIZE;

		pickerCtx.fillStyle = COLORS[i];
		pickerCtx.fillRect(rectX, rectY, TILE_WIDTH, TILE_HEIGHT);
		pickerCtx.strokeRect(rectX, rectY, TILE_WIDTH, TILE_HEIGHT);

		pickerCtx.fillStyle ="#000000";
		pickerCtx.fillText(NAMES[i], PICKER_TILE_WIDTH / 2, textY);
	}
}

function resizeGridCanvas() {
	gridCanvas.width = TILE_WIDTH * gridWidth;
	gridCanvas.height = TILE_HEIGHT * gridHeight;

	drawGrid();
}

function highLightSelectedTile(index, color) {
	initPicker();
	pickerCtx.strokeStyle = color;
	// j'ajoute 0.5 aux coord pour effacer la ligne précédente (sinon js fait un mélange des deux couleurs)
	pickerCtx.strokeRect(PICKER_HIGHLIHT_OFFSET + 0.5, index * PICKER_TILE_HEIGHT + PICKER_HIGHLIHT_OFFSET + 0.5
			, PICKER_TILE_WIDTH - 2 * PICKER_HIGHLIHT_OFFSET, PICKER_TILE_HEIGHT - 2 * PICKER_HIGHLIHT_OFFSET);
}

function getSizeInput() {
	var width = parseInt($("#gridWidth").val());
	var height = parseInt($("#gridHeight").val());

	if (isNaN(width) || isNaN(height)
			|| width < MIN_GRID_WIDTH || width > MAX_GRID_WITH
			|| height < MIN_GRID_HEIGHT || height > MAX_GRID_HEIGHT) {
		return [];
	}

	return [width, height];
}

function doChangeSizeClick() {	
	if (getSizeInput().length === 0)
		return;

	if (modified === 1)
		$('#changeSizeModal').modal('show');
	else
		changeSize();
}

function changeSize() {
	var dimension = getSizeInput();

	if (dimension.length === 0)
		return;

	var width = dimension[0];
	var height = dimension[1];

	gridWidth = width;
	gridHeight = height;
	centerCanvas();
	initGrid(gridWidth, gridHeight);
	$('#changeSizeModal').modal('hide');
}

function centerCanvas() {
	var gridCanvasHeight = gridHeight * TILE_HEIGHT;
	var pickerCanvasHeight = PICKER_TILE_HEIGHT * COLORS.length;
	var margin;
	var containerWidth = $('#canvasContainer').width();

	if (gridCanvasHeight < pickerCanvasHeight) {
		margin = (pickerCanvasHeight - gridCanvasHeight) / 2;
		$('#editorCanvas').css("margin-bottom", margin);
		$('#pickerCanvas').css("margin-bottom", 0);
	} else {
		margin = (gridCanvasHeight - pickerCanvasHeight) / 2;
		$('#pickerCanvas').css("margin-bottom", margin);
		$('#editorCanvas').css("margin-bottom", 0);
	}

	margin = (containerWidth - PICKER_TILE_WIDTH - gridWidth * TILE_WIDTH) / 2;
	$('#editorCanvas').css("margin-left", margin);
}

function addError(message) {
	var list = $("#errors");
	var entry = $('<li class="alert alert-danger"></li>');
	entry.append(message);
	list.append(entry);
}

function checkLevel() {
	var starts = 0;
	var goals = 0;
	var validity = true;

	for (var i = 0; i < gridWidth; i++) {
		for (var j = 0; j < gridHeight; j++) {
			if (grid[i][j] === 2)
				starts ++;
			if (grid[i][j] === 3)
				goals ++;			
		}
	}

	document.getElementById("errors").innerHTML = "";
	$("#change_size").prop("disabled", false);

	/*if (sessionStorage.getItem("isConnected") != "true") {
		addError("Vous devez être connecté pour créer un niveau !");
		validity = false;
	}*/

	if (starts === 0) {
		addError("Le niveau doit contenir une case de départ");
		validity = false;
	}
	if (starts > 1) {
		addError("Le niveau ne peut contenir qu'une seule case de départ");
		validity = false;
	}
	if (goals === 0) {
		addError("Le niveau doit contenir une case d'arrivée");
		validity = false;
	}
	if (goals > 1) {
		addError("Le niveau ne peut contenir qu'une seule case d'arrivée");
		validity = false;
	}
	if ($("#levelName").val().length === 0) {
		addError("Le niveau doit avoir un nom");
		validity = false;
	}
	if (!(parseInt($("#levelList").val()) > 0)) {
		addError("Veuillez sélectionner une liste");
		validity = false;
	}
//	if (!($("#instructionsNumber").val() > 0)) {
//	addError("Nombre maximum d'instructions invalide");
//	validity = false;
//	}
//	if (getInstructions().length === 0) {
//	addError("Le niveau doit avoir au moins une instruction autorisée");
//	validity = false;
//	}
	if (getSizeInput().length === 0) {
		addError("Les dimensions doivent être comprises entre " 
				+ MIN_GRID_WIDTH + "x" + MIN_GRID_HEIGHT + " et " + MAX_GRID_WITH + "x" + MAX_GRID_HEIGHT);
		$("#change_size").prop("disabled", true);
	}

	$("#save_button").prop("disabled", !validity);

	return validity;
}

function handleKeyPress(e) {
	var key = e.keyCode || e.which;
	if (key === 13) {
		doChangeSizeClick();
	} else {

	}
}

function transpose(matrix) {
	var transpo = [[]];

	for (var i = 0; i < grid.length; i++) {
		for (var j = 0; j < grid[0].length; j++) {
			if (transpo[j] === undefined) {
				transpo[j] = [];
			}
			transpo[j][i] = grid[i][j];
		}
	}

	return transpo;
}

function loadLevelLists(selectLast) {
	$("#levelList").empty();
	$.getJSON("v1/levelLists/me/" + Cookies["id"], function(data) {
		console.log(data);
		for(var i = 0 ; i < data.length ; i++) {
			var item = $('<option value="' + data[i].id + '">' + data[i].name + '</option>');
			item.appendTo("#levelList");
		}
		checkLevel();

		if (selectLast) {
			console.log("last:" + data[data.length - 1].id);
			$('#levelList').val(data[data.length - 1].id);
		} else {
			console.log("session: " + window.sessionStorage.list);
			$('#levelList').val(window.sessionStorage.list);
		}
	});
}

function parseSessionLevel() {
	var tiles = window.sessionStorage.level.split(",");

	for (var i = 0; i < gridWidth; i++) {
		for (var j = 0; j < gridHeight; j++) {
			grid[i][j] = parseInt(tiles[j * gridWidth + i]);
		}
	}
}

function saveLevel() {
	// TODO : enregistrement db
	var validity = checkLevel();
	if (validity) {
		var structuredContent = [];

		sessionStorage.level = transpose(grid);
		sessionStorage.name = $("#levelName").val();
		sessionStorage.width = gridWidth;
		sessionStorage.height = gridHeight;
		sessionStorage.list = $('#levelList').val();

		window.location.assign("/test.html");
	}
	else 
		alert("Niveau invalide");
}

function createList() {
	var json = JSON.stringify({
		"name": $("#newListName").val(),
	});

	console.log(json);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/levelLists/create/" + Cookies["id"],
		dataType : "json",
		data : json ,
		success : function(data, textStatus, jqXHR) {
			if (data.success) {
				console.log("Success!");
			} else {
				alert("Oh mince...");
			}
			loadLevelLists(true);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});


	$('#addListModal').modal('hide');
}

function loadSessionInfo() {
	var instrNum = parseInt(window.sessionStorage.instructionsNumber);
	if (window.sessionStorage.level !== undefined) {
		gridWidth = parseInt(window.sessionStorage.width);
		gridHeight = parseInt(window.sessionStorage.height);
		initGrid(gridWidth, gridHeight);
		parseSessionLevel();
		drawGrid();
		modified = 1;
		$('#gridWidth').val(gridWidth);
		$('#gridHeight').val(gridHeight);
	} else {
		initGrid(gridWidth, gridHeight);
	}
	$('#levelName').val(window.sessionStorage.name);

	$('#instructionsNumber').prop("min", instrNum);
	$('#instructionsNumber').val(instrNum);
}

function initSpinners() {
	var widthSpinner = document.getElementById("gridWidth");
	var heightSpinner = document.getElementById("gridHeight");

	heightSpinner.min = MIN_GRID_HEIGHT;
	heightSpinner.max = MAX_GRID_HEIGHT;
	heightSpinner.value = gridHeight;
	widthSpinner.min = MIN_GRID_WIDTH;
	widthSpinner.max = MAX_GRID_WITH;
	widthSpinner.value = gridWidth;
}

$(document).ready(function() {

	gridCanvas = document.getElementById("editorCanvas");
	gridCtx = gridCanvas.getContext("2d");

	pickerCanvas = document.getElementById("pickerCanvas");
	pickerCtx = pickerCanvas.getContext("2d");

	canvasContainer = document.getElementById("canvasContainer");

	gridCanvas.addEventListener("mousedown", function(event) {
		gridCanvas.addEventListener("mousemove", doGridClick, false)
		doGridClick(event);
	}, false);

	document.getElementsByTagName("body")[0].addEventListener("mouseup", function() {
		gridCanvas.removeEventListener("mousemove", doGridClick);
	}, false);

	initPicker();
	highLightSelectedTile(selectedType, HIGHLIGHT_COLOR);
	initSpinners();
	centerCanvas();

	loadLevelLists(window.sessionStorage.list === undefined);	

	loadSessionInfo();

	$("#save_button").click(function(e) {
		e.preventDefault();
		saveLevel();
	});

	$("#change_size").click(function(e) {
		e.preventDefault();
		doChangeSizeClick();
	});

	$("#changeSizeConfirmation").click(function(e) {
		e.preventDefault();
		changeSize();
	});

	$('input').change(function() {
		checkLevel();
	});

	$('input').keyup(function() {
		checkLevel();
	});

	$('#gridWidth').keypress(function(e) {
		handleKeyPress(e);
	});

	$('#gridHeight').keypress(function(e) {
		handleKeyPress(e);
	});

	$('#addNewList').click(function(e) {
		e.preventDefault();
		$('#addListModal').modal('show');
	});

	$("#createListConfirmation").click(function(e) {
		e.preventDefault();
		createList();
	});

	$('#newListName').keypress(function(e) {
		var key = e.keyCode || e.which;
		if (key === 13) {
			createList();
		}
	});

	$('#levelList').change(function() {
		checkLevel();
	});

	checkLevel();
});