requirejs.config({
	baseUrl : "",
	paths : {
		"jquery" : "libs/jquery",
		"blockly" : "libs/blockly_compressed",
		"blockly_msg" : "libs/blockly_msg",
		"blocks" : "libs/blocks_compressed",
		"blockly_js" : "libs/javascript_compressed.js",
		"libs/boostrap" : "libs/bootstrap",
		"grid" : "scripts/grid",
		"player" : "scripts/player",
		"graphical_player" : "scripts/graphical_player",
		"game" : "scripts/game",
		"interpreter" : "scripts/interpreter",
		"events" : "scripts/events",
		"animation" : "scripts/animation",
		"block_creator" : "scripts/block_creator",
		"theme" : "scripts/theme",
		"menu_bar_controller" : "scripts/page_controllers/menu_bar_controller",
		"users_query" : "scripts/queries/users_query",
		"levels_query" : "scripts/queries/levels_query"
	},

	shim : {
		"libs/bootstrap" : {
			deps : [ "jquery" ]
		},

		"blocks" : {
			deps : [ "blockly" ]
		},

		"blockly_msg" : {
			deps : [ "blocks", "blockly" ]
		},

		"blockly_js" : {
			deps : [ "blocks", "blockly", "blockly_msg" ]
		},

		"menu_bar_controller" : {
			deps : [ "users_query", "levels_query" ]
		}
	}
});

var game;
var level_type = sessionStorage.type;

require(
		[ "jquery", "libs/bootstrap", "game", "grid", "player", "interpreter",
				"animation", "graphical_player", "events", "block_creator",
				"theme" ],
		function($) {
			var Game = require("game");
			var BlockCreator = require("block_creator");
			var Theme = require("theme");

			game = new Game();

			var time;
			function mainLoop() {
				requestAnimationFrame(mainLoop);
				var now = new Date().getTime(), dt = (now - (time || now)) / 1000;

				time = now;

				if (window.levelData && game.theme.isLoaded()) {
					// On ajoute les themes a la combobox
					for (var i = 0; i <= game.theme.nbLine; ++i) {
						var o = new Option("Theme " + (i + 1), "" + (i - 1));
						$(o).html("Theme " + (i + 1));
						$("#theme_selector").append(o);
					}

					// Si un theme a été sauvegardé, on le recharge
					if (sessionStorage.themeId)
						game.theme.themeId = parseInt(sessionStorage.themeId);

					$("#theme_selector").val(game.theme.themeId);

					// Si une vitesse a été sauvegardé, on l'utilise
					if (sessionStorage.speed) {
						$("#speed").val(sessionStorage.speed);
					}

					// On crée un tableau 2D avec les données
					var tiles = [];
					for (var i = 0; i < window.levelData.structuredContent.length; ++i) {
						tiles.push(window.levelData.structuredContent[i].item);
					}
					game.setTiles(tiles);

					
					//LEVEL TYPE
					
					//Blocs
					if (level_type == "blocs") {
						blockCreator = new BlockCreator(game);

						var toolbox = blockCreator
								.getToolbox(window.levelData.instructionsList);

						// On crée la zone pour blockly
						Blockly.inject(document.getElementById('blocklyDiv'), {
							trashcan : true,
							toolbox : toolbox,
							maxBlocks : window.levelData.maxInstructions
						});

						// On met un message dynamique pour afficher le nombre
						// de bloques restant
						Blockly
								.addChangeListener(function() {
									if (runned)
										execute("");
									if (Blockly.maxBlocks == Infinity)
										return;
									var remainingBlocks = Blockly.maxBlocks
											- Blockly.getMainWorkspace()
													.getAllBlocks().length;
									$("#max_instruction").text(remainingBlocks);
									$("#max_instruction_s").text(
											remainingBlocks > 1 ? "s" : "");
								});
					}
					else if (level_type == "cartes"){
						
						//Boucle jeu carte
						
						
						
					}
					
					
					window.levelData = null;
				}

				if (game)
					game.update(dt);
			}
			mainLoop();
		});

var runned = false;
// Fonction pour executer le code de l'algo
function execute(code) {
	// On cache le message
	$("#message_box").hide();
	// On regenere la grille pour la remettre a son etat initial
	game.grid.generate()
	// On prepare l'interpreteur
	game.interpreter.setup();
	// On réinitialise le theme pour eviter les bugs graphiques
	game.theme.reset();
	// On execute les actions sur le joueur invisible permettant juste de
	// remplir l'interpreteur
	var player = game.player;

	if (!runned) {
		// On modifie le design du bouton
		$("#execute").attr("class", "btn btn-danger");
		$("#execute")
				.html(
						"<span class=\"glyphicon glyphicon-remove\"></span> Réinitialiser");

		// On execute le code
		runned = true;

		eval("function code() { " + code + "\n}\ncode()");
		// Si tous c'est bien passé, l'interpreteur devrait etre rempli de
		// commande qui vont maintenant pouvoir etre affiché graphiquement

		runned = true;

	} else {
		// On modifie le design du bouton
		$("#execute").attr("class", "btn btn-success");
		$("#execute").html(
				"<span class=\"glyphicon glyphicon-play\"></span> Executer");
		runned = false;
	}
}

$(document).ready(function() {

	document.getElementById('execute').onclick = function() {
		if (Blockly != null && level_type == "blocs") {
			execute(Blockly.JavaScript.workspaceToCode());
		} 
		else if (level_type == "cartes"){
			alert('Cartes')
		}
		else {
			alert("Not loaded yet :(");
		}
	}

	// Permet de changer le theme dynamiquement
	$("#theme_selector").change(function() {
		game.theme.themeId = parseInt($(this).val());

		// On sauvegarde le theme choisi
		sessionStorage.themeId = game.theme.themeId;
	});

	// Lors des changements de la vitesse
	$("#speed").change(function() {
		// On sauvegarde la vitesse choisi
		sessionStorage.speed = $(this).val();
	});

});

// On intercepte les changements de dimension pour adapter le canvas
$(window).resize(
		function() {
			// Commenté, pause trop de probleme (flash, animation coupé...)
			// surtout parceque blockly provoque un evenement de resize
			// lorsqu'un bloc est déplacé
			// if (game) game.updateDimensions();
			// var minHeight = $("#grid-container").height() +
			// 20;//$("#grid-container").offset() + " " +
			// $("#grid-container").height();
			var minHeight = 500;
			if ($("#max_instruction").offset())
				minHeight = $("#max_instruction").offset().top
						- $("#grid-container").offset().top + 30;

			var height = $("canvas").parent().height();

			// console.log(minHeight + " " + height);
			$(".blocklySvg").attr("height", Math.max(minHeight, height));

		});
