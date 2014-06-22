$(document).ready(function() {
	$("body").css("overflow","hidden");
	$("#pin_view_layer").css("overflow","scroll");
	$(".close-layer").hover(function(){
		$(this).find("i").css("background-position","0 -50px");
	},
	function(){
		$(this).find("i").css("background-position","0 0");
	}
	);
	
});