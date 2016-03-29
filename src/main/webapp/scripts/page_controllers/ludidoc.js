/**
 * Useless : ludidoc.html is an emty html page
 */


$(document).ready(function () {

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
        var name = instruction.name;
        var regexp = /(.*)%(.*)%(.*)/g;
        var match = regexp.exec(name);
        if (match != null && match.length > 3)
            name = match[1] + " N " + match[3];

        name += (instruction.block == 2 ? " ... sinon" : "");
        nav.append($('<li><a href="#' + idx + '" for="' + idx + '">' + name + '</a></li>'));

        doc.append($('<div id="' + idx + '" class="panel panel-default doc-element">' +
                '<div class="panel-heading">' +
                '<img src="' + instruction.imageUrl + '" alt="' + name + '"/>' +
                '</div>' +
                '<div class="panel-body">' +
                //'<img src="' + instruction.animationUrl+ '" alt="' + name + '"/>' +
                instruction.description +
                '</div>' +
                '</div>'));
        console.log(instruction);
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

    $.getJSON("v1/instructions", function (data) {
        for (var i = 0; i < data.length; i++) {
            createInput(data[i], i);
        }
    });
});