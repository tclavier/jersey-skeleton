define(["jquery"],  function(require) {
	/**
		Classe representant la grille d'un niveau
		
		game : Instance de Game correspondant au niveau
		tiles : Tableau a deux dimensions de la grille du niveau
		width : Largeur (en pixel) de la zone pour dessiner la grille
		height : Hauteur (en pixel) de la zone pour dessiner la grille
		Les careaux seront redimensionné en fonction des dimensions de la zone dessinable
	**/
	return function Grid(game, tiles, width, height) {
		this.game = game;
		this.tiles = tiles;
		
		// Calcul de la taille des carreaux en fonction de la taille de la zone dessinable
		this.tile_size = Math.min(width/this.tiles.length, width/this.tiles[0].length);
		
		this.width = this.tile_size * this.tiles[0].length;
		this.height = this.tile_size * this.tiles.length;
		
		// Tableau correspondant au couleur des carreaux en fonction de leur id (voir wiki)
		var tilesColors = ["#EEEEEE", "#222222", "#EEEEEE", "#FFFF00", "#55FF55"];

		// Genere la grille (place le joueur...)
		this.generate = function() {
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
					context.fillStyle = "#EEEEEE";
					
					// Si le carreaux a une couleur particuliere defini dans tilesColor
					if (id < tilesColors.length) {
						context.fillStyle = tilesColors[id];
					}
					
					// On dessine le carreaux avec la bonne couleur
					context.fillRect(xx*this.tile_size, yy*this.tile_size, this.tile_size, this.tile_size);
					context.strokeRect(xx*this.tile_size, yy*this.tile_size, this.tile_size, this.tile_size);
				}
			}
		}
		
		
		this.update = function update(delta) {
		}
	}


});
