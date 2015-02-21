define(["jquery"],  function($) {
    return function Game(width, height, tiles) {
		this.width = width;
		this.height = height;
		this.tiles = tiles

		var Grid = require("grid");
		var Player = require("player");
		
		this.grid = new Grid(this, this.tiles, this.width, this.height);
		this.player = new Player(this, 0, 0);

		this.render = function render() {
			var c = document.getElementById("grid");
			var ctx = c.getContext("2d");
			
			this.grid.render(ctx);
			//this.player.render(ctx);
		}

		this.update = function update(delta) {
			this.grid.update(delta);
			this.player.update(delta);
			this.render();
		}
	}


});
