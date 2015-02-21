requirejs.config({
    baseUrl: "",
    paths: {
        "jquery": "libs/jquery",
        "libs/boostrap": "libs/bootstrap",
        "grid": "scripts/grid",
		"player": "scripts/player",
		"game": "scripts/game"
    },

    shim: {
        "libs/bootstrap": {
            deps: ["jquery"]
        }
    }
});

require(["jquery", "libs/bootstrap", "game", "grid", "player"], function ($) {
    var Game = require("game");
	
	var game = new Game(100, 100, [[0, 0],[1, 0]]);
	
	game.render();


    console.log("main loaded");
});
