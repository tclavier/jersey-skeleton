$(document).ready(function() {


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
				} else {
					$('#button_register').popover({trigger : 'manual', title: 'Erreur', content : data.message, placement : 'bottom', animation : 'true'});
					$('#button_register').popover('show');
					$('#button_register').on('shown.bs.popover', function() {
						setTimeout(function() {
							$('#button_register').popover('hide');
						}, 5000);
					});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('postUser error: ' + textStatus);
			}
		});
	}



	// Enregistrer un utilisateur
	$("#button_register").click(function() {
		var name = $("#name_register").val();
		var passwd = $("#password_register").val();
		var email = $("#email_register").val();

		registerUser(name, passwd, email);

		$("#name_register").val("");
		$("#password_register").val("");
		$("#email_register").val("");
	});

});