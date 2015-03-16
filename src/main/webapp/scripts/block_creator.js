define(["jquery"],  function($) {
    return function BlockCreator(game) {
        this.game = game;
        var instance = this;

        // On defini le prefix a chaque fonction permettant d'indiquer la ligne
        Blockly.JavaScript.STATEMENT_PREFIX = "game.interpreter.executedBlock = %1;\n";

        /**
         * Analyse le nom de l'instruction pour gerer les balises
         */
        this.parseInstructionName = function(instructionName, block) {
            //var regexp = /.*%(.)(\[.*\])?%(.*)/g;
            var regexp = /(.*)%(.*)%(.*)/g;
            var match = regexp.exec(instructionName);
            if (match != null && match.length > 3) {
                block.appendField(match[1]);
                regexp = /(.)(\[(.*)\])?/g;
                var subMatch = regexp.exec(match[2]);
                // Ensuite, on recupere la lettre de la commande pour savoir qu'elle est la balise
                if (subMatch != null && subMatch.length > 3) {
                    var cmd = subMatch[1];
                    if (cmd === "v") {
                        // Si c'est un spinner
                        block.appendField(new Blockly.FieldTextInput("1"), "NAME");
                    } else if (cmd === "c") {
                        // Si c'est une combobox
                        // TODO faire les combobox
                    }
                }
                console.log(subMatch);
                block.appendField(match[3]);

            } else {
                block.appendField(instructionName); 
            }
        }

        /**
         * Crée une instruction blockly
         */
        this.createBlocklyInstruction = function(instruction) {
            Blockly.Blocks[instruction.name + instruction.block] = {
                init: function() {
                    this.setColour(instruction.color);
                    // Si l'instruction est un bloque
                    if (instruction.block == 1) {
                        instance.parseInstructionName(instruction.name, this.appendStatementInput("block"));
                    } else if (instruction.block == 2) { 
                        instance.parseInstructionName(instruction.name, this.appendStatementInput("block"));
                        this.appendStatementInput("else").appendField("sinon");
                    } else {
                        instance.parseInstructionName(instruction.name, this.appendDummyInput());
                    }
                    this.setPreviousStatement(true);
                    this.setNextStatement(true);
                }
            };

            Blockly.JavaScript[instruction.name + instruction.block] = function(block) {
                // Remplacement des balises
                var code = instruction.code
                    code = code.replace(new RegExp("%line%", 'g'), block.id);

                // Si c'est un bloque, on rajoute les {}
                if (instruction.block >= 1) {
                    // On ajoute le comptage de bloque
                    code =  code + " {\nif (!game.interpreter.increment(" + block.id + ")) return;\n" + Blockly.JavaScript.statementToCode(block, "block") + "\n}";
                    if (instruction.block == 2) {
                        code += "else {\n" +  Blockly.JavaScript.statementToCode(block, "else") + "\n}"; 
                    }
                }
                return code + "\n";
            };
        }



        /**
         * Recupere la liste xml des instructions
         * instructionList : La liste des instructions a charger/creer et mettre dans la liste xml
         */
        this.getToolbox = function(instructionsList) {
            // On crée les instructions
			var toolbox = '<xml>';
			for (var i = 0; i < instructionsList.length; ++i) {
                var instruction = instructionsList[i];
				this.createBlocklyInstruction(instruction);
                toolbox += '  <block type="' + instruction.name + instruction.block + '"></block>';
			}
			toolbox += '</xml>';

            return toolbox;

        }


    }
});
