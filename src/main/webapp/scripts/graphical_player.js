var scanAnimation = null;

define(["jquery"],  function($) {
	/**
	   Classe permettant de representer le joueur graphiquement
	   
	   game : Instance de Game correspondant au niveau
	   x : Position en abscisse du joueur (pixel)
	   y : Position en ordonnée du joueur (pixel)
	**/
	return function GraphicalPlayer(game, x, y) {
		this.x = x;
		this.y = y;
		this.game = game;

        // Animation de "scanner" pour les conditions
        var Animation = require("animation");
        if (!scanAnimation) {
            scanAnimation = new Animation(this.game, "images/test.png", 6, 2, [0, 1, 2, 3, 4, 5], 0.1);
            scanAnimation.ox = 1/2;
            scanAnimation.oy = 0;
        }
		
        // Destination du joueur en pixel (pour animer les deplacements vers un point)
		var goToX = x;
		var goToY = y;
		
		// Angle a atteindre (pour animer la rotation du joueur)
		var goToAngle = 0;
		
		// Variable pour savoir si le joueur fait quelque chose (deplacement, rotation)
		var moving = false;
		var turning = false;
		
		// Angle du joueur en radian (utilisé directement pour le dessin)
		var angle = 0;
		
		// Direction du joueur (Ainsi moveForward amenera dans la case (x + dirX, y + dirY))
		var dirX = 0;
		var dirY = 1;
		
		// Dessine le joueur sur le context
		this.render = function render(context) {
            this.game.theme.drawPlayer(context, this.x, this.y, this.game.grid.tile_size, angle);

            // On dessine le scanner
            scanAnimation.draw(context, this.x + this.game.grid.tile_size/2, this.y + this.game.grid.tile_size/2, this.game.grid.tile_size/2, this.game.grid.tile_size/2);
		}
		
		// Met a jour le joueur (utilisé pour gerer les animations)
		this.update = function update(delta) {
			// ----- Animation de deplacement vers (goToX, goToY)
			if (Math.abs(goToX - this.x) > 2) {
				// Si on est encore loin du point a atteindre en x (> 2 px de difference)
				// On determine la direction dans laquelle avancer (1 -> droite, -1 -> gauche)
				var xsign = 1;
				if (goToX - this.x < 0) xsign = -1;
				
				// On avance dans cette direction (en prenant en compte la vitesse general du jeu)
				this.x += 10 * delta * game.getSpeed() * xsign;
                // On s'assure qu'on a pas depassé la cible
                if (((goToX - this.x) * xsign) < 0) this.x = goToX;
			} else {
				// Si c'est bon en X (< 2 px), on s'assure d'etre exactement au point de destination
				this.x = goToX;
				// On fait la meme chose mais en y cette fois ci
				if (Math.abs(goToY - this.y) > 2) {
					var ysign = 1;
					if (goToY - this.y < 0) ysign = -1;

					this.y += 10 * delta * game.getSpeed() * ysign;
                    // On s'assure qu'on a pas depassé la cible
                    if (((goToY - this.y) * ysign) < 0) this.y = goToY;
				} else {
					this.y = goToY;
                    // On prévient le gestionnaire d'evenement que le joueur vient d'atteindre une nouvelle case
                    if (moving) {
                        this.game.events.onPlayerArrivalOn(this.game.grid.tiles[this.tileY()][this.tileX()]);
                        this.game.theme.onPlayerFinishedWalking();
                    }
                    // On indique la fin du deplacement puisque la destination a été atteinte
					moving = false;
				}
			}
			
			// ---- Animation de rotation vers goToAngle
			if (Math.abs(goToAngle - angle) > 0.05) {
				// Meme principe que pour l'animation de deplacement
				// On augmente l'angle jusqu'a ce que la difference entre l'angle du joueur et
				// l'angle a atteindre soit inferieur a Math.PI/20 (les valeurs sont en radians)
				var sign = 1;
				// On determine le meilleur sens de rotation pour arriver le plus vite a l'angle a atteindre
				if (goToAngle < angle) sign = -1;
				
				// On augmente ainsi l'angle dans la direction la plus optimisée
				angle += Math.PI/10 * sign * this.game.getSpeed() * delta;
                // On s'assure qu'on a pas depassé la cible
                if (((goToAngle - angle) * sign) < 0) angle = goToAngle;
			} else {
				// Si on a atteint l'angle cible
				// On s'assure deja d'etre exactement a l'angle cible
				angle = goToAngle;
				// On indique que l'animation de rotation est fini
				turning = false;
			}

            scanAnimation.update(delta);
		}

        this.showMessage = function(text) {
            var canvasPos = $("#grid").offset();
            this.game.showBubble(text, canvasPos.left + this.x, canvasPos.top + this.y);
        }
		
		// Deplace le joueur a la position (x, y) en pixel
		this.moveTo = function moveTo(x, y) {
			moving = true;
			goToX = x;
			goToY = y;

            // On informe le theme
            this.game.theme.onPlayerStartedWalking();
		}
		
		// Fait tourner le joueur jusqu'a l'angle "a" en radian
		this.rotateTo = function rotateTo(a) {
			turning = true;
			goToAngle = a;
		}
		
		// Deplace le joueur vers le carreau (x, y) 
		this.moveToTile = function moveToTile(x, y) {
			this.moveTo(x * this.game.grid.tile_size, y * this.game.grid.tile_size);
		}
		
		// Position x du joueur en carreaux
		this.tileX = function tileX() {
			return Math.round(this.x/this.game.grid.tile_size);
		}
		
		// Position y du joueur en carreaux
		this.tileY = function tileY() {
			return Math.round(this.y/this.game.grid.tile_size);
		}
		
		// Renvoie vrai si le joueur fait quelque chose (deplacement, rotation)
		this.isDoingSomething = function isDoingSomething() {
			return moving || turning || scanAnimation.running();
		}
		
		this.turnLeft = function turnLeft() {
			// Animation de rotation
			this.rotateTo(angle - Math.PI/2);
			
			// Calcul la nouvelle direction apres un 1/4 de tour a gauche
			if (dirX == 0) {
				dirX = dirY;
				dirY = 0;
			} else {
				dirY = -dirX;
				dirX = 0;
			}
		}
		
		this.turnRight = function turnRight() {
			// Animation de rotation
			this.rotateTo(angle + Math.PI/2);
			
			// Calcul la nouvelle direction apres un 1/4 de tour a droite
			if (dirX == 0) {
				dirX = -dirY;
				dirY = 0;
			} else {
				dirY = dirX;
				dirX = 0;
			}
		}

        /**
         * Change l'animation du scanner en fonction de son resultat
         * result : Vrai si le scanner trouve un chemin
         */
        var setScanResult = function(result) {
            scanAnimation.pattern = result ? [0, 1, 2, 3, 4, 5] : [6, 7, 8, 9, 10, 11];
        }

        /**
         * Fonctionis pour faire une animation de scanner
         * scanForward -> Scanner devant le joueur
         * scanBackward -> Scanner derriere le joueur
         * scanLeft -> Scanner a gauche du joueur
         * scanRight -> Scanner a droite du joueur
         */

        this.scanForward = function() {
            setScanResult(!this.game.grid.isTileSolid(this.tileX() + dirX, this.tileY() + dirY));
            scanAnimation.angle = angle;
            scanAnimation.start(false);
        }

        this.scanBackward = function() {
            setScanResult(!this.game.grid.isTileSolid(this.tileX() - dirX, this.tileY() - dirY));
            scanAnimation.angle = angle + Math.PI;
            scanAnimation.start(false);
        }

        this.scanLeft = function() {
            setScanResult(!this.game.grid.isTileSolid(this.tileX() + dirY, this.tileY() - dirX));
            scanAnimation.angle = angle - Math.PI/2;
            scanAnimation.start(false);
        }

        this.scanRight = function() {
            setScanResult(!this.game.grid.isTileSolid(this.tileX() - dirY, this.tileY() + dirX));
            scanAnimation.angle = angle + Math.PI/2;
            scanAnimation.start(false);
        }

	}

});
