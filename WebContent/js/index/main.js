
function refreshPage(){
	 getPProduct(1,"");
	curPage = 1;
	masNode.masonry('destroy');
	 isInit=true;
	 productInfo = [];
	 typeId=0;
	getNewItems();
}

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
    $("#ppsaveBtn").click(function(){
    	var type_id=$("#pp_type_id").val();
		var name=$("#pp_name").val();
		var price=$("#pp_price").val();
		var introduce=	$("#pp_introduce").val();
		var img_url=$("#detail_img").attr("src");
		if(name==""){
			base.eAlert("名称不能为空!",3);
			return;
		}
		if(price==""){
			base.eAlert("请输入价格!",3);
			return;
		}
		if(img_url==""){
			base.eAlert("请上传预览图!",3);
			return;
		}
    	savePProduct(name,introduce,price,type_id,img_url);
    });
    $("#ppMdfBtn").click(function(){
    	var id=$("#pp_id").val();
    	var type_id=$("#pp_type_id").val();
		var name=$("#pp_name").val();
		if(name==""){
			base.eAlert("名称不能为空!",3);
			return;
		}
		if(price==""){
			base.eAlert("请输入价格!",3);
			return;
		}
		if(img_url==""){
			base.eAlert("请上传预览图!",3);
			return;
		}
		var price=$("#pp_price").val();
		var introduce=	$("#pp_introduce").val();
		var img_url=$("#detail_img").attr("src");
    	mdfPProductById(id,name,introduce,price,type_id,img_url);
    });
    
    $("#ppAddBtn").click(function(){
		$("#pp_type_id").val(1);
		$("#pp_name").val("");
		$("#pp_price").val("");
		$("#pp_introduce").val("");
		$("#pp_id").val("");
		$("#detail_img").attr("src","\\image\\product\\default.jpg");
    	$("#productMangeDialog").css("min-height","400px");
    	$("#productMangeDialog").css("max-height","400px");
    	$("#productMangeDialog").css("overflow","hidden");
    	$(".editArea").show();
    	$(".listArea").hide();
    	$(".editMode").hide();
    	$(".addMode").show();
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
    	$(".editMode").show();
    	$(".addMode").hide();
    	getPProductById($(this).parent().find("input").val());
    });
    
    
    $("#productMangeDialog").on('click',".delBtn",function(){
    	delPProductById($(this).next("input").val());
    });
    
    $("#pReBtn").click(function(){
    	 reToList();
    });
    
    $("#uploadify").uploadify({
        //指定swf文件
        'swf': 'js/uploadify/uploadify.swf',
        //后台处理的页面
        'uploader': '/base.do?action=upload',
        //按钮显示的文字
        'buttonText': '上传图片',
        //显示的高度和宽度，默认 height 30；width 120
        //'height': 15,
        //'width': 80,
        //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc': 'Image Files',
        'fileObjName'   : 'file',  
        //允许上传的文件后缀
        'fileTypeExts': '*.gif; *.jpg; *.png',
        //发送给后台的其他参数通过formData指定
        //'formData': { 'someKey': 'someValue', 'someOtherKey': 1 },
        //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
        //'queueID': 'fileQueue',
        //选择文件后自动上传
        'onUploadSuccess':function(file, data, response){
        	  var jsonObject = jQuery.parseJSON(data);
        	alert(jsonObject.savUrlPath);
        	$("#detail_img").attr("src",jsonObject.savUrlPath);
        },
        'auto': true,
        //设置为true将允许多文件上传
        'multi': false
    });
});

/**
 * 返回产品列表
 */
function reToList(){
	$("#productMangeDialog").css("min-height","600px");
	$("#productMangeDialog").css("max-height","600px");
	$("#productMangeDialog").css("overflow","auto");
	$(".editArea").hide();
	$(".listArea").show();
}


/**
 * 修改产品ID
 * @param id
 * @param name
 * @param introduce
 * @param price
 * @param type_id
 * @param img_url
 */
function mdfPProductById(id,name,introduce,price,type_id,img_url){
	base.doReq("/product.do", {
		"action" : "mdfProduct_ajaxreq",
		"id" : id,
		"name":name,
		"introduce":introduce,
		"price":price,
		"type_id":type_id,
		"imgUrl":img_url
	}, function(data) {
		if (data.isSuc) {
			base.sAlert(data.msg,3);
			reToList();
			refreshPage();
		}else{
			base.eAlert(data.msg,3);
		}
	});
}

/**
 * 新增产品
 * @param name
 * @param introduce
 * @param price
 * @param type_id
 * @param img_url
 */
function savePProduct(name,introduce,price,type_id,img_url){
		base.doReq("/product.do", {
			"action" : "addProduct_ajaxreq",
			"name":name,
			"introduce":introduce,
			"price":price,
			"type_id":type_id,
			"imgUrl":img_url
		},function(data){
			if (data.isSuc) {
				base.sAlert(data.msg,3);
				reToList();
				refreshPage();
			}else{
				base.eAlert(data.msg,3);
			}
		});
}

/**
 * 删除产品
 * @param id
 */
function delPProductById(id){
	base.doReq("/product.do", {
		"action" : "delProduct_ajaxreq",
		"id":id
	},function(data){
		if (data.isSuc) {
			base.sAlert(data.msg,3);
			reToList();
			refreshPage();
		}else{
			base.eAlert(data.msg,3);
		}
	});
}

/**
 * 产品详细信息
 * @param id
 */
function getPProductById(id){
	base.doReq("/product.do",{"action":"getProductById_ajaxreq","id":id},
		function(data){
			if(data.isSuc){
				$("#pp_type_id").val(data.type_id);
				$("#pp_name").val(data.name);
				$("#pp_price").val(data.price);
				$("#pp_introduce").val(data.introduce);
				$("#pp_id").val(data.id);
				$("#detail_img").attr("src",data.img_url);
		}
	});
}

function getPProduct(cPage,productName){
		base.doReq("/product.do", {
		"action" : "list_ajaxreq",
		"page" : cPage,
		"productName" : productName
	}, function(data) {
		if (data.isSuc) {
			$("#productData").find(".dataTr").remove();
			$.each(data.productInfoList, function(i, n) {
				var productTmp = $("#productDataTp").html();
				productTmp = productTmp.replace(/@typeName/g, n.typeName);
				productTmp = productTmp.replace(/@name/g, n.name);
				productTmp = productTmp.replace(/@price/g, n.price);
				productTmp = productTmp.replace(/@id/g, n.id);
				$("#productData").append(
						"<tr class='dataTr'>" + productTmp + "</tr>");
			});
		}
		$("#pCurPage").val(data.page);
		$("#pLastPage").val(Math.ceil(data.totalPage / data.pageSize));
		$("#pPageArea").html(
				getPageNavP(data.page, data.pageSize, data.totalPage));
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



