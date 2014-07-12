/*初始化*/
$(document).ready(function() {
	indexInit();//首页模块初始化
	loginInit();//登陆模块初始化
    showBoxInit();//商品展示框初始化
    personalInit();//个人中心初始化
    $('#myOrderDialog').on("click",".oStatus",function(){
    	var oStatusName=$(this).find("a").html();
    	var oStatusVal=oStatusName=="全部"?"-1":oStatusName== "未处理"?"0":
    		oStatusName== "处理中"?"1":oStatusName== "已处理"?"2":"-1";
    	$("#oStatusVal").val(oStatusVal);
    	$("#oStatusBtn").html(oStatusName);
    	getMyOrder(1,oStatusVal,false);
    });
    
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
    	 getMyOrder(1,-1,true);
    });
    
    $("#oPageArea").on('click',".o_page",function(){
    	var selVal=0;
    	var oStatus=$("#oStatusVal").val();
    	var curPage=$("#oCurPage").val();
    	var oLastPage=$("#oLastPage").val();
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
    	console.log(selVal+"_"+oStatus);
    	 getMyOrder(selVal,oStatus,false);
    });
});

function getPageNav(curPage,pageSize,totalPage){
	var pageSum=totalPage/pageSize;
	var PageNavStr="<div class='pagination' style='margin:0;'><ul>";
	PageNavStr+="<li class='o_page'><a href='#'>上一页</a></li>";
	for(var i=0;i<pageSum;i++){
		if(curPage==(i+1))
			PageNavStr+="<li class='o_page active'><a href='#'>"+(i+1)+"</a><li>";
		else
			PageNavStr+="<li class='o_page'><a href='#'>"+(i+1)+"</a><li>";
	}
	PageNavStr+="<li class='o_page'><a href='#'>下一页</a></li>";
	PageNavStr+="</ul></div>";
	return PageNavStr;
}

function getMyOrder(cPage,oStatus,isOpen){
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/order.do?action=getMyOrder_ajaxreq&page="+cPage+"&status="+oStatus,
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
						myOrdertList=myOrdertList.replace(/@statusName/g, n.statusName);
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
					$("#oCurPage").val(data.page);
					$("#oLastPage").val(data.totalPage/data.pageSize);
					$("#oPageArea").html(getPageNav(data.page,data.pageSize,data.totalPage));
					if(isOpen)
						$('#myOrderDialog').modal('toggle');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
}
