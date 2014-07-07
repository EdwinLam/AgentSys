/**
 * 登陆窗口初始化函数
 */
function loginInit(){
	$("#loginBtn").click(function(){
		$("#loginForm").submit();
	});
	
	//登陆模块
	$("#loginForm").validate({
		errorClass:"help-inline",
		errorElement:"span",
		submitHandler:function(form){
			  $.ajax({
					type : "post",
					url : "/index.do?action=login",
					data: {l_phone:$("#l_phone").val(), l_psw:$("#l_psw").val()},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
						 location.reload();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						return false;
					}
				});
		 },
		errorPlacement: function(error, element) { 
			$obj=element.parent().parent();
		    error.appendTo(element.parent());
			$obj.attr("class","control-group error");
		},
		 highlight : function(element) {
			  $obj=$(element).parent().parent();
			  $obj.attr("class","control-group error");
         },  
		success:function(label) {
			$obj=label.parent().parent();
			 $obj.attr("class","control-group success");
		},
		rules : {
			l_phone : {
					required:true,
					 isMobile:true
				},
			 l_psw : {
				required : true,
				minlength : 5
			}
		},
		messages : {
			l_phone :{
				required : "请输入手机号码",
				isMobile:"手机格式有误"
			},
			l_psw : {
				required : "请输入密码",
				minlength : jQuery.format("密码不能小于{0}个字 符")
			}
		}
	});
}

