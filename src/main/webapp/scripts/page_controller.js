$(document).ready(function() {

	// On affiche la page d'accueil par défaut
	var activeMenu = $("#home_bar");
	var activeElement = $("#home_pane");
	activeMenu.addClass("active")
	activeElement.show();

	

	// affichage de la page lors d'un clique
	$(".navbar-brand").click(function() {
		// récupérer l'élément panel attaché à l'élément du menu
		
		var newElement = $("#" + $(this).attr("for"));
		activeElement.hide();
		newElement.show();

		activeMenu.removeClass("active");
		$(this).addClass("active");

		activeElement = newElement;
		activeMenu = $(this);
	});
	
	// affichage de la page lors d'un clique
	$(".menu_bar_element").click(function() {
		// récupérer l'élément panel attaché à l'élément du menu
		var newElement = $("#" + $(this).attr("for"));
		activeElement.hide();
		newElement.show();

		activeMenu.removeClass("active");
		$(this).addClass("active");

		activeElement = newElement;
		activeMenu = $(this);
	});
	
	$("#reg_button").click(function() {
		var newElement = $("#" + $(this).attr("for"));
		activeElement.hide();
		newElement.show();

		activeMenu.removeClass("active");
		//$(this).addClass("active");

		activeElement = newElement;
		activeMenu = $(this);
	
	});


	// ouverture d'un niveau lors d'un clique sur le niveau dans la liste
	$(".level_preview").click(function() {
		// Chargement du niveau en AJAX
		var idLevel = $(this).attr("id");
		loadLevel(idLevel);		
				
		// Affichage du panel du niveau
		newElement = $("#game_pane");
		activeElement.hide();
		newElement.show();
		activeElement = newElement;
	});
	
	
	// Enregistrer un utilisateur
	$("#button_register").click(function() {
		var name = $("#name_register").val();
		var passwd = $("#password_register").val();
		var email = $("#email_register").val();
		
		registerUser(name, passwd, email);
	});
	
	
	// Log l'utilisateur
	$("#button_login").click(function() {
		var name = $("#name_login").val();
		var passwd = $("#password_login").val();
		loginUser(name, passwd);
	});
	

});