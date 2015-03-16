define(["jquery"],  function($) {
    return function BlockCreator(game) {
        this.game = game;
        var instance = this;

        // On defini le prefix a chaque fonction permettant d'indiquer la ligne
        Blockly.JavaScript.STATEMENT_PREFIX = "game.interpreter.executedBlock = %1;\n";

        /**
         * Ajoute un champs pour editer un entier a un bloc
         * block : Instance d'un bloc blockly
         * param : Parametre du spinner (min,max)
         */
        this.appendSpinner = function(block, param) {
            var minMax = param.split(",");
            if (minMax.length != 2) return;
            var min = parseInt(minMax[0]);
            var max = parseInt(minMax[1]);

            block.appendField(new Blockly.FieldTextInput(min+"", function validator(text) {
                // D'abord on s'assure que c'est bien un entier
                if (Blockly.FieldTextInput.numberValidator(text) == null)
                return null;

            return (parseInt(text) >= min && parseInt(text) <= max) ? text : null;
            }), "vField");
        }

        /**
         * Ajoute une liste deroulante a un bloc
         * block : Instance d'un bloc blockly
         * param : Parametre de la liste deroulante (element1:val1,element2:val2)
         */
        this.appendCombobox = function(block, param) {
            var list = [];
            var elements = param.split(",");
            for (var i = 0; i < elements.length; ++i) {
                var element = elements[i].split(":");
                if (element.length == 2)
                    list.push([element[0], element[1]]);
            }

            block.appendField(new Blockly.FieldDropdown(list), "cField");
        }

        /**
         * Analyse le nom de l'instruction pour gerer les balises
         * instructionName : Nom de l'instruction a analyser
         * block : L'instance du bloque blockly
         */
        this.parseInstructionName = function(instructionName, block) {
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
                        this.appendSpinner(block, subMatch[3]);
                    } else if (cmd === "c") {
                        // Si c'est une combobox
                        this.appendCombobox(block, subMatch[3]); 
                    }
                }
                block.appendField(match[3]);
            } else {
                block.appendField(instructionName); 
            }
        }


        this.parseCodeTags = function(block, code) {
            // Balise %line%
            code = code.replace(new RegExp("%line%", 'g'), block.id);

            // Balise %v%
            code = code.replace(new RegExp("%v%", 'g'), block.getFieldValue("vField"));

            // Balise %c%
            code = code.replace(new RegExp("%c%", 'g'), block.getFieldValue("cField"));

            return code;
        }

        /**
         * Crée une instruction blockly
         * L'instruction a créer (instance d'un objet avec les attributs name et block)
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
                var code = instance.parseCodeTags(block, instruction.code);
                    //
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
