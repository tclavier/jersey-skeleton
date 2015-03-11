/**
 * Liste toutes les pages à protéger (nécessitant d'être loggé)
 */
function isLoginRequiredPage() {
	var page = location.pathname;

	return page == "/options.html";/* || 
				page == "/editor.html";*/
}




$(document).ready(function() {

	/****************************************
	 **** CHARGEMENT AVANT TOUT LE RESTE ****
	 ****************************************/

	/*
	 * When the user connect or disconnect
	 */
	function setConnected(connected) {
		sessionStorage.setItem("isConnected", connected);

		if(sessionStorage.getItem("isConnected") == "true") {
			$("#login_navbar").hide();
			$("#info_profil_navbar").show();
		} else {
			$("#info_profil_navbar").hide();
			$("#login_navbar").show();

			if(isLoginRequiredPage()) {
				location.replace("/");
			}
		}
	}

	function checkConnection() {
		if(document.cookie == "")
			setConnected(false);
		else
			$.getJSON("v1/users/isLogged/" + document.cookie, function(data) {
				setConnected(data.success);
			});
	}

	checkConnection();


	/*****************************
	 **** REQUETES AJAX USERS ****
	 *****************************/

	function loginUser(name, password) {
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : "v1/users/login",
			dataType : "json",
			data : JSON.stringify({
				"id" : 0,
				"name" : name,
				"password" : password
			}),
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if(data.success) {
					// data.message contain uniq id for session
					document.cookie = data.message;
					setConnected(true);
				} else {
					$('#login_send').popover({trigger : 'manual', title: 'Erreur', content : data.message, placement : 'bottom', animation : 'true'});
					$('#login_send').popover('show');
					$('#login_send').on('shown.bs.popover', function() {
						setTimeout(function() {
							$('#login_send').popover('hide');
						}, 5000);
					});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('postUser error: ' + textStatus);
			}
		});
	}

	/*
	 * Fonction permettant d'afficher les informations de l'utilisateur
	 */
	function showProfile(userCookie) {
		$.ajax({
			type : 'GET',
			dataType : 'application/json', 
			url : 'v1/users/'+userCookie,

			succes : function(json, statut) {
				console.log("DATA : "+json);
				var page = $("#profil_pane");
				page.append(json+"");
			}
		})
	}

	function logoutUser() {
		if(document.cookie == "")
			setConnected(false);
		else
			$.getJSON("v1/users/logout/" + document.cookie, function(data) {
				console.log(data);
				setConnected(false);
			});
	}




	function linkFacebookAccount(facebookId) {
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : "v1/users/linkFb",
			dataType : "json",
			data : JSON.stringify({
				"facebookId" : facebookId,
				"cookie" : document.cookie
			}),
			success : function(data, textStatus, jqXHR) {
				console.log(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('postUser error: ' + textStatus);
			}
		});
	}




	/****************************
	 **** CONTROLE DES PAGES ****
	 ****************************/





	// Login l'utilisateur
	$("#login_send").click(function() {
		var name = $("#name_login").val();
		var passwd = $("#password_login").val();
		loginUser(name, passwd);
		$("#name_login").val("");
		$("#password_login").val("");
	});

	//Logout l'utilisateur
	$("#logout_icon").click(function() {
		logoutUser();
	});




});