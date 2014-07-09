/*初始化*/
$(document).ready(function() {
	indexInit();//首页模块初始化
	loginInit();//登陆模块初始化
    showBoxInit();//商品展示框初始化
    personalInit();//个人中心初始化
    
    $(".listimg").hover(function(){
    	var tiptool=$(this).parent().find(".tiptool");
    	tiptool.css("top",$(this).position().top-10);
    	tiptool.css("left",$(this).position().left+63);
    	tiptool.show();
    },function(){$(this).parent().find(".tiptool").hide();});
    $(".orderinfo").hover(function(){
    	var tiptool=$(this).parent().find(".tiptool");
    	tiptool.css("top",$(this).position().top-tiptool.height()/2);
    	tiptool.css("left",105);
    	tiptool.show();
    },function(){$(this).parent().find(".tiptool").hide();});
    
    $("#orderBtn").click(function(){
    	$('#myOrderDialog').modal('toggle');
    });
});

function getMyOrder(){
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/order.do?action=getMyOrder_ajaxreq",
			dataType : "json",
			success : function(data) {
				if(data.isSuc){
					
					$("#myOrderData").find(".dataTr").remove();
					$.each(data.cartInfoList,function(i,n){
						var cartList=$("#myOrderDataTp").html();
						cartList=cartList.replace(/@orderid/g, n.cartId);
						cartList=cartList.replace(/@address/g, n.img_url);
						cartList=cartList.replace(/@phone/g, n.name);
						cartList=cartList.replace(/@price/g, n.price);
						cartList=cartList.replace(/@count/g, n.count);
						cartList=cartList.replace(/@danzong/g, n.count*n.price);
						$("#cartData").append("<tr class='dataTr'>"+cartList+"</tr>");
					});
					
					sumUpPrice();
					$("#cartData .dataTr .item_text").mustInt().blur(function(){
						if($(this).val()==""||$(this).val()==0){
							$(this).val(1);
						}
					});
					$("#cartData .dataTr .trisumup").bind("click keyup blur",function(){
						 sumUpPrice();
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
}
