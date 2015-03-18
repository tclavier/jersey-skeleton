
var tab = [];

function searchUsers() {	
	$.getJSON("/v1/users/search?term=" + $("#search-friend").val() , function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			tab[i] = data[i].name;
		}
		console.log(tab);
	});
}


$(document).ready(function() {





	$( "#search-friend" ).autocomplete({
		source: function (request, response) {
			$.getJSON("/v1/users/search?term=" + $("#search-friend").val(), function (data) {
				response($.map(data, function (value, key) {	            	
					return {
						label: value.name
					};
				}));
			});
		},
		delay: 0
	});




	function showProfileInfo(data) {
		//$("#info_player").html("");
		$("#info_player").append("<b> Pseudo :</b> " + data.user.name+"<br>");
		$("#info_player").append("<b> Email :</b> " + data.user.email+"<br> <br>");
		$("#info_player").append("<a href='options.html'> Modifier mon profil </a>");


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
		$.getJSON("v1/profile/me/" + Cookies["id"], function(data) {
			showProfileInfo(data);
		})
		.error(function() {
			// Utilisateur non loggé
			location.replace("/index.html");
		});
	} else {
		$.getJSON("v1/profile/" + location.hash.substring(1), function(data) {
			showProfileInfo(data);
		});
	}




});