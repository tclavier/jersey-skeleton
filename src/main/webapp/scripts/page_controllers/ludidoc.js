
$(document).ready(function() {
		
	$('#sidebar').affix({
		offset: {
			top: -100
		}
	});
	
	function setActiveDoc(newElement) {
		$("#sidebar a").removeClass("active");
		newElement.addClass("active");
	}
	
	function createInput(instruction, idx) {
		var nav = $("#sidebar");
		var doc = $("#doc-container");
		
		nav.append($('<li><a href="#' + idx + '" for="' + idx + '">' + instruction.name + '</a></li>'));
		
		doc.append($('<div id="' + idx + '" class="panel panel-default doc-element">' +
						'<div class="panel-heading">' +
							'<img src="' + instruction.imageUrl+ '" alt="' + instruction.name + '"/>' +
						'</div>' +
						'<div class="panel-body">' +
						'<img src="' + instruction.animationUrl+ '" alt="' + instruction.name + '"/>' +
						instruction.description +
						'</div>' +
					'</div>'));
		console.log(instruction);
	}
	
	function setActive(idElement) {
		
	}
	
	/*function toggleDoc(clickedElement) {
		currentMenuElement.removeClass("active");
		currentDocElement.hide();
		
		currentDocElement = $("#" + clickedElement.attr("for"));
		currentMenuElement = clickedElement;
				
		currentDocElement.show();
		currentMenuElement.addClass("active");
	}
	
	currentMenuElement = $($('#sidebar a')[0]);
	currentDocElement = $("#" + currentMenuElement.attr("for"));
	toggleDoc(currentMenuElement);
	*/
	
	
	$.getJSON("v1/instructions", function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			createInput(data[i], i);
		}
		
		$('#sidebar a').click(function() {
			console.log($(this).parent());
			setActiveDoc($(this).parent());
		});
		
		currentMenuElement = $($('#sidebar a')[0]).parent();
		setActiveDoc(currentMenuElement);
	});
	
});