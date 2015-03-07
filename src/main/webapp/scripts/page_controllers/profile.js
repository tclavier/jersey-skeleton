
$(document).ready(function() {



	function showProfileInfo(data) {
		$("#info_player").html("");
		$("#info_player").append("<h1>" + data.user.name + "</h1><br />");
		$("#info_player").append("Email :" + data.user.email + "<br />");


		$("#creations_list").html("");
		for(var i = 0 ; i < data.levelsInfo.length ; i++) {
			var levelInfo = $('<div class="level_info"></div>');
			levelInfo.append('<a href="game.html#' + data.levelsInfo[i].id + '">' + data.levelsInfo[i].name + '</a>');
			$("#creations_list").append(levelInfo);
		}
	}

	function showFriendList(data) {
		$("#friend_list").html("");
		for(var i = 0 ; i < data.length ; i++) {
			var friendInfo = $('<div class="friend_info"></div>');
			friendInfo.append(data[i].name + "<br />");
			friendInfo.append('<img class="fb_picture" src="' + data[i].picture.data.url + '" />');
			$("#friend_list").append(friendInfo);
		}
	}



	function loadFriendList() {
		FB.getLoginStatus(function(response) {
			if (response.status === 'connected') {
				FB.api(
						"/me/friends?fields=name,id,picture",
						function (response) {
							if (response && !response.error) {
								console.log(response);
								showFriendList(response.data);
							}
						}
				);
			}
			else {
				$("#friend_list").html('<a href="options.html">Vous devez vous connecter à facebook pour ajouter vos amis !</a>');
			}
		});
	}




	/**
	 * INIT FB SDK
	 */
	window.fbAsyncInit = function() {
		FB.init({
			appId      : '1550153965266129',
			xfbml      : true,
			version    : 'v2.1'
		});
		if(location.hash == "")
			loadFriendList();
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