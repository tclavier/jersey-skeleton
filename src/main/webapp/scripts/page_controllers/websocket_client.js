var wsUri = "ws://localhost:8080/irc/";
var websocket;

function init() {
	testWebSocket();
}


function testWebSocket() {
	websocket = new WebSocket(wsUri + Cookies["id"]);
	websocket.onopen = function(evt) {
		console.log("connected");
	};
	websocket.onclose = function(evt) {
		console.log("closed");
	};
	websocket.onmessage = function(evt) {
		console.log(evt);
		var data = JSON.parse(evt.data);
		console.log(data);
		if(!data.fromServer)
			$("#screen").append(data.from + " : " + data.content + "<br />");
	};
	websocket.onerror = function(evt) {
		console.log("Erreur");
		console.log(evt);
	};
}

$(document).ready(function() {
	init();
	
	$("#sendMessage").click(function() {
		websocket.send($("#messageToSend").val());
		$("#screen").append("moi : " + $("#messageToSend").val() + "<br />");
	});
});