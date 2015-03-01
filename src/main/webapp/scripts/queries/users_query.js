
var isConnected;

/*
 * When the user connect or disconnect
 */
function setConnected(connected) {
	isConnected = connected;

	if(isConnected) {
		$("#login_navbar").hide();
		$("#info_profil_navbar").show();
		
		onClickMenu("#home_bar");
	} else {
		$("#info_profil_navbar").hide();
		$("#login_navbar").show();
	}
}


function registerUser(name, password, email) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/users/register",
		dataType : "json",
		data : JSON.stringify({
			"id" : 0,
			"name" : name,
			"password" : password, 
			"email" : email
		}),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.success) {
				loginUser(name, password);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}


function loginUser(name, password) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/users/login",
		dataType : "json",
		data : JSON.stringify({
			"id" : 0,
			"name" : name,
			"password" : password, 
			"email" : ""
		}),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.success) {
				// data.message contain uniq id for session
				document.cookie = data.message;
				setConnected(true);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}

function logoutUser() {
	$.getJSON("v1/users/logout/" + document.cookie, function(data) {
		setConnected(false);
	});
}
