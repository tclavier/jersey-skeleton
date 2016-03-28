define(["jquery"], function (require) {
    return function Interpreter(game) {
        var MAX_NUMBER_INSTRUCTIONS = 5000;
        this.game = game;
        this.stack = [];
        this.exited = false;
        this.executedBlock = 0;
        this.numberInstructions = 0;


        this.setup = function () {
            this.stack = [];
            this.exited = false;
            this.numberInstructions = 0;
            this.resetBlocksColour();
        }

        /**
         * Augmente le compteur d'instructions
         * blockId : Id du bloc blockly
         * @return Vrai si le script peut continuer, faux sinon
         */
        this.increment = function (blockId) {
            // Si on est deja sortie du script
            if (this.exited)
                return false;

            ++this.numberInstructions;
            if (this.numberInstructions > MAX_NUMBER_INSTRUCTIONS) {
                this.game.events.onInfiniteLoopDetected(blockId);
                this.addExitCommand();
                return false;
            }
            return true;
        }

        this.addCommand = function (command) {
            if (!this.exited)
                this.stack.push([command, this.executedBlock]);
        }

        //Afficher les commandes
        this.getCommands = function () {
            return this.stack;
        }

        this.addExitCommand = function () {
            this.exited = true;
        }

        this.hasSteps = function () {
            return this.stack.length > 0;
        }


        /**
         * Réinitialise les bloques a leurs couleurs original
         */
        this.resetBlocksColour = function () {
            if (!Blockly)
                return;
            // Resets all the colors
            for (var i = 0; i < Blockly.mainWorkspace.getAllBlocks().length; ++i) {
                var b = Blockly.mainWorkspace.getAllBlocks()[i];
                if (b != null && typeof b['tmpColour'] !== 'undefined') {
                    b.setColour(b.tmpColour);
                    b.tmpColour = undefined;
                }
            }
        }

        this.nextStep = function () {
            if (this.stack.length > 0) {
                var command = this.stack.shift();
                if (Blockly) {
                    this.resetBlocksColour();
                    var block = Blockly.mainWorkspace.getBlockById(command[1]);
                    if (block != null && block.tmpColour == null) {
                        block.tmpColour = block.getColour();

                        // Si c'etait la derniere commande et que c'etait une erreur, on colorise le bloque en rouge
                        if (this.stack.length == 0 && this.exited) {
                            block.setColour(0);
                        } else {
                            block.setColour(block.getColour() - 40);
                        }
                    }

                }
                return command[0];
            }

            return false;
        }
    }
});
