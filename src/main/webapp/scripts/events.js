define(["jquery"],  function(require) {
    return function Events(game) {
        this.game = game;

        /**
         * Appel� lorsque le joueur arrive sur une nouvelle cellule
         * cellId : Id de la cellule
         */
        this.onPlayerArrivalOn = function(cellId) {
            // Si c'est la cellule d'arriv�e
            if (cellId == 3) {
            	levelFinished();
                // On réinitialise les instructions pour eviter que l'animation continue apres la fin du niveau
                this.game.interpreter.setup();
            }
        }
        
        this.onPlayerCollide = function() {
        	// TODO : Avertir l'utilisateur de la collision correctement
        	this.game.interpreter.addCommand("alert(\"Collision avec un mur !\");");
        }

        /**
         * Appelé lorsque une boucle infinie est détécté dans le code (durant la pré-execution)
         * blockId : Id du bloc sur qui provoque la boucle infinie
         */
        this.onInfiniteLoopDetected = function(blockId) {
            //alert("Boucle infinie sur l'instruction \"" + Blockly.getMainWorkspace().getBlockById(blockId).type + "\"");
        }
    }
});
