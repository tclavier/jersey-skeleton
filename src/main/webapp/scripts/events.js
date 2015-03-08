define(["jquery"],  function(require) {
    return function Events(game) {
        this.game = game;

        /**
         * Appelé lorsque le joueur arrive sur une nouvelle cellule
         * cellId : Id de la cellule
         */
        this.onPlayerArrivalOn = function(cellId) {
            console.log(cellId);

            // Si c'est la cellule d'arrivée
            if (cellId == 3) {
                alert("Bravo ! Vous avez réussi le niveau !\n\nVous allez être redirigé vers le niveau suivant.");
                // Faire la redirection
            }
        }
    }
});
