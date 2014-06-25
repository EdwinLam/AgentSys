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
	$("body").css("overflow","hidden");
	$("#pin_view_layer").css("overflow","scroll");
	$(".close-layer").hover(function(){
		$(this).find("i").css("background-position","0 -50px");
	},
	function(){
		$(this).find("i").css("background-position","0 0");
	}
	);
	$(".close-layer i").click(function(){
		$("#product_show_dialog").hide();
	});
	//限制购买数量只能输入数字
	$("#buynum").mustInt().blur(function(){
		if($(this).val()==""){
			$(this).val(1);
		}
	});
	//控制数量的按钮
	$("#buynumup").click(function(){
		var num=$("#buynum").val();
		if(num<999)
			$("#buynum").val((num*1+1*1));
		$("#buynumup").attr("disabled",num==999?false:true);
		$("#buynumdown").attr("disabled",num==1?false:true);
	});
	$("#buynumdown").click(function(){
		var num=$("#buynum").val();
		if(num!=1)
			$("#buynum").val(num*1-1*1);
		$("#buynumup").attr("disabled",num==999?false:true);
		$("#buynumdown").attr("disabled",num==1?false:true);
	});
	
	$("#buyBtn").click(function(){
		alert("购买按钮");
	});
	$("#scartBtn").click(function(){
		alert("购物车按钮");
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

function showProductDialog(id){
	
}