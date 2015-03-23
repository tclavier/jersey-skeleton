define(["jquery"],  function(require) {
	/**
	   Classe representant une animation a partir d'une image
	   
	   game : Instance de Game correspondant au niveau
       imagePath : Fichier de l'image contenant l'animation
       widthInFrame : Nombre de frame sur la largeur de l'image
       heightInFrame : Nombre de frame sur la hauteur de l'image
       pattern : Tableau avec les identifiants representant l'animation
	**/
	return function Animation(game, imagePath, widthInFrame, heightInFrame, pattern, interval) {
		this.game = game;
        if (imagePath.src) {
            this.image = imagePath;
            this.loaded = true;
        } else {
            this.image = new Image();
            this.image.src = imagePath;
            this.loaded = false;
        }

        // Pattern d'animation
        this.pattern = pattern;

        // Numero de la frame sur laquelle est l'animation
        this.patternId = 0;

        // Angle de l'animation
        this.angle = 0;

        // Origine de l'animation (centre de rotation...), valeur entre 0 (gauche) et 1 (droite)
        this.ox = 0;
        this.oy = 0;

        var instance = this;
        var timer = 0;
        var running = false;
        var loop = false;
        
        this.visible = false;

        if (imagePath.src) {
            instance.width = Math.floor(instance.image.width / widthInFrame);
            instance.height = Math.floor(instance.image.height / heightInFrame);
        } else {
            this.image.onload = function() {
                instance.loaded = true;
                instance.width = Math.floor(instance.image.width / widthInFrame);
                instance.height = Math.floor(instance.image.height / heightInFrame);
            }
        }

        /**
         * Commence l'animation
         * looping : Vrai si l'animation doit boucler
         */
        this.start = function(looping) {
            loop = looping;
            timer = 0;
            running = true;
            this.patternId = 0;
        }

        /**
         * Arrete l'animation
         * visible : Si vrai, l'animation reste visible stoppé
         */
        this.stop = function(visible) {
            loop = false;
            timer = 0;
            running = false;
            this.patternId = 0;
            this.visible = visible;
        }

		
		// Dessine l'animation 
		this.draw = function draw(context, x, y, width, height) {
            if ((!this.loaded || !running) && !this.visible) return;
            context.save();

            // Rotation (translation pour l'origine, puis rotation)
            context.translate(x, y);
            context.rotate(this.angle);
            
            // On dessine la bonne frame de l'animation
            var sx = this.width * (this.pattern[this.patternId] % widthInFrame);
            var sy = this.height * Math.floor(this.pattern[this.patternId] / widthInFrame);
            
            context.drawImage(this.image, sx, sy, this.width, this.height, -this.ox * width, -this.oy * height, width, height);
            context.restore();
		}
		
		// Met a jour l'animation 
		this.update = function update(delta) {
            if (!this.loaded || !running) return;
            timer += delta * this.game.getSpeed() * 0.1;
            if (timer >= interval) {
                timer = 0;
                this.patternId = (this.patternId + 1) % this.pattern.length;
                if (this.patternId == 0 && !loop) {
                    running = false;
                }
            }

		}

        /**
         * Retourne vrai si l'animation est entrain de tourner
         */
        this.running = function() {
            return running;
        }
	}
});
