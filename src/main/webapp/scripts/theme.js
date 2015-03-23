define(["jquery"],  function($) {
    /**
      Classe permettant de gerer un theme

      game : Instance de Game correspondant au niveau
     **/
    return function Theme(game, themesImgPath) {
        this.game = game;
        this.image = new Image();
        this.image.src = themesImgPath;
        this.loaded = false;
        this.themeId = 0;

        var Animation = require("animation");

        var playerAnimation = new Animation(this.game, themesImgPath, 5, 1, [0, 1, 2, 1, 0, 4, 3, 4], 0.15);
        playerAnimation.ox = 1/2;
        playerAnimation.oy = 1/2;
        playerAnimation.visible = true;


        var instance = this;
        this.image.onload = function() {
            instance.loaded = true;
            //instance.width = Math.floor(instance.image.width / widthInFrame);
            //instance.height = Math.floor(instance.image.height / heightInFrame);
        }

        /**
         * Réinitialise le theme
         */
        this.reset = function() {
            playerAnimation.stop(true);
        }



        // Dessine l'animation 
        this.draw = function draw(context, x, y, width, height) {
            if (!this.loaded || !running) return;
            /*context.save();

            // Rotation (translation pour l'origine, puis rotation)
            context.translate(x, y);
            context.rotate(this.angle);

            // On dessine la bonne frame de l'animation
            var sx = this.width * (this.pattern[this.patternId] % widthInFrame);
            var sy = this.height * Math.floor(this.pattern[this.patternId] / widthInFrame);

            context.drawImage(this.image, sx, sy, this.width, this.height, -this.ox * width, -this.oy * height, width, height);

            context.restore();*/
        }

        this.drawPlayer = function(context, x, y, size, angle) {

            // S'il n'y a pas de theme, on dessine un triangle baisque
            if (this.themeId == -1) {
                // On memorise les transformations deja faites sur le context
                context.save();

                // On commence le dessin
                context.beginPath();

                // On fait la rotation pour obtenir l'angle "angle" (translation pour definir le centre de rotation)
                context.translate(x + size/2, y + size/2);
                context.rotate(angle);
                context.translate(-(x + size/2), -(y + size/2));


                // Defini la marge de chaque coté du joueur (evite que le triangle soit collé au bords des carreaux)
                var margin = 3;

                // Dessine le joueur (triangle)
                context.moveTo(x + margin, y + margin);
                context.lineTo(x + size - margin, y + margin);
                context.lineTo(x + size/2 - margin, y + size - margin);

                // Rempli de couleur rouge le triangle
                context.fillStyle = "#FF0000";
                context.fill();

                // On restaure les transformations (pour eviter d'appliquer la rotation a d'autres elements
                context.restore();
            } else {
                // Sinon, on dessine le joueur en fonction de l'image du theme
                //context.drawImage(this.image, x, y, size, size);

                playerAnimation.angle = angle;
                playerAnimation.draw(context, x + size/2, y + size/2, size, size);

            }

        }

        this.onPlayerStartedWalking = function() {
            // Si l'animation n'est pas deja lancé, on la demarre
            if (!playerAnimation.running()) playerAnimation.start(true);
        }

        this.onPlayerFinishedWalking = function() {
            playerAnimation.stop(true);
        }

        // Met a jour l'animation 
        this.update = function update(delta) {
            if (!this.loaded) return;

            playerAnimation.update(delta);
        }

        /**
         * Test si le theme est chargé
         */
        this.isLoaded = function() {
            return this.loaded;
        }

    }
});
