define(["jquery"],  function(require) {
    return function Interpreter(game) {

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

        this.nextStep = function() {
            if (this.stack.length > 0) {
                eval(this.stack.shift());
                return true;
            }

            return false;
        }
    }
});
