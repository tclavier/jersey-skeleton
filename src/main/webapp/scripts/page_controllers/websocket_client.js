/**
 * Handle the Ludichat in the websocket_client.html with multiple users
 *  handling a connection of user, refreshing and posting messages
 */

var wsUri = "ws://localhost:8080/irc/";
var websocket;

function init() {
    testWebSocket();
}


function testWebSocket() {
    websocket = new WebSocket(wsUri + Cookies["id"]);
    websocket.onopen = function (evt) {
        console.log("connected");
    };
    websocket.onclose = function (evt) {
        console.log("closed");
    };
    websocket.onmessage = function (evt) {
        var data = JSON.parse(evt.data);
        console.log(data);
        if (data.type == 2) {
            if (!data.fromServer)
                addMessage(data.from, data.content);
        } else if (data.type == 1) {
            refreshOnlineUsers(data);
        }
    };
    websocket.onerror = function (evt) {
        console.log("Erreur");
        console.log(evt);
    };
}


function refreshOnlineUsers(data) {
    $('#connected-users').html("");
    for (var i = 0; i < data.users.length; i++) {
        addOnlineUser(data.users[i].name, data.users[i].id);
    }
}

function addOnlineUser(name, id) {
    var element = $('<li class="media"><div class="media-body"><div class="media"><div class="media-body"><h5><a href="profile.html?id=' + id + '">' + name + '</a></h5><small class="text-muted">Active From 3 hours</small></div></div></div></li>');
    $('#connected-users').append(element);
}

function addMessage(name, content, id) {
    var d = new Date();
    var hrefLink = (id == undefined ? "profile.html" : "profile.html?id=" + id);
    var date = d.getDate() + "/" + ("0" + d.getMonth()).slice(-2) + " à " + d.getHours() + ":" + ("0" + d.getMinutes()).slice(-2);
    var element = $('<li class="media"><div class="media-body"><div class="media"><div class="media-body">' + content + '<br /> <small class="text-muted"><a href="' + hrefLink + '">' + name + '</a> | ' + date + '</small><hr /></div></div></div></li>');
    $('#screen').append(element);
}

function handleKeyPress(e) {
    var key = e.keyCode || e.which;
    if (key === 13) {
        doSendMessageClick();
    }
}

function doSendMessageClick() {
    var message = $("#messageToSend").val();
    if (message != "") {
        $("#messageToSend").val("");
        websocket.send(message);
        addMessage("Moi", message);
    }
}

$(document).ready(function () {
    init();

    $("#sendMessage").click(function () {
        doSendMessageClick();
    });

    $('#messageToSend').keyup(function (e) {
        handleKeyPress(e);
    });
});