requirejs.config({
    baseUrl: "",
    paths: {
        "jquery": "libs/jquery",
        "libs/boostrap": "libs/bootstrap",
        "grid": "scripts/grid",
		"player": "scripts/player",
		"graphical_player": "scripts/graphical_player",
		"game": "scripts/game"
    },

    shim: {
        "libs/bootstrap": {
            deps: ["jquery"]
        }
    }
});

var game;

require(["jquery", "libs/bootstrap", "game", "grid", "player", "graphical_player"], function ($) {
    var Game = require("game");
	
	game = new Game(100, 100, [[0, 0],[1, 0]]);
	
	game.render();


    console.log("main loaded");
});
