define(["jquery"],  function($) {
    return function Game(width, height, tiles) {
		this.width = width;
		this.height = height;
		this.tiles = tiles

		var Grid = require("grid");
        var Interpreter = require("interpreter");
        var Player = require("player");
		var GraphicalPlayer = require("graphical_player");
	

        this.createPlayer = function createPlayer(tileX, tileY) {
            this.gplayer = new GraphicalPlayer(this, tileX * this.grid.tile_size, tileY * this.grid.tile_size);
            this.player = new Player(this, tileX * this.grid.tile_size, tileY * this.grid.tile_size);
        }


		this.render = function render() {
			var c = document.getElementById("grid");
			var ctx = c.getContext("2d");
			
			this.grid.render(ctx);
			if (this.gplayer) this.gplayer.render(ctx);
		}

		this.update = function update(delta) {
			this.grid.update(delta);
			if (this.gplayer) this.gplayer.update(delta);
			this.render();
		}


        this.grid = new Grid(this, this.tiles, this.width, this.height);
        this.grid.generate();
        this.interpreter = new Interpreter(this);
	}


});
