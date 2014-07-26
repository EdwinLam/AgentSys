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
		if(userInfo==null){
			base.eAlert("您还没有登陆!");
			return;
		}
		 addToCart();
	});
	
	/**
	 * 增加到购物车
	 */
	function addToCart(){
		var count=$("#buynum").val();
		var packageId=$("#hidden_packageId").val();
		base.doReq("/order.do?action=addToCart_ajaxreq",{
			"packageId" : packageId,
			"count" : count
		}, function(data) {
			if(data.isSuc){
				base.sAlert(data.msg,3);
				$("#cart span").show();
				$("#cart span").html(data.size);
				$("#productShowDialog").modal("hide");
			}else{
				base.eAlert(data.msg,3);
			}
		});
	}

}