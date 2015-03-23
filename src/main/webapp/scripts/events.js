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
            // Affiche une bulle de dialogue a coté de l'instruction qui provoque la boucle infinie
            /*var block = Blockly.getMainWorkspace().getBlockById(blockId);
            var coord = block.getRelativeToSurfaceXY();
            var svgText = Blockly.createSvgElement("svg", {"x": 200, "y": 200}, null);
            console.log(svgText);
            var bubble = new Blockly.Bubble(block.workspace, Blockly.Comment.prototype.createEditor_(), block.svgPath_, coord.x, coord.y, null, null);
            */
            //alert("Boucle infinie sur l'instruction \"" + Blockly.getMainWorkspace().getBlockById(blockId).type + "\"");
        }
    }
});
