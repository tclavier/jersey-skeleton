define(["jquery"], function (require) {
    /**
     Classe representant la grille d'un niveau
     
     game : Instance de Game correspondant au niveau
     tiles : Tableau a deux dimensions de la grille du niveau
     width : Largeur (en pixel) de la zone pour dessiner la grille
     height : Hauteur (en pixel) de la zone pour dessiner la grille
     Les careaux seront redimensionn� en fonction des dimensions de la zone dessinable
     **/
    return function Grid(game, tiles, width, height) {
        this.game = game;
        this.tiles = tiles;

        // Tableau correspondant au couleur des carreaux en fonction de leur id (voir wiki)
        var tilesColors = ["#EEEEEE", "#222222", "#EEEEEE", "#FFFF00", "#55FF55"];

        this.updateDimensions = function updateDimensions(width, height) {
            // Calcul de la taille des carreaux en fonction de la taille de la zone dessinable
            this.tile_size = Math.min(width / this.tiles.length, width / this.tiles[0].length);

            this.width = this.tile_size * this.tiles[0].length;
            this.height = this.tile_size * this.tiles.length;
        }

        this.updateDimensions(width, height);

        // Genere la grille (place le joueur...)
        this.generate = function () {
            for (var yy = 0; yy < this.tiles.length; ++yy) {
                for (var xx = 0; xx < this.tiles[yy].length; ++xx) {
                    var tileId = this.tiles[yy][xx];

                    if (tileId == 2) {
                        this.game.createPlayer(xx, yy);
                    }
                }
            }

        }

        // Dessine les carreaux de la grille
        this.render = function render(context) {
            for (var yy = 0; yy < this.tiles.length; ++yy) {
                for (var xx = 0; xx < this.tiles[yy].length; ++xx) {
                    var id = this.tiles[yy][xx];

                    this.game.theme.drawTile(context, id, xx * this.tile_size, yy * this.tile_size, this.tile_size);
                }
            }
        }


        this.update = function update(delta) {
        }

        this.isTileSolid = function isTileSolid(tilex, tiley) {
            if (tilex < 0 || tiley < 0 || tiley >= this.tiles.length || tilex >= this.tiles[0].length)
                return true;
            return this.tiles[tiley][tilex] == 1;
        }
    }


});
