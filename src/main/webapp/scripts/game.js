define(["jquery"],  function($) {
    return function Game(tiles) {
		var canvas = document.getElementById("grid");
		this.width = Math.min(canvas.height, canvas.width);
		this.height = this.width;
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
			var ctx = canvas.getContext("2d");
			ctx.save();
			ctx.translate((canvas.width - this.grid.width)/2, (canvas.height - this.grid.height)/2);
			this.grid.render(ctx);
			if (this.gplayer) this.gplayer.render(ctx);
			ctx.restore();
		}

		this.update = function update(delta) {
            // Si le joueur n'est pas dans une animation, on execute la commande suivante
            if (!this.gplayer.isDoingSomething() && this.interpreter.hasSteps()) {
                // On précise que c'est le joueur graphique
                var player = this.gplayer;
                eval(this.interpreter.nextStep());
            }

			this.grid.update(delta);
			if (this.gplayer) this.gplayer.update(delta);
			this.render();
		}


        this.grid = new Grid(this, this.tiles, this.width, this.height);
        this.grid.generate();
        this.interpreter = new Interpreter(this);
	}


});
