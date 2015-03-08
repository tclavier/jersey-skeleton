/**
 * 
 */

$(document).ready(function() {
	

	var TILE_WIDTH = 50;
	var TILE_HEIGHT = 50;

	var MIN_GRID_WIDTH = 3;
	var MAX_GRID_WITH = 10;
	var MIN_GRID_HEIGHT = 3;
	var MAX_GRID_HEIGHT = 10;

	var PICKER_TILE_WIDTH = 90;
	var PICKER_TILE_HEIGHT = 90;
	var FONT_SIZE = 14;
	var PICKER_HIGHLIHT_OFFSET = 5;
	var HIGHLIGHT_COLOR = "#0000FF";

	var COLORS = ["#424242", "#000000", "#FF0000", "#FFFF00"];
	var NAMES = ["Vide", "Mur", "Départ", "Arrivée"];

	var gridWidth = 8;
	var gridHeight = 6;
	var grid = [[]];
	var selectedType = 0;
	var modified = 0;

	var gridCanvas = document.getElementById("editorCanvas");
	var gridCtx = gridCanvas.getContext("2d");

	var pickerCanvas = document.getElementById("pickerCanvas");
	var pickerCtx = pickerCanvas.getContext("2d");
	
	var canvasContainer = document.getElementById("canvasContainer");

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

	function doGridClick(event) {
		// position sur le gridCanvas
		var canvasX = event.pageX- gridCanvas.offsetLeft - canvasContainer.offsetLeft;
		var canvasY = event.pageY - gridCanvas.offsetTop - canvasContainer.offsetTop;
		
		// position dans la grille
		var gridX = Math.floor(canvasX / TILE_WIDTH);
		var gridY = Math.floor(canvasY / TILE_HEIGHT);

		setTileType(gridX, gridY, selectedType);
		drawGrid();
	}

	function doPickerClick(event) {
		// position sur le canvas
		var canvasY = event.pageY - pickerCanvas.offsetTop - canvasContainer.offsetTop;
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

	function changeSize() {
		var width = parseInt($("#gridWidth").val());
		var height = parseInt($("#gridHeight").val());

		if (isNaN(width) || isNaN(height)
				|| width < MIN_GRID_WIDTH || width > MAX_GRID_WITH 
				|| height < MIN_GRID_HEIGHT || height > MAX_GRID_HEIGHT) {
			alert("Dimensions invalides\nLes dimensions doivent être comprises entre"
					+ MIN_GRID_WIDTH + "x" + MIN_GRID_HEIGHT + " et " + MAX_GRID_WITH + "x" + MAX_GRID_HEIGHT);
			return;
		}

		if (modified === 1 && !confirm("Vos modifications vont être effacées, voulez vous continuer?"))
			return;

		gridWidth = width;
		gridHeight = height;
		initGrid(gridWidth, gridHeight);
	}

	function addError(message) {
		var list = $("#errors");
		var entry = $('<li></li>');
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

		if (starts === 0) {
			addError("Le niveau doit contenir une case de départ");
			validity = false;
		}
		if (starts > 1) {
			addError("Le niveau ne peut contenir qu'une case de départ");
			validity = false;
		}
		if (goals === 0) {
			addError("Le niveau doit contenir une case d'arrivée");
			validity = false;
		}
		if (goals > 1) {
			addError("Le niveau ne peut contenir qu'une case d'arrivée");
			validity = false;
		}
		if ($("#levelName").val().length === 0) {
			addError("Le niveau doit avoir un nom");
			validity = false;
		}
		if (!($("#instructionsNumber").val() > 0)) {
			addError("Nombre maximum d'instructions invalide");
			validity = false;
		}
		if (getInstructions().length === 0) {
			addError("Le niveau doit avoir au moins une instruction autorisée");
			validity = false;
		}			

		$("#save_button").prop("disabled", !validity);;

		return validity;
	}

	function handleKeyPress(e) {
		var key = e.keyCode || e.which;
		if (key === 13) {
			changeSize();
		}
	}
	
	function sendLevel() {
		
		var structuredContent = [];
		for(var i = 0 ; i < grid.length ; i++) {
			structuredContent[i] = {item : grid[i]};
		}
		
		var json = JSON.stringify({
			"structuredContent": structuredContent,
			"structuredInstructions": getInstructions(),
			"name": $("#levelName").val(),
			"maxInstructions": $("#instructionsNumber").val()
		});
		
		console.log(json);
		
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : "v1/levels/add/" + document.cookie,
			dataType : "json",
			data : json ,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if(data.success) {
					// TODO : afficher message de succés
					alert("Success!");
				} else {
					// TODO : Afficher mesage d'erreur
					alert("Oh mince...");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('postUser error: ' + textStatus);
			}
		});
	}

	function saveLevel() {
		// TODO : enregistrement db
		var validity = checkLevel();
		if (validity)
			sendLevel();
		else 
			alert("Niveau invalide");
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
	
	function getInstructions() {
		var out = [];
		var checkBoxes = $("#instructionsList").children();
		
		for (var i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].checked === true)
				out.push(checkBoxes[i].value);
		}
		
		return out;
	}

	gridCanvas.addEventListener("mousedown", function() {
		gridCanvas.addEventListener("mousemove", doGridClick, false)
	}, false);
	
	document.getElementsByTagName("body")[0].addEventListener("mouseup", function() {
		gridCanvas.removeEventListener("mousemove", doGridClick);
	}, false);

	initGrid(gridWidth, gridHeight);
	initPicker();
	highLightSelectedTile(selectedType, HIGHLIGHT_COLOR);
	initSpinners();
	
	$("#save_button").click(function() {
		saveLevel();
	});
	
	$("#change_size").click(function() {
		changeSize();
	});
	
	console.log(getInstructions());
});