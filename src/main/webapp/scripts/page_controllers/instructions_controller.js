function loadInstructions() {
	$.getJSON("v1/instructions", function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			var label = data[i].name;
			if (data[i].block === 2) {
				label += " ... sinon";
			}
			var item = $('<li class="list-group-item list-group-item-success" style="margin: 5px; " value="' 
					+ data[i].id + '">' + label + '</li>');
			item.appendTo("#instructions");
			updateCSS(item);
		}
	});
}

function updateCSS(item) {
	var parentId = item.parent().attr("id");
	console.log(parentId);
	if (parentId === "instructions") {
		item.prop("class", "list-group-item list-group-item-danger");
	} else {
		item.prop("class", "list-group-item list-group-item-success");
	}
}

function getInstructions() {
	var out = [];
	var children = $("#selectedInstructions").children();

	for (var i = 0; i < children.length; i++) {
		out.push(children[i].value);
	}

	return out;
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

	if (!($("#instructionsNumber").val() > 0)) {
		return false;
	}
	if (getInstructions().length === 0) {
		return false
	}

	return sessionStorage.validity || false;
}

$(document).ready(function() {

	loadInstructions();

	$( "#instructions, #selectedInstructions" ).sortable({
		connectWith: ".instructionsList",
		receive: function(event, ui) {
			updateCSS(ui.item);
			checkLevel();
		}
	}).disableSelection();

	$('#legalizeEverything').click(function() {
		authorizeInstructions();
		checkLevel();
	});

	$('#forbidEverything').click(function() {
		forbidInstructions();
		checkLevel();
	});
});