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
	
	
	
	// Change le pseudo de l'utilisateur
	$("#updateNameButton").click(updateName);
	
	// Change le mail de l'utilisateur
	$("#updateMailButton").click(updateEmail);
	
	// Change le mot de passe de l'utilisateur
	$("#updatePasswordButton").click(updatePassword);
	
	
	// Cache les messages d'erreur si on en a pas
	$("#errorPseudo").hide();
	$("#errorEmail").hide();
	$("#errorPassword").hide();
	
	// Cache le message d'erreur quand on quitte les modals
	$("#pseudo_modal").on('hidden.bs.modal', function() {
		$("#errorPseudo").hide();
	});
	
	$("#email_modal").on('hidden.bs.modal', function() {
		$("#errorEmail").hide();
	});
	
	$("#password_modal").on('hidden.bs.modal', function() {
		$("#errorPassword").hide();
	});
	
	// Focus sur le premier champ de chaque formulaire
	$("#pseudo_modal").on('shown.bs.modal', function() {
		$("#newPseudo").focus();
	});
	
	$("#email_modal").on('shown.bs.modal', function() {
		$("#newMail").focus();
	});
	
	$("#password_modal").on('shown.bs.modal', function() {
		$("#oldPassword").focus();
	});

	
	
	loadProfil();

    // Change l'avatar de l'utilisateur
    document.getElementById("updateAvatar").addEventListener('change', updateAvatar, false);


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

function updateAvatar(evt) {
    console.log(evt);

    var files = evt.target.files; // FileList object

    // files is a FileList of File objects. List some properties.
    var output = [];
    for (var i = 0; i < files.length; i++) {
        var reader = new FileReader();

        // Pour afficher le resultat en preview
        reader.onload = (function(theFile) {
            return function(e) {
                $("#avatar").attr("src", e.target.result);
            };
        })(files[i]);

        // Pour envoyer le resultat au serveur
        var formData = new FormData();
        formData.append('file', files[i]);
        $.ajax({
            url : '/v1/avatars/add',
            type : 'POST',
            data : formData,
            cache : false,
            contentType : false,
            processData : false,
            success : function(data, textStatus, jqXHR) {
                var feedback = JSON.parse(jqXHR.responseText);
                if (!feedback.success) {
                    alert("Impossible d'uploader votre avatar !\n" +  feedback.message);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert(textStatus);
            }
        });


        reader.readAsDataURL(files[i]);
    }
}

function updateName() {
	
	console.log("update Name");

	var name = $("#newPseudo").val();
	var name2 = $("#newPseudoConfirmation").val();
	
	
	if (name == name2) {
		$.ajax({
			type : 'PUT',
			contentType : 'application/json',
			url : "v1/users/updateName/" + Cookies["id"] + "/" + name,
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if (data.success) {
					$("#pseudo_modal").modal('hide');
					loadProfil();
				} else {
					$("#errorPseudo").empty();
					$("#errorPseudo").append(data.message);
					$("#errorPseudo").show();

				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$("#errorPseudo").empty();
				$("#errorPseudo").append("Vous devez remplir tous les champs !");
				$("#errorPseudo").show();
			}
		});
	} else {
		$("#errorPseudo").empty();
		$("#errorPseudo").append("Les deux pseudos sont différents !");
		$("#errorPseudo").show();
	}
	
	
}

function updateEmail() {
	console.log("update Email");

	var mail = $("#newMail").val();
	var mail2 = $("#newMailConfirmation").val();
	
	
	if (mail == mail2) {
		$.ajax({
			type : 'PUT',
			contentType : 'application/json',
			url : "v1/users/updateEmail/" + Cookies["id"] + "/" + mail,
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if (data.success) {
					$("#email_modal").modal('hide');
					loadProfil();
				} else {
					console.log("else !!");
					$("#errorEmail").empty();
					$("#errorEmail").append(data.message);
					$("#errorEmail").show();

				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$("#errorEmail").empty();
				$("#errorEmail").append("Vous devez remplir tous les champs !");
				$("#errorEmail").show();
			}
		});
	} else {
		$("#errorEmail").empty();
		$("#errorEmail").append("Les deux emails sont différents !");
		$("#errorEmail").show();
	}
	
}

function updatePassword() {
	console.log("update password");
	
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	var newPassword2 = $("#newPasswordConfirmation").val();
	
	if (newPassword == newPassword2) {
		$.ajax({
			type : 'PUT',
			contentType : 'application/json',
			url : "v1/users/updatePassword/" + Cookies["id"] + "/" + oldPassword +"/" + newPassword,
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if (data.success) {
					$("#password_modal").modal('hide');
					loadProfil();
				} else {
					console.log("else !!");
					$("#errorPassword").empty();
					$("#errorPassword").append(data.message);
					$("#errorPassword").show();
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$("#errorPassword").empty();
				$("#errorPassword").append("Vous devez remplir tous les champs !");
				$("#errorPassword").show();
			}
		});
	} else {
		$("#errorPassword").empty();
		$("#errorPassword").append("Les deux mots de passe sont différents !");
		$("#errorPassword").show();
	}
	
}

function showProfileInfo(data) {
	
	$("#info_player").text("");

	$("#info_player").append("<b> Pseudo :</b> " + data.user.name+"  ");
	$("#info_player").append("<input class='btn btn-default btn-xs' type='button' value='Modifier' id='pseudo_button'> <br> <br>");
	$("#info_player").append("<b> Email :</b> " + data.user.email+"  ");
	$("#info_player").append("<input class='btn btn-default btn-xs' type='button' value='Modifier' id='email_button'> <br> <br>");
	$("#info_player").append("<b> Mot de passe :</b> ******  ");
	$("#info_player").append("<input class='btn btn-default btn-xs' type='button' id='password_button' value='Modifier'> <br>");
	
	
	$("#password_button").click(function () { $("#password_modal").modal("show") });
	$("#email_button").click(function() { $("#email_modal").modal("show")});
	$("#pseudo_button").click(function() { $("#pseudo_modal").modal("show")});

}

function loadProfil() {
	if(location.hash == "") {
		$.getJSON("v1/profile/me/" + Cookies["id"], function(data) {
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
	
}
