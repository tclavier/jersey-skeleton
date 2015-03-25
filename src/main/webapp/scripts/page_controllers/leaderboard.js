function loadLeaderboard() {
	$.getJSON("v1/levelLists", function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			addRow(data[i]);
		}
	});
}

function addRow(data) {
	var row = $("<tr><td></td><tr>");
	
}

$(document).ready(function() {
	loadLeaderboard();
});