define(["jquery"],  function(require) {
    return function GraphicalPlayer(game, x, y) {
		this.x = x;
		this.y = y;
		this.game = game;

		var goToX = x;
		var goToY = y;
		var goToAngle = 0;
		var moving = false;
		var turning = false;
		var angle = 0;
		var dirX = 0;
		var dirY = 1;

		this.render = function render(context) {
			context.save();
			context.beginPath();
			context.translate(this.x + this.game.grid.tile_size/2, this.y + this.game.grid.tile_size/2);
			context.rotate(angle);
			context.translate(-(this.x + this.game.grid.tile_size/2), -(this.y + this.game.grid.tile_size/2));
			var margin = 3;
			context.moveTo(this.x + margin, this.y + margin);
			context.lineTo(this.x + this.game.grid.tile_size - margin, this.y + margin);
			context.lineTo(this.x + this.game.grid.tile_size/2 - margin, this.y + this.game.grid.tile_size - margin);
			
			context.fillStyle = "#FF0000";
			context.fill();
			context.restore();
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
			
			if (Math.abs(goToAngle - angle) > Math.PI/20) {
				var sign = 1;
				if (goToAngle < angle) sign = -1;
				angle += Math.PI/20 * sign;
			} else {
				angle = goToAngle;
				turning = false;
			}
		}

		this.moveTo = function moveTo(x, y) {
			moving = true;
			goToX = x;
			goToY = y;
		}
		
		this.rotateTo = function rotateTo(a) {
			turning = true;
			goToAngle = a;
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
			return moving || turning;
		}
		
		
		this.turnLeft = function turnLeft() {
			this.rotateTo(angle - Math.PI/2);
			if (dirX == 0) {
				dirX = dirY;
				dirY = 0;
			} else {
				dirY = -dirX;
				dirX = 0;
			}
		}
		
		this.turnRight = function turnRight() {
			this.rotateTo(angle + Math.PI/2);
			if (dirX == 0) {
				dirX = -dirY;
				dirY = 0;
			} else {
				dirY = dirX;
				dirX = 0;
			}
		}
		
		this.moveForward = function moveForward() {
			this.moveToTile(this.tileX() + dirX, this.tileY() + dirY);
		}
		
		this.moveBackward = function moveBackward() {
			this.moveToTile(this.tileX() - dirX, this.tileY() - dirY);
		}
	}

});
