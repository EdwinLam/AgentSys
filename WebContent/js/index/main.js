/*初始化*/
$(document).ready(function() {
	indexInit();//首页模块初始化
	loginInit();//登陆模块初始化
    showBoxInit();//商品展示框初始化
    personalInit();//个人中心初始化
    myOrderInit();//我的订单初始化
    $("#productManageBtn").click(function(){
        $('#productMangeDialog').modal('toggle');
    });

});

function getPProduct(cPage){
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/product.do?action=action=list_ajaxreq&page="+cPage,
			dataType : "json",
			cache:false,
			success : function(data) {
				if(data.isSuc){
					$("#productData").find(".dataTr").remove();
					$.each(data.productInfoList,function(i,n){
						var productTmp=$("#productDataTp").html();
						productTmp=productTmp.replace(/@typeName/g, n.orderid);
						productTmp=productTmp.replace(/@name/g, n.address);
						productTmp=productTmp.replace(/@price/g, n.phone);
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
}


