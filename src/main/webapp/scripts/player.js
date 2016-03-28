define(["jquery"], function (require) {
    return function Player(game, x, y) {
        this.x = x;
        this.y = y;
        this.game = game;
        var dirX = 0;
        var dirY = 1;

        this.moveTo = function moveTo(x, y) {
            this.x = x;
            this.y = y;
        }

        this.moveToTile = function moveToTile(x, y) {
            if (x >= 0 && y >= 0 && x < this.game.grid.tiles[0].length && y < this.game.grid.tiles.length && this.game.grid.tiles[y][x] != 1) {
                this.moveTo(x * this.game.grid.tile_size, y * this.game.grid.tile_size);
                this.game.interpreter.addCommand("player.moveToTile(" + x + ", " + y + ");");
                return true;
            }
            this.game.events.onPlayerCollide();
            //this.game.interpreter.addCommand("alert(\"Collision avec un mur !\");");
            this.game.interpreter.addExitCommand();
            return false;
        }

        this.tileX = function tileX() {
            return Math.round(this.x / this.game.grid.tile_size);
        }

        this.tileY = function tileY() {
            return Math.round(this.y / this.game.grid.tile_size);
        }

        this.turnLeft = function turnLeft() {
            if (dirX == 0) {
                dirX = dirY;
                dirY = 0;
            } else {
                dirY = -dirX;
                dirX = 0;
            }
            this.game.interpreter.addCommand("player.turnLeft();");
        }

        this.turnRight = function turnRight() {
            if (dirX == 0) {
                dirX = -dirY;
                dirY = 0;
            } else {
                dirY = dirX;
                dirX = 0;
            }
            this.game.interpreter.addCommand("player.turnRight();");
        }

        this.moveForward = function moveForward() {
            this.moveToTile(this.tileX() + dirX, this.tileY() + dirY);
        }

        this.moveBackward = function moveBackward() {
            this.moveToTile(this.tileX() - dirX, this.tileY() - dirY);
        }

        this.canGoForward = function canGoForward() {
            this.game.interpreter.addCommand("player.scanForward();");

            return !this.game.grid.isTileSolid(this.tileX() + dirX, this.tileY() + dirY);
        }

        this.canGoBackward = function canGoBackward() {
            this.game.interpreter.addCommand("player.scanBackward();");

            return !this.game.grid.isTileSolid(this.tileX() - dirX, this.tileY() - dirY);
        }

        this.canGoLeft = function canGoLeft() {
            this.game.interpreter.addCommand("player.scanLeft();");
            return !this.game.grid.isTileSolid(this.tileX() + dirY, this.tileY() - dirX);
        }

        this.canGoRight = function canGoRight() {
            this.game.interpreter.addCommand("player.scanRight();");
            return !this.game.grid.isTileSolid(this.tileX() - dirY, this.tileY() + dirX);
        }

        this.hasArrived = function () {
            return this.game.grid.tiles[this.tileY()][this.tileX()] == 3;
        }
        this.moveRight = function () {
            this.moveToTile(this.x + 1, this.y);
        }
        this.moveLeft = function () {
            this.moveToTile(this.x - 1, this.y);
        }
        this.moveUp = function () {
            this.moveToTile(this.x, y - 1);
        }
        this.moveDown = function () {
            this.moveToTile(this.x, y + 1);
        }

    }


});
