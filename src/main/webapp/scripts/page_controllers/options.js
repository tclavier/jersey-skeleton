$(document).ready(function() {
	
	//Activation de facebook
	$("#facebook_checkbox").change(function() {
		enableFacebook($(this).prop("checked"));
	});
	
});