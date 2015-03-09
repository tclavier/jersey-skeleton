define(["jquery"],  function(require) {
    return function Interpreter(game) {
        this.stack = [];
        this.exited = false;
        this.executedBlock = 0;


        this.setup = function() {
            this.stack = [];
            this.exited = false;
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
