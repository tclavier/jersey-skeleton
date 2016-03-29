/**
 * Multiples function with the user
 *  apllication /v1/user/
 * Affiche user, get users and handle the list of users
 */

function getUser(name) {
    $.getJSON("v1/user/" + name, function (data) {
        afficheUser(data)
    });
}

function afficheUser(data) {
    console.log(data);
    $("#reponse").html(data.id + " : " + data.name);
}

function postUser(name) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "v1/user/",
        dataType: "json",
        data: JSON.stringify({
            "name": name,
            "id": 0
        }),
        success: function (data, textStatus, jqXHR) {
            afficheUser(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('postUser error: ' + textStatus);
        }
    });
}

function listUsers() {
    $.getJSON("v1/user/", function (data) {
        afficheListUsers(data)
    });
}

function afficheListUsers(data) {
    var html = '<ul>';
    var index = 0;
    for (index = 0; index < data.length; ++index) {
        html = html + "<li>" + data[index].name + "</li>";
    }
    html = html + "</ul>";
    $("#reponse").html(html);
}