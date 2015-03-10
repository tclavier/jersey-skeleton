define(["jquery"],  function(require) {
    return function Interpreter(game) {
        var MAX_NUMBER_INSTRUCTIONS = 5000;
        this.game = game;
        this.stack = [];
        this.exited = false;
        this.executedBlock = 0;
        this.numberInstructions = 0;


        this.setup = function() {
            this.stack = [];
            this.exited = false;
            this.numberInstructions = 0;
        }

        /**
         * Augmente le compteur d'instructions
         * blockId : Id du bloc blockly
         * @return Vrai si le script peut continuer, faut sinon
         */
        this.increment = function(blockId) {
            ++this.numberInstructions;
            if (this.numberInstructions > MAX_NUMBER_INSTRUCTIONS) {
                this.game.events.onInfiniteLoopDetected(blockId);
                this.addCommand("alert(\"Trop d'instructions !\");");
                this.addExitCommand();
                return false;
            }
            return true;
        }

        this.addCommand = function(command) {
            if (!this.exited)
                this.stack.push([command, this.executedBlock]);
        }

        this.addExitCommand = function() {
            this.exited = true;
        }

        this.hasSteps = function() {
            return this.stack.length > 0;
        }

        this.nextStep = function() {
            if (this.stack.length > 0) {
                var command = this.stack.shift();
                if (Blockly) {
                    Blockly.mainWorkspace.getBlockById(command[1]).select();
                }
                return command[0];
            }

            return false;
        }
    }
});
