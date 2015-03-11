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
        this.image = new Image();
        this.image.src = imagePath;
        this.loaded = false;
        this.patternId = 0;

        var instance = this;
        var timer = 0;

        this.image.onload = function() {
            instance.loaded = true;
            instance.width = Math.floor(instance.image.width / widthInFrame);
            instance.height = Math.floor(instance.image.height / heightInFrame);
        }
		
		// Dessine l'animation 
		this.draw = function draw(context, x, y, width, height) {
            if (!this.loaded) return;
            var sx = this.width * pattern[this.patternId] % widthInFrame;
            var sy = this.height * Math.floor(pattern[this.patternId] / widthInFrame);
            context.drawImage(this.image, sx, sy, this.width, this.height, x, y, width, height);
		}
		
		// Met a jour l'animation 
		this.update = function update(delta) {
            if (!this.loaded) return;
            timer += delta;

            if (timer >= interval) {
                timer = 0;
                this.patternId = (this.patternId + 1) % pattern.length;
            }

		}
	}
});
