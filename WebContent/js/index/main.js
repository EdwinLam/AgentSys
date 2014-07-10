/*初始化*/
$(document).ready(function() {
	indexInit();//首页模块初始化
	loginInit();//登陆模块初始化
    showBoxInit();//商品展示框初始化
    personalInit();//个人中心初始化
    $('#myOrderDialog').on('mouseenter mouseout', '.listimg', function() {
    	if(event.type=="mouseenter"||event.type=="mouseover"){
    		var tiptool=$(this).parent().find(".tiptool");
        	tiptool.find(".o_name").html($(this).next("input").val());
        	tiptool.find(".o_count").html($(this).next("input").next("input").val());
        	tiptool.find(".o_price").html($(this).next("input").next("input").next("input").val());
        	tiptool.css("top",$(this).position().top-10);
        	tiptool.css("left",$(this).position().left+63);
        	tiptool.show();
    	}else{
    		$(this).parent().find(".tiptool").hide();
    	}
    });
    
    $(".orderinfo").hover(function(){
    	var tiptool=$(this).parent().find(".tiptool");
    	tiptool.css("top",$(this).position().top-tiptool.height()/2);
    	tiptool.css("left",105);
    	tiptool.show();
    },function(){$(this).parent().find(".tiptool").hide();});
    
    $("#orderBtn").click(function(){
    	 getMyOrder();
    });
});

function getMyOrder(){
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/order.do?action=getMyOrder_ajaxreq&page=1",
			dataType : "json",
			cache:false,
			success : function(data) {
				if(data.isSuc){
					$("#myOrderData").find(".dataTr").remove();
					$.each(data.orderDetailList,function(i,n){
						var myOrdertList=$("#myOrderDataTp").html();
						myOrdertList=myOrdertList.replace(/@orderid/g, n.orderid);
						myOrdertList=myOrdertList.replace(/@address/g, n.img_url);
						myOrdertList=myOrdertList.replace(/@phone/g, n.name);
						myOrdertList=myOrdertList.replace(/@totalprice/g, n.totalprice);
						myOrdertList=myOrdertList.replace(/@ordertime/g, n.ordertime);
						var imgList="";
						$.each(n.productInfoList,function(j,k){
							imgList+="<img class='listimg'  src='"+k.img_url+"' />";
							imgList+="<input type='hidden'  value='"+k.name+"'/>";
							imgList+="<input type='hidden'  value='"+k.count+"'/>";
							imgList+="<input type='hidden'  value='"+k.price+"'/>";
						});
						myOrdertList=myOrdertList.replace(/@imgList/g, imgList);
						$("#myOrderData").append("<tr class='dataTr'>"+myOrdertList+"</tr>");
					});
					$('#myOrderDialog').modal('toggle');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
}
