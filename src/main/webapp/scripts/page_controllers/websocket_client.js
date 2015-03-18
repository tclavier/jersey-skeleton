var wsUri = "ws://localhost:8080/echo";
var websocket;

function init() {
	testWebSocket();
}


function testWebSocket() {
	websocket = new WebSocket(wsUri + "/" + $("#clientName").val());
	websocket.onopen = function(evt) {
		console.log("connected");
	};
	websocket.onclose = function(evt) {
		console.log("closed");
	};
	websocket.onmessage = function(evt) {
		console.log(evt)
		var data = JSON.parse(evt.data);
		$("#screen").append(data.name + " : " + data.content + "<br />");
	};
	websocket.onerror = function(evt) {
		console.log("Erreur");
		console.log(evt);
	};
}

$(document).ready(function() {
	$("#connect").click(function() {
		init();
	});
	
	$("#sendMessage").click(function() {
		websocket.send(JSON.stringify({
			"name" : $("#clientName").val(),
			"content" : $("#messageToSend").val()
		}));
		$("#screen").append($("#messageToSend").val() + "<br />");
	});
});