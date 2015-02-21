

function registerUser(name, password, email) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/users/",
		dataType : "json",
		data : JSON.stringify({
			"id" : 0,
			"name" : name,
			"password" : password, 
			"email" : email
		}),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}