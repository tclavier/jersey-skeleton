define(["jquery"],  function(require) {
    return function Grid(game, tiles, width, height) {
        this.game = game;
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.tile_size = (width/tiles.length);

        var tilesColors = ["#EEEEEE", "#222222", "#EEEEEE", "#FFFF00", "#55FF55"];

        this.render = function render(context) {
            for (var xx = 0; xx < tiles.length; ++xx) {
                for (var yy = 0; yy < tiles[0].length; ++yy) {
                    var id = tiles[yy][xx];
                    context.fillStyle = "#EEEEEE";
                    if (id < tilesColors.length) {
                        context.fillStyle = tilesColors[id];
                    }

                    context.fillRect(xx*this.tile_size, yy*this.tile_size, this.tile_size, this.tile_size);
                    context.strokeRect(xx*this.tile_size, yy*this.tile_size, this.tile_size, this.tile_size);
                }
            }
        }

        this.update = function update(delta) {

        }
    }


});
