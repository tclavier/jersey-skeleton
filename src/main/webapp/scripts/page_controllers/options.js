$(document).ready(function() {


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

});