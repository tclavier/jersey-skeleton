var listArr = [];

var canvas;
var ctx;

var canvasWidth = 100;
var canvasHeight = 100;

var COLORS = ["#EEEEEE", "#000000", "#FF0000", "#FFFF00"];

var TILE_HEIGHT = 10;
var TILE_WIDTH = 10;
var BORDER_OFFSET = 3;

function loadLists() {
	$.getJSON("v1/levelLists/me/full/" + Cookies['id'], function(data) {
		var listIds = "#list-" + data[0].id;
		for (var i = 0; i < data.length; i++) {

			displayList(data[i]);
			listIds += ", #list-" + data[i].id;	
			listArr.push(data[i].id);
		}
		connectLists(listIds);
	});
}

function displayList(list) {
	var listDiv = $('<div class="panel panel-warning col-md-2 text-center col-md-offset-3"' +
	'style="padding: 0px;">');
	var header = $('<div class="panel-heading">' + list.name + 
			'<div class="info">Cliquez pour afficher/masquer les niveaux</div></div>');	
		
	var ul = $('<ul class="connectedList list-group panel-body"' +
			'id="list-' + list.id + '"></ul>');	

	var item = $('<li class="list-group-item list-group-item-danger" style="margin: 5px; " value="' 
			+ list.id + '">' + list.name + '</li>');
	
	header.click(function() {
		ul.toggle();
	});

	for (var i = 0; i < list.levels.length; i++) {
		var level = createLevelNode(list.levels[i]);

		level.appendTo(ul);
	}

	header.appendTo(listDiv);
	ul.appendTo(listDiv);
	listDiv.appendTo("#container");
	ul.hide();
}

function createLevelNode(level) {
	var node = $('<li class="list-group-item list-group-item-danger" style="margin: 5px; " value="' 
			+ level.id + '">' + level.name + '</li>');

	node.hover(
			function(e) {
				var el = $('#preview');
				el.show();
				movePreview(e);

				drawPreview(level);
				window.addEventListener("mousemove", movePreview, true);
			},function(e) {
				var el = $('#preview');
				el.hide();
				window.removeEventListener("mousemove", movePreview, true);
			});
	
	node.click(function() {
		$('#preview').hide();
	});

	return node;
}

function movePreview(e) {
	var el = $('#preview');
	el.css("top", e.clientY + 12);
	el.css("left", e.clientX - (canvasWidth / 2));
}

function connectLists(lists) {
	$( lists ).sortable({
		connectWith: ".connectedList"
	}).disableSelection();
}

function drawPreview(level) {
	var grid = level.structuredContent;
	canvasHeight = grid.length * TILE_HEIGHT + 2 * BORDER_OFFSET;
	canvasWidth = grid[0].item.length * TILE_WIDTH + 2 * BORDER_OFFSET;
	
	console.log(grid[0]);
	
	canvas.height = canvasHeight;
	canvas.width = canvasWidth;
	
	ctx.fillStyle = "#FF00FF";
	ctx.fillRect(0, 0, canvasWidth, canvasHeight);
	
	for (var i = 0; i < grid.length; i++) {
		for (var j = 0; j < grid[0].item.length; j++) {
			ctx.fillStyle = COLORS[grid[i].item[j]];
			ctx.fillRect(j * TILE_WIDTH + BORDER_OFFSET, i * TILE_HEIGHT + BORDER_OFFSET
					, TILE_WIDTH, TILE_HEIGHT);
		}
	}
}

function sendLists() {
	var lists = [];

	for (var i = 0; i < listArr.length; i++) {
		var id = listArr[i];
		var list = {id: id, levelsAssociation: []};
		var children = $("#list-" + id).children();

		for (var j = 0; j < children.length; j++) {
			console.log(id + "." + j);
			list.levelsAssociation.push({idList: id, idLevel: children[j].value});
		}

		lists.push(list);
	}

	var json = JSON.stringify(lists);

	console.log(json);

	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : "v1/levelLists/me/" + Cookies["id"],
		dataType : "json",
		data : json ,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.success) {
				console.log("success");
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

$(document).ready(function() {
	canvas = document.getElementById("preview");
	ctx = canvas.getContext("2d");

	ctx.fillStyle = "#00FF00";
	ctx.fillRect(0, 0, 100, 100);

	$('#preview').hide();

	loadLists();

	$('#sendLists').click(function() {
		sendLists();
	});	
});