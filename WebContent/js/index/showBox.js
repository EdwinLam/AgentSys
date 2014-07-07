function showBoxInit(){
	$(".close-layer").hover(function(){
		$(this).find("i").css("background-position","0 -50px");
	},
	function(){
		$(this).find("i").css("background-position","0 0");
	}
	);
	$(".close-layer i").click(function(){
		$("body").css("overflow","auto");
		$("#product_show_dialog").hide();
		$("#pin_view_layer").css("overflow","none");
		$(".showboxc").hide();
	});
	$("#buyBtn").click(function(){
		addOrder();
	});
	$("#scartBtn").click(function(){
		 addToCart();
	});
	
	function addOrder(){
		var packageId=$("#hidden_packageId").val();
		var count=$("#buynum").val();
		  $.ajax({
			  	async: false,
				type : "post",
				url : "/order.do?action=ordercp_ajaxreq&packageId=" + packageId+"&count="+count,
				dataType : "json",
				success : function(data) {
					if(data.isSuc){
						alert(data.msg);
						location.reload();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					return false;
				}
			});
	}
	
	function addToCart(){
		var count=$("#buynum").val();
		var packageId=$("#buynum").val();
		  $.ajax({
			  	async: false,
				type : "post",
				url : "/order.do?action=addToCart_ajaxreq&packageId=" + packageId+"&count="+count,
				dataType : "json",
				success : function(data) {
					alert(data.msg);
					if(data.isSuc){
						$("#cart span").html(data.size);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					return false;
				}
			});
	}

}