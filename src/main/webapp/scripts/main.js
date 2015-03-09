requirejs.config({
	baseUrl: "",
	paths: {
		"jquery": "libs/jquery",
		"blockly": "libs/blockly_compressed",
		"blockly_msg": "libs/blockly_msg",
		"blocks": "libs/blocks_compressed",
		"blockly_js" : "libs/javascript_compressed.js",
		"libs/boostrap": "libs/bootstrap",
		"grid": "scripts/grid",
		"player": "scripts/player",
		"graphical_player": "scripts/graphical_player",
		"game": "scripts/game",
		"interpreter": "scripts/interpreter",
        "events" : "scripts/events",
		"menu_bar_controller": "scripts/page_controllers/menu_bar_controller",
		"users_query": "scripts/queries/users_query",
		"levels_query": "scripts/queries/levels_query"
	},

	shim: {
		"libs/bootstrap": {
			deps: ["jquery"]
		},
		
		"blocks": {
			deps: ["blockly"]
		},
		
		"blockly_msg": {
			deps: ["blocks", "blockly"]
		},
		
		"blockly_js": {
			deps: ["blocks", "blockly", "blockly_msg"]
		},
		
		"menu_bar_controller": {
			deps: ["users_query", "levels_query"]
		}
	}
});


function createBlocklyInstruction(instruction) {
	Blockly.Blocks[instruction.name] = {
	  init: function() {
		this.setColour(instruction.color);
		// Si l'instruction est un bloque
		if (instruction.block == 1) {
			this.appendStatementInput("block").appendField(instruction.name);
		} else {
			this.appendDummyInput().appendField(instruction.name);
		}
		this.setPreviousStatement(true);
		this.setNextStatement(true);
	  }
	};

	Blockly.JavaScript[instruction.name] = function(block) {
		// Si c'est un bloque, on rajoute les {}
		if (instruction.block == 1) {
			// TODO: Le code pour compter le nombre de répétition du bloque et ainsi eviter les boucles infinies
			return instruction.code + " {\n" + Blockly.JavaScript.statementToCode(block, "block") + "\n}";
		}
		return instruction.code + "\n";
	};
}

var game;
var speed = 10;

require(["jquery", "libs/bootstrap", "game", "grid", "player", "interpreter", "graphical_player","events"], function ($) {
	var Game = require("game");
	
	var time;
	function mainLoop() {
		requestAnimationFrame(mainLoop);
		var now = new Date().getTime(),
		dt = (now - (time || now))/1000;

		time = now;
		
		if (window.levelData) {
			// On crée un tableau 2D avec les données
			var tiles = [];
			for (var i = 0; i < window.levelData.structuredContent.length; ++i) {
				tiles.push(window.levelData.structuredContent[i].item);
			}
			game = new Game(tiles);
			
			// On crée les instructions
			Blockly.JavaScript.STATEMENT_PREFIX = "game.interpreter.executedBlock = %1;\n";
			var toolbox = '<xml>';
			for (var i = 0; i < window.levelData.instructionsList.length; ++i) {
				createBlocklyInstruction(window.levelData.instructionsList[i]);
				toolbox += '  <block type="' + window.levelData.instructionsList[i].name + '"></block>';
			}
			toolbox += '</xml>';

			// On crée la zone pour blockly
			Blockly.inject(document.getElementById('blocklyDiv'), {trashcan: true, toolbox: toolbox, maxBlocks: window.levelData.maxInstructions});

            // On met un message dynamique pour afficher le nombre de bloques restant
            Blockly.addChangeListener(function() {
                var remainingBlocks = Blockly.maxBlocks - Blockly.getMainWorkspace().getAllBlocks().length;
                $("#max_instruction").text(remainingBlocks);
                $("#max_instruction_s").text(remainingBlocks > 1 ? "s" : "");
            });
			console.log(window.levelData);
			window.levelData = null;
		}
		
		if (game) game.update(dt);
	}
	mainLoop();
	console.log("main loaded");
});

// Fonction pour executer le code de l'algo
function execute(code) {
	// On regenere la grille pour la remettre a son etat initial
	game.grid.generate()
	// On prepare l'interpreteur
	game.interpreter.setup();
	// On execute les actions sur le joueur invisible permettant juste de remplir l'interpreteur
	var player = game.player;
	// On execute le code
	eval(code);
	// Si tous c'est bien passé, l'interpreteur devrait etre rempli de commande qui vont maintenant pouvoir etre affiché graphiquement
}

$(document).ready(function() {
    document.getElementById('execute').onclick = function () {
        if (Blockly != null) {
            execute(Blockly.JavaScript.workspaceToCode());
        } else {
            alert("Not loaded yet :(");
        }
    }
});

 
 // On intercepte les changements de dimension pour adapter le canvas
$(window).resize(function() {
	if (game) game.updateDimensions();
	$(".blocklySvg").attr("height", $("canvas").parent().height());

});
