define(["jquery"],  function(require) {
    return function Interpreter(game) {
        this.stack = [];
        this.exited = false;


        this.setup = function() {
            this.stack = [];
            this.exited = false;
        }

        this.addCommand = function(command) {
            if (!this.exited)
                this.stack.push(command);
        }

        this.addExitCommand = function() {
            this.exited = true;
        }

        this.hasSteps = function() {
            return this.stack.length > 0;
        }

        this.nextStep = function() {
            if (this.stack.length > 0) {
                return this.stack.shift();
            }

            return false;
        }
    }
});
