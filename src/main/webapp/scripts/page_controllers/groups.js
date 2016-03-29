/**
 * Start of a javascript control of the groups'page
 */


function loadGroups() {
	$.getJSON("v1/users/sortByName", function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			addRow(data[i]);
		}
	})
}

function addRow(data, rank) {
	var row = $('<tr><td><a href="profile.html?id='+data.idUser+'">'+data.name+'</a></td><td>'+data.email+'</td><tr>');
	$("#groups-users-table").append(row);
}

function sortByName(data1,data2){
     
}


$(document).ready(function() {
	loadGroups();
});


/*
$(document).ready(function() {
    var url="v1/users";
    var json = new Array();
    //getter
    $.getJSON(url,function(data){
        json = data;
    }).done(function(){
        sortJsonField();
        $('#groups-pseudo-id').click(function(){
            sortJsonField("name");
        })
        $('#groups-email-id').click(function(){
            sortJsonField("email");
        })
    });

        function sortJsonField(field){
            function sortJson(a,b){
                if(field == "name"){ return a.name > b.name? 1 : -1; }else
                if(field == "email"){ return a.email > b.email? 1 : -1; }else
                return a.name > b.name? 1 : -1;
            }
            json.users.sort(sortJson);
            showJSON();
        };
        //shower
        function showJSON(){
            //$('#groups-table').empty();
            $.each(json.users,function(i,row){ 
                $("#groups-users-table").append(
                    '<tr><td>' + row.id + '</td><td>' + row.name + '<td></tr>'
                );
            });
        };
        showJSON(); 
});*/