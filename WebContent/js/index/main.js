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
    
    $("#productMangeDialog").on('click',".editBtn",function(){
    	$("#productMangeDialog").css("min-height","400px");
    	$("#productMangeDialog").css("max-height","400px");
    	$("#productMangeDialog").css("overflow","hidden");
    	$(".editArea").show();
    	$(".listArea").hide();
    });
    
    $("#pReBtn").click(function(){
    	$("#productMangeDialog").css("min-height","600px");
    	$("#productMangeDialog").css("max-height","600px");
    	$("#productMangeDialog").css("overflow","auto");
    	$(".editArea").hide();
    	$(".listArea").show();
    });
    
    $("#uploadify").uploadify({
        //指定swf文件
        'swf': 'js/uploadify/uploadify.swf',
        //后台处理的页面
        'uploader': 'UploadHandler.ashx',
        //按钮显示的文字
        'buttonText': '上传图片',
        //显示的高度和宽度，默认 height 30；width 120
        //'height': 15,
        //'width': 80,
        //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc': 'Image Files',
        //允许上传的文件后缀
        'fileTypeExts': '*.gif; *.jpg; *.png',
        //发送给后台的其他参数通过formData指定
        //'formData': { 'someKey': 'someValue', 'someOtherKey': 1 },
        //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
        //'queueID': 'fileQueue',
        //选择文件后自动上传
        'auto': true,
        //设置为true将允许多文件上传
        'multi': true
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



