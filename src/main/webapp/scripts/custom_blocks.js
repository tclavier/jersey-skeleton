function loadCustomBlocks(Blockly) {
	Blockly.Blocks['move_forward'] = {
	  init: function() {
		this.setHelpUrl('http://www.example.com/');
		this.setColour(65);
		this.appendDummyInput()
			.appendField("Avancer");
		this.setPreviousStatement(true);
		this.setNextStatement(true);
		this.setTooltip('');
	  }
	};

	Blockly.JavaScript['move_forward'] = function(block) {
	  var code = 'player.moveForward();';
	  return code;
	};
	
	Blockly.Blocks['move_backward'] = {
	  init: function() {
		this.setHelpUrl('http://www.example.com/');
		this.setColour(65);
		this.appendDummyInput()
			.appendField("Reculer");
		this.setPreviousStatement(true);
		this.setNextStatement(true);
		this.setTooltip('');
	  }
	};

    Blockly.JavaScript['move_backward'] = function(block) {
	  var code = 'player.moveBackward();';
	  return code;
	};

	
	Blockly.Blocks['turn_left'] = {
	  init: function() {
		this.setHelpUrl('http://www.example.com/');
		this.setColour(65);
		this.appendDummyInput()
			.appendField("Tourner à gauche");
		this.setPreviousStatement(true);
		this.setNextStatement(true);
		this.setTooltip('');
	  }
	};

    Blockly.JavaScript['turn_left'] = function(block) {
	  var code = 'player.turnLeft();';
	  return code;
	};

	
	Blockly.Blocks['turn_right'] = {
	  init: function() {
		this.setHelpUrl('http://www.example.com/');
		this.setColour(65);
		this.appendDummyInput()
			.appendField("Tourner à droite");
		this.setPreviousStatement(true);
		this.setNextStatement(true);
		this.setTooltip('');
	  }
	};


    Blockly.JavaScript['turn_right'] = function(block) {
	  var code = 'player.turnRight();';
	  return code;
	};

}

function blocklyLoaded(blockly) {
    window.Blockly = blockly;
    window.parent.document.getElementById('execute').onclick = function () {
        alert(blockly.JavaScript.workspaceToCode());

    };
}
