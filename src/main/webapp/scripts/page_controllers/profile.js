$(document).ready(function() {

	
	function handleKeyPress(e) {
		var key = e.keyCode || e.which;
		if (key === 13) {
			doSearchClick();
		}
	}
	
	
	function doSearchClick() {
		$.getJSON("/v1/users/getId/" + $("#search-friend").val(), function(data) {
			location.replace("profile.html?id=" + data.id);
		});
	}
	
	$('#go-to-profile').keyup(function(e) {
		handleKeyPress(e);
	});
	
	$("#go-to-profile").click(function() {
		doSearchClick();
	});

	$("#search-friend").autocomplete({
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


	function showProfileInfo(data, currentUserProfile) {
		//$("#info_player").html("");
		$("#info_player").append("<b> Pseudo :</b> " + data.user.name+"<br>");
		if(currentUserProfile) $("#info_player").append("<b> Email :</b> " + data.user.email+"<br> <br>");
		if(currentUserProfile) $("#info_player").append("<a href='options.html'> Modifier mon profil </a>");
		else $("#info_player").append('<button id="add-friend" type="button" class="btn btn-primary">Ajouter en ami</button>');
		
		$("#add-friend").click(function() {
			$.getJSON("/v1/friends/addFriend/" + urlParam("id") + "/" + Cookies["id"], function(data) {
				console.log(data);
			});
		});
		

		$("#creations_list").html("");
		for(var i = 0 ; i < data.levelsInfo.length ; i++) {
			var levelInfo = $('<div class="level_info"></div>');
			levelInfo.append('<a href="game.html?level=' + data.levelsInfo[i].id + '">' + data.levelsInfo[i].name + '</a>');
			$("#creations_list").append(levelInfo);
		}
	}

	function showFriendList(data) {
		for(var i = 0 ; i < data.length ; i++) {
			var friendInfo = $('<div class="friend_info"></div>');
			friendInfo.append('<img class="profil_picture" src="images/profil.png" />');
			friendInfo.append(data[i].name);
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
	/*window.fbAsyncInit = function() {
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
	}(document, 'script', 'facebook-jssdk'));*/

	var idUser = urlParam("id");
	if(!idUser) {
		$.getJSON("v1/profile/me/" + Cookies["id"], function(data) {
			console.log(data);
			showProfileInfo(data, true);
		})
		.error(function() {
			// Utilisateur non loggé
			location.replace("/index.html");
		});
		
		$.getJSON("v1/friends/me/" + Cookies["id"], function(data) {
			console.log(data);
			showFriendList(data);
		});
		
		
	} else {
		$.getJSON("v1/profile/" + idUser, function(data) {
			console.log(data);
			showProfileInfo(data, false);
		});
		
		$.getJSON("v1/friends/" + idUser, function(data) {
			console.log(data);
			showFriendList(data);
		});
	}


});