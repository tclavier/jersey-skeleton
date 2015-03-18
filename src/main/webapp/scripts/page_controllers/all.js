/**
 * Liste toutes les pages à protéger (nécessitant d'être loggé)
 */
function isLoginRequiredPage() {
	var page = location.pathname;

	return page == "/options.html";/* || 
				page == "/editor.html";*/
}



/**
 * Cookies : 
 */

var Cookies = {
		init: function () {
			var allCookies = document.cookie.split('; ');
			for (var i=0;i<allCookies.length;i++) {
				var cookiePair = allCookies[i].split('=');
				this[cookiePair[0]] = cookiePair[1];
			}
		},
		create: function (name,value,days) {
			if (days) {
				var date = new Date();
				date.setTime(date.getTime()+(days*24*60*60*1000));
				var expires = "; expires="+date.toGMTString();
			}
			else var expires = "";
			document.cookie = name+"="+value+expires+"; path=/";
			this[name] = value;
		},
		erase: function (name) {
			this.create(name,'',-1);
			this[name] = undefined;
		}
};
Cookies.init();



$(document).ready(function() {
	/****************************************
	 **** GOOGLE ANALYTICS               ****
	 ****************************************/

	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	ga('create', 'UA-60738606-1', 'auto');
	ga('send', 'pageview');


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
				console.log("toto");
				location.replace("/");
			}
		}
	}

	function checkConnection() {
		if(!Cookies["id"])
			setConnected(false);
		else
			$.getJSON("v1/users/isLogged/" + Cookies["id"], function(data) {
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
					Cookies.create("id", data.message);
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
		if(!Cookies["id"])
			setConnected(false);
		else
			$.getJSON("v1/users/logout/" + Cookies["id"], function(data) {
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
				"cookie" : Cookies["id"]
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


	function doLoginClick() {
		var name = $("#name_login").val();
		var passwd = $("#password_login").val();
		loginUser(name, passwd);
		$("#name_login").val("");
		$("#password_login").val("");
	}

	function handleKeyPress(e) {
		var key = e.keyCode || e.which;
		if (key === 13) {
			doLoginClick();
		}
	}

	// Login l'utilisateur
	$("#login_send").click(function() {
		doLoginClick();
	});

	$('#name_login').keyup(function(e) {
		handleKeyPress(e);
	});

	$('#password_login').keyup(function(e) {
		handleKeyPress(e);
	});

	//Logout l'utilisateur
	$("#logout_icon").click(function() {
		logoutUser();
	});



});
