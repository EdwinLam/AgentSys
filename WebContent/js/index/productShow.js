
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
	$("#pin_view_layer").css("overflow","scroll");
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
		$(".showboxc").hide();
	});
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
	
	$("#buyBtn").click(function(){
		alert("购买按钮");
	});
	$("#scartBtn").click(function(){
		 addToCart();
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
});

function addToCart(){
	var count=$("#buynum").val();
	var packageId=$("#buynum").val();
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/order.do?action=addToCart_ajaxreq&packageId=" + packageId+"&count="+count,
			dataType : "json",
			success : function(data) {
				alert(data.msg);
				if(data.isSuc=="true"){
					$("#cart").html("<span>"+data.size+"</span>");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
}

/**
 * 展示商品详情
 * @param id
 */
function showProductDialog(id){
	var prodcutEntity=productInfo[id];
	$("body").css("overflow","hidden");
	$("#priceVal").html(prodcutEntity.price);
	$("#titleVal").html(prodcutEntity.name);
	$("#introduceVal").html(prodcutEntity.introduce);
	$("#imgVal").attr("src",prodcutEntity.img_url);
	$("#hidden_packageId").val(prodcutEntity.packageId);
	$("#product_show_dialog").show();
	$("#productShowView").show();
}