$(document).ready(function() {


//	ouverture d'un niveau lors d'un clique sur le niveau dans la liste
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
	

});