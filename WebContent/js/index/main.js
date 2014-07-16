/*初始化*/
$(document).ready(function() {
	indexInit();//首页模块初始化
	loginInit();//登陆模块初始化
    showBoxInit();//商品展示框初始化
    personalInit();//个人中心初始化
    myOrderInit();//我的订单初始化
    $("#productManageBtn").click(function(){
        $('#productMangeDialog').modal('toggle');
        getPProduct(1,"");
    });
    
    $("#pnqueryByBtn").click(function(){
    	var pName=$("#productNameTe").val();
    	 getPProduct(1,pName);
    });
    
    $("#pPageArea").on('click',".p_page",function(){
    	var selVal=0;
    	var pName=$("#productNameTe").val();
    	var curPage=$("#pCurPage").val();
    	var oLastPage=$("#pLastPage").val();
    	var clickVal=$(this).find("a").html();
    	if(clickVal=="上一页"){
    		if(curPage==1)
    			return;
    		else
    			selVal=curPage*1-1;
    	}else if(clickVal=="下一页"){
    		if(curPage==oLastPage)
    			return;
    		else
    			selVal=curPage*1+1;
    	}else{
    		selVal=clickVal;
    	}
    	getPProduct(selVal,pName);
    });

});

function getPProduct(cPage,productName){
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/product.do?action=list_ajaxreq&page="+cPage+"&productName="+encodeURIComponent(encodeURIComponent(productName)),
			dataType : "json",
			cache:false,
			success : function(data) {
				if(data.isSuc){
					$("#productData").find(".dataTr").remove();
					$.each(data.productInfoList,function(i,n){
						var productTmp=$("#productDataTp").html();
						productTmp=productTmp.replace(/@typeName/g, n.typeName);
						productTmp=productTmp.replace(/@name/g, n.name);
						productTmp=productTmp.replace(/@price/g, n.price);
						$("#productData").append("<tr class='dataTr'>"+productTmp+"</tr>");
					});
				}
				$("#pCurPage").val(data.page);
				$("#pLastPage").val(Math.ceil(data.totalPage/data.pageSize));
				$("#pPageArea").html(getPageNavP(data.page,data.pageSize,data.totalPage));
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
}

function getPageNavP(curPage,pageSize,totalPage){
	var pageSum=Math.ceil(totalPage/pageSize);
	var PageNavStr="<div class='pagination' style='margin:0;'><ul>";
	PageNavStr+="<li class='p_page'><a href='#'>上一页</a></li>";
	for(var i=0;i<pageSum;i++){
		if(curPage==(i+1))
			PageNavStr+="<li class='p_page active'><a href='#'>"+(i+1)+"</a><li>";
		else
			PageNavStr+="<li class='p_page'><a href='#'>"+(i+1)+"</a><li>";
	}
	PageNavStr+="<li class='p_page'><a href='#'>下一页</a></li>";
	PageNavStr+="</ul></div>";
	return PageNavStr;
}



