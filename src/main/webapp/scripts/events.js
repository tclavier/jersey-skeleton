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
            }
        }

        /**
         * Appelé lorsque une boucle infinie est détécté dans le code (durant la pré-execution)
         * blockId : Id du bloc sur qui provoque la boucle infinie
         */
        this.onInfiniteLoopDetected = function(blockId) {
            alert("Boucle infinie sur l'instruction \"" + Blockly.getMainWorkspace().getBlockById(blockId).type + "\"");
        }
    }
});
