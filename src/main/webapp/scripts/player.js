define(["jquery"],  function(require) {
    return function Player(game, x, y) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.events = [];
		var locked = false;

		this.moveTo = function moveTo(x, y) {
			if (locked) return;
			this.x = x;
			this.y = y;
		}

		this.moveToTile = function moveToTile(x, y) {
			if (locked) return;
			if (x >= 0 && y >= 0 && x < this.game.grid.tiles[0].length && y < this.game.grid.tiles.length && this.game.grid.tiles[y][x] != 1) {
				this.moveTo(x * this.game.grid.tile_size, y * this.game.grid.tile_size);
				return true;
			}
			return false;
		}

		this.tileX = function tileX() {
			return Math.round(this.x/this.game.grid.tile_size);
		}

		this.tileY = function tileY() {
			return Math.round(this.y/this.game.grid.tile_size);
		}
	}


});
