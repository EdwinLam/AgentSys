
	//只能输入0-9的数字和小数点  
    $.fn.mustFloat = function(){  
        return validate($(this),/[^0-9.]/g);  
    };  
    //只能输入>0的正整数  
    $.fn.mustInt = function(){  
        return validate($(this),/\D|^0/g);  
    };  
    function validate(_obj,reg){
        _obj.on("keyup blur",function(){  
            $(this).val($(this).val().replace(reg,'')); 
            if($(this).val().length>3){
            	 $(this).val( $(this).val().substring(0,3));
            }
        }).bind("paste",function(){  //CTR+V事件处理 
            $(this).val($(this).val().replace(reg,''));
            if($(this).val().length>3){
           	 $(this).val( $(this).val().substring(0,3));
           }
        });
        return _obj;  
    }  

$(document).ready(function() {
	//限制购买数量只能输入数字
	$("#buynum").mustInt().blur(function(){
		if($(this).val()==""||$(this).val()==0){
			$(this).val(1);
		}
	});
	
	//控制数量的按钮
	$("#buynumup").click(function(){
		var num=$("#buynum").val();
		if(num<999)
			$("#buynum").val((num*1+1*1));
		$("#buynumup").attr("disabled",num==999?true:false);
	});
	$("#buynumdown").click(function(){
		var num=$("#buynum").val();
		if(num!=1)
			$("#buynum").val(num*1-1*1);
		$("#buynumdown").attr("disabled",num==1?true:false);
	});
	

	
	$("#maBtn").click(function(){
		$("#addressinfo").attr("disabled",false);
		$("#addressinfo").focus();
		$("#maBtn").hide();
	});
	
	$("#addressinfo").blur(function(){
		$("#addressinfo").attr("disabled",true);
		$("#maBtn").show();
	});
	
	//选择全部
	$(".selAll").click(function(){
		 selAll($(this));
	});
	
	$(".cartDelItem").click(function(){
		delCart($(this));
	});
});

function selAll($obj){
	$obj.closest("table").find(".item_chk").each(function(){
	    $(this).prop("checked",$obj.prop("checked"));
	  });
	sumUpPrice();
}

function delCart($obj){
	$obj.closest("tr").remove();
}

function cartAddCount(obj){
	var $obj=$(obj).siblings("input");
	var num=$obj.val();
	$obj.val((num*1+1*1));
	sumUpPrice();
}

function cartReduceCount(obj){
	var $obj=$(obj).siblings("input");
	var num=$obj.val();
	num=num*1-1*1;
	if(num>=1){
		$obj.val(num);
	}
	sumUpPrice();
}

function toDecimal(x) {    

    return x.toFixed(2);    
}   

/**
 * 计算购物车总价
 */
function sumUpPrice(){
	var totalPrice=0;
	$("#cartListShow").find("tbody .dataTr").each(function(){
			var danjia=$(this).find(".danjia").html();
			var count=$(this).find(".item_text").val();
			var danzong= toDecimal(danjia*count);
			$(this).find(".danzong").html(danzong);
		 if($(this).find(".item_chk").prop("checked")){
			totalPrice+=danzong*1;
		 }
	});
	if(totalPrice>0){
		$("#jsBtn").attr("class","submit-btn");
		$("#jsBtn").attr("disabled",false);
	}else{
		$("#jsBtn").attr("class","submit-btn-disabled");
		$("#jsBtn").attr("disabled",true);
	}
	$("#cartListShow").find(".cartprice").html("¥"+ toDecimal(totalPrice));
}

/**
 * 购物车删除商品
 * @param obj
 */
function delCart(obj){
	var cartId=$(obj).closest("tr").find(".item_chk").val();
	 base.doReq("/order.do?action=delCart_ajaxreq",{"cartId":cartId}, function(data) {
			if(data.isSuc){
				base.sAlert(data.msg);
				$("#cart span").html(data.size);
				$($(obj).closest("tr")).remove();
				 sumUpPrice();
			}else{
				base.eAlert(data.msg);
			}
	 });
}

function orderCart(){
	if($("#cartAddrss").val()==""){
		base.eAlert("请填写收货地址!");
		return false;
	}
	if($("#cartPhone").val()==""){
		base.eAlert("请填写联系电话!");
		return false;
	}
	var addUrl="";
	$(".dataTr").each(function(){
		var isCheck=$(this).find(".item_chk:checked").size()>0;
		if(isCheck){
			var cartId=$(this).find(".item_chk").val();
			var count=$(this).find(".item_text").val();
			addUrl+="&cartIds="+cartId+"&counts="+count;
		}
	});
	 base.doReq( "/order.do?action=ordercpcart_ajaxreq"+addUrl,{
		 "phone":$("#cartPhone").val(),
		 "address":$("#cartAddress").val(),
		 "msg":$("#cartMsg").val()
	 },function(data) {
			if(data.isSuc){
				$("#cart span").html(data.size);
				base.sAlert(data.msg);
				sumUpPrice();
				$(".dataTr").each(function(){
					var isCheck=$(this).find(".item_chk:checked").size()>0;
					if(isCheck){
						$(this).remove();
					}
				});

			}else{
				base.eAlert(data.msg);
			}
	});
}

function getMyCart(){
	  base.doReq( "/order.do?action=getMyCart_ajaxreq",{},function(data) {
			if(data.isSuc){
				$("#cartData").find(".dataTr").remove();
				$.each(data.cartInfoList,function(i,n){
					var cartList=$("#cartDataTp").html();
					cartList=cartList.replace(/@cartId/g, n.cartId);
					cartList=cartList.replace(/@img_url/g, n.img_url);
					cartList=cartList.replace(/@name/g, n.name);
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
		});
	
}

