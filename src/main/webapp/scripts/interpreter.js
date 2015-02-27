define(["jquery"],  function(require) {
    return function Interpreter(game) {
        this.stack = [];


        this.addCommand = function(command) {
            stack.push(command);
        }

        this.nextStep = function() {
            if (stack.length > 0) {
                eval(stack.shift());
                return true;
            }

            return false;
        }
    }
});
