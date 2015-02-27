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

	game = new Game(100, 100, [[0, 0],[1, 0]]);

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

    alert(code);
}
