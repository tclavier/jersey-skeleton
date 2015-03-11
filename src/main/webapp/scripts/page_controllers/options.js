$(document).ready(function() {


	function showProfileInfo(data) {
		$("#info_player").append("<b> Pseudo :</b> " + data.user.name+"  ");
		$("#info_player").append("<input class='btn btn-default btn-xs' type='button' value='Modifier'> <br> <br>");
		$("#info_player").append("<b> Email :</b> " + data.user.email+"  ");
		$("#info_player").append("<input class='btn btn-default btn-xs' type='button' value='Modifier'> <br> <br>");
		$("#info_player").append("<b> Mot de passe :</b> ******  ");
		$("#info_player").append("<input class='btn btn-default btn-xs' type='button' value='Modifier'> <br>");

	}


	function enableFacebook(enable) {
		FB.getLoginStatus(function(response) {
			if (response.status === 'connected') {
				$("#login_fb").html("Connecté à facebook !")
			}
			else {
				FB.login(function(response) {
					console.log(response);
					if(response.status === "connected")
						$("#login_fb").html("Connecté à facebook !")


				}, {scope: 'public_profile,user_friends'});
			}
		});
	}

	//Activation de facebook
	$("#login_fb").click(function() {
		enableFacebook(true);
	});


	/**
	 * INIT FB SDK
	 */
	window.fbAsyncInit = function() {
		FB.init({
			appId      : '1550153965266129',
			xfbml      : true,
			version    : 'v2.1'
		});
	};

	(function(d, s, id){
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) {return;}
		js = d.createElement(s); js.id = id;
		js.src = "http://connect.facebook.net/fr_FR/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));


	if(location.hash == "") {
		$.getJSON("v1/profile/me/" + document.cookie, function(data) {
			console.log(data);
			showProfileInfo(data);
		})
		.error(function() {
			// Utilisateur non loggé
			location.replace("/");
		});
	} else {
		$.getJSON("v1/profile/" + location.hash.substring(1), function(data) {
			console.log(data);
			showProfileInfo(data);
		});
	}

});