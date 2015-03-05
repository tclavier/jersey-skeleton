/**
 * 
 */

var TILE_WIDTH = 50;
var TILE_HEIGHT = 50;
var gridWidth = 8;
var gridHeight = 6;

var COLORS = ["#424242", "#000000", "#FF0000", "#FFFF00"];

var grid = [[]];

var canvas = document.getElementById("editorCanvas");
var ctx = canvas.getContext("2d");

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

function drawGrid() {
	ctx.strokeStyle = "#000000";
	
	for (var i = 0; i < gridWidth; i++) {
		for (var j = 0; j < gridHeight; j++) {
			ctx.fillStyle = COLORS[grid[i][j]];
			ctx.fillRect(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
			ctx.strokeRect(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		}
	}
}

function setTileType(x, y, type) {
	if (x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) 
		return;
	
	grid[x][y] = (grid[x][y] + 1) % 4;
}

canvas.addEventListener("mousedown", doClick, false);

function doClick(event) {
	// position sur le canvas
	var canvasX = event.pageX - canvas.offsetLeft;
	var canvasY = event.pageY - canvas.offsetTop;
	// position dans la grille
	var gridX = Math.floor(canvasX / TILE_WIDTH);
	var gridY = Math.floor(canvasY / TILE_HEIGHT);
	
	console.log(canvasY / TILE_HEIGHT);
	
	console.log(canvasX + ", " + canvasY + " : " + gridX + ", " + gridY);
	
	setTileType(gridX, gridY, 42);
	drawGrid();
}

initGrid(gridWidth, gridHeight);
drawGrid();