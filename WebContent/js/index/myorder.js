function myOrderInit(){
   
    $(".oStatus").click(function(){
    	var oStatusName=$(this).find("a").html();
    	var oStatusVal=oStatusName=="全部"?"-1":oStatusName== "未处理"?"0":
    		oStatusName== "处理中"?"1":oStatusName== "已处理"?"2":"-1";
    	$("#oStatusVal").val(oStatusVal);
    	$("#oStatusBtn").html(oStatusName);
    	getMyOrder(1,oStatusVal,false,"");
    });
    
    $("#oStatusBtn").click(function(){
    	var oStatusName=$(this).html();
    	var oStatusVal=oStatusName=="全部"?"-1":oStatusName== "未处理"?"0":
    		oStatusName== "处理中"?"1":oStatusName== "已处理"?"2":"-1";
    	getMyOrder(1,oStatusVal,false,"");
    });
    
    $('#myOrderDialog').on('mouseenter mouseout', '.listimg', function() {
    	if(event.type=="mouseenter"||event.type=="mouseover"){
    		var tiptool=$("#orderProductMenu");
    		tiptool.css("z-index","99999999");
        	tiptool.find(".cartAddress").html($(this).next("input").val());
        	tiptool.find(".o_name").html($(this).next("input").val());
        	tiptool.find(".o_count").html($(this).next("input").next("input").val());
        	tiptool.find(".o_price").html($(this).next("input").next("input").next("input").val());
        	tiptool.css("top",$(this).offset().top-10);
        	tiptool.css("left",$(this).offset().left+63);
        	tiptool.show();
    	}else{
    		$("#orderProductMenu").hide();
    	}
    });
    
    $('#myOrderDialog').on('mouseenter mouseout', '.orderinfo', function() {
    	if(event.type=="mouseenter"||event.type=="mouseover"){
	    	var tiptool=$("#orderTipMenu");
	    	var oAddressVal=$(this).next("input").val();
	    	var oPhoneVal=$(this).next("input").next("input").val();
	    	var oTotalpriceVal=$(this).next("input").next("input").next("input").val();
	    	var oMsgVal=$(this).next("input").next("input").next("input").next("input").val();
	    	tiptool.find(".oAddress").html(oAddressVal);
	    	tiptool.find(".oPhone").html(oPhoneVal);
	    	tiptool.find(".oMsg").html(oMsgVal);
	    	tiptool.find(".oTotalprice").html(oTotalpriceVal);
	    	tiptool.css("z-index","99999999");
	    	tiptool.css("top",$(this).offset().top-tiptool.height()/2+4);
	    	tiptool.css("left",$(this).offset().left+165);
	    	tiptool.show();
    	}else{
    		$("#orderTipMenu").hide();
    	}
    });
    
    $("#orderBtn").click(function(){
    	 getMyOrder(1,-1,true,"");
    	 $("#FunOrderName").html("我的订单");
    });
    
    $("#queryByBtn").click(function(){
    	 var queryOrderIdStr=$("#queryOrderId").val();

    	 $("#oStatusVal").val(-1);
     	$("#oStatusBtn").html("全部");
    	 getMyOrder(1,-1,false,$("#queryOrderId").val());
    });
    
    $("#orderManageBtn").click(function(){
    	getMyOrder(1,-1,true,"");
    	$("#FunOrderName").html("订单管理");
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
    	 getMyOrder(selVal,oStatus,false,"");
    });
}

function getPageNav(curPage,pageSize,totalPage){
	var pageSum=Math.ceil(totalPage/pageSize);
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

function getStatusMenu(roleType,orderid,status){
	var statusName=["未处理","处理中","已处理"];
	var statusMenu="";
		if(roleType==1&&status==0){
			statusMenu+="<input type='button' value='点击处理' onclick='flagOrder("+orderid+",this)'/>";
		}else{
			statusMenu=statusName[status];
		}
  return statusMenu;
}

function flagOrder(orderId,obj){
		base.doReq("/order.do?action=flagorder_ajaxreq",{"orderId":orderId,"flag":2},function(data) {
				if(data.isSuc){
					$(obj).parent().html("已处理");
					base.sAlert(data.msg,3);
				}else{
					base.eAlert(data.msg,3);
				}
			});
}

function getMyOrder(cPage,oStatus,isOpen,orderno){
		base.doReq("/order.do?action=getMyOrder_ajaxreq",{"page":cPage,"status":oStatus,"orderNo":orderno},function(data){
			if(data.isSuc){
				$("#myOrderData").find(".dataTr").remove();
				$.each(data.orderDetailList,function(i,n){
					var myOrdertList=$("#myOrderDataTp").html();
					myOrdertList=myOrdertList.replace(/@orderNo/g, n.orderNo);
					myOrdertList=myOrdertList.replace(/@address/g, n.address);
					myOrdertList=myOrdertList.replace(/@phone/g, n.phone);
					myOrdertList=myOrdertList.replace(/@totalprice/g, n.totalprice);
					myOrdertList=myOrdertList.replace(/@ordertime/g, n.ordertime);
					myOrdertList=myOrdertList.replace(/@statusName/g, getStatusMenu(data.roletype,n.id,n.statusval));
					myOrdertList=myOrdertList.replace(/@msg/g,n.msg );
					var imgList="";
					$.each(n.productInfoList,function(j,k){
						imgList+="<img class='listimg'  src='"+k.img_url+"' style='cursor:pointer'/>";
						imgList+="<input type='hidden'  value='"+k.name+"'  style='cursor:pointer'/>";
						imgList+="<input type='hidden'  value='"+k.count+"'  style='cursor:pointer'/>";
						imgList+="<input type='hidden'  value='"+k.price+"'  style='cursor:pointer'/>";
					});
					myOrdertList=myOrdertList.replace(/@imgList/g, imgList);
					$("#myOrderData").append("<tr class='dataTr'>"+myOrdertList+"</tr>");
				});
				$("#oCurPage").val(data.page);
				$("#oLastPage").val(Math.ceil(data.totalPage/data.pageSize));
				$("#oPageArea").html(getPageNav(data.page,data.pageSize,data.totalPage));
				if(isOpen)
					$('#myOrderDialog').modal('toggle');
			}else{
				base.eAlert(data.msg,3);
			}
		});
}