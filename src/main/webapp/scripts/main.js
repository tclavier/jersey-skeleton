requirejs.config({
    baseUrl: "",
    paths: {
        "jquery": "libs/jquery",
		"blockly": "libs/blockly_compressed",
		"blockly_msg": "libs/blockly_msg",
		"blocks": "libs/blocks_compressed",
        "libs/boostrap": "libs/bootstrap",
        "grid": "scripts/grid",
		"player": "scripts/player",
		"graphical_player": "scripts/graphical_player",
		"game": "scripts/game",
        "interpreter": "scripts/interpreter"
    },

    shim: {
        "libs/bootstrap": {
            deps: ["jquery"]
        },
		
		"blocks": {
			deps: ["blockly"]
		},
		
		"blockly_msg": {
			deps: ["blocks", "blockly"]
		}
    }
});

var game;
var speed = 10;

require(["jquery", "libs/bootstrap", "game", "grid", "player", "interpreter", "graphical_player"], function ($) {
	var Game = require("game");

	game = new Game(100, 100, [[2, 0],[1, 0]]);

	var time;
	function mainLoop() {
		requestAnimationFrame(mainLoop);
		var now = new Date().getTime(),
		dt = (now - (time || now))/1000;

		time = now;
		game.update(dt);
	}
	
	mainLoop();
    console.log("main loaded");
});

// Fonction pour executer le code de l'algo
function execute(code) {
    // On regenere la grille pour la remettre a son etat initial
    game.grid.generate()
    // On prepare l'interpreteur
    game.interpreter.setup();
    // On execute les actions sur le joueur invisible permettant juste de remplir l'interpreteur
    var player = game.player;
    // On execute le code
    eval(code);
    // Si tous c'est bien passé, l'interpreteur devrait etre rempli de commande qui vont maintenant pouvoir etre affiché graphiquement
}
