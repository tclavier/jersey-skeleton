$(document).ready(function() {
	
	/****************************************
	 **** CHARGEMENT AVANT TOUT LE RESTE ****
	 ****************************************/
	
	var isConnected;

	/*
	 * When the user connect or disconnect
	 */
	function setConnected(connected) {
		isConnected = connected;
		sessionStorage.setItem("isConnected", connected);
		
		if(isConnected) {
			$("#login_navbar").hide();
			$("#info_profil_navbar").show();

			//window.location.replace("/");
		} else {
			$("#info_profil_navbar").hide();
			$("#login_navbar").show();
			
			//window.location.replace("/");
		}
	}

	function checkConnection() {

		checkNormalConnection();
	}
	
	
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
				"password" : password, 
				"email" : ""
			}),
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if(data.success) {
					// data.message contain uniq id for session
					document.cookie = data.message;
					setConnected(true);
				} else {
					$('#button_login').popover({trigger : 'manual', title: "Erreur", content : data.message, placement : 'bottom'});
					$('#button_login').popover('show');

					$('#button_login').on('shown.bs.popover', function() {
						setTimeout(function() {
							$('#button_login').popover('hide');
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

	function checkNormalConnection() {
		if(document.cookie == "")
			setConnected(false);
		else
			$.getJSON("v1/users/isLogged/" + document.cookie, function(data) {
				setConnected(data.success);
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


	/**********************
	 **** API FACEBOOK ****
	 **********************/

	/**
	 * INIT FB SDK
	 */
	window.fbAsyncInit = function() {
		FB.init({
			appId      : '1550153965266129',
			xfbml      : true,
			version    : 'v2.1'
		});

		checkConnection();
	};

	(function(d, s, id){
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) {return;}
		js = d.createElement(s); js.id = id;
		js.src = "http://connect.facebook.net/fr_FR/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

	function checkLoginState() {
		FB.getLoginStatus(function(response) {
			statusChangeCallback(response);
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