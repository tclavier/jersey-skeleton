define(["jquery"],  function(require) {
    return function GraphicalPlayer(game, x, y) {
		this.x = x;
		this.y = y;
		this.game = game;

		var goToX = x;
		var goToY = y;
		var moving = false;

		this.render = function render(context) {
			context.beginPath();
			var margin = 3;
			context.moveTo(this.x + margin, this.y + margin);
			context.lineTo(this.x + this.game.grid.tile_size - margin, this.y + margin);
			context.lineTo(this.x + this.game.grid.tile_size/2 - margin, this.y + this.game.grid.tile_size - margin);
			context.fillStyle = "#FF0000";
			context.fill();
		}

		this.update = function update(delta) {
			if (Math.abs(goToX - this.x) > 2) {
				var xsign = 1;
				if (goToX - this.x < 0) xsign = -1;

				this.x += 10 * delta * speed * xsign;
			} else {
				this.x = goToX;
				if (Math.abs(goToY - this.y) > 2) {
					var ysign = 1;
					if (goToY - this.y < 0) ysign = -1;

					this.y += 10 * delta * speed * ysign;
				} else {
					this.y = goToY;
					if (moving && this.game.grid.tiles[this.tileY()][this.tileX()] == 3) {
						alert("Bravo ! Vous avez réussi le niveau !\n\nVous allez être redirigé vers le niveau suivant.");
					}
					moving = false;
				}
			}
		}

		this.moveTo = function moveTo(x, y) {
			moving = true;
			goToX = x;
			goToY = y;
		}

		this.moveToTile = function moveToTile(x, y) {
			this.moveTo(x * this.game.grid.tile_size, y * this.game.grid.tile_size);
		}

		this.tileX = function tileX() {
			return Math.round(this.x/this.game.grid.tile_size);
		}

		this.tileY = function tileY() {
			return Math.round(this.y/this.game.grid.tile_size);
		}

		this.isDoingSomething = function isDoingSomething() {
			return moving;
		}
		
	}

});
