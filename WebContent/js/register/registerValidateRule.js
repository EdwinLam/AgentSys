// 手机号码验证 
jQuery.validator.addMethod("isMobile", function(value, element) { 
  var length = value.length; 
  var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
  return this.optional(element) || (length == 11 && mobile.test(value)); 
}, "请正确填写您的手机号码"); 

jQuery.validator.addMethod("isExistPhone", function(value, element) { 
	var isSuc=false;
	  $.ajax({
		  async: false,
			type : "post",
			url : "/index.do?action=checkPhone&phone=" + value,
			dataType : "json",
			success : function(data) {
				if(data.isExist=="1"){
					isSuc= false;
				}else{
					isSuc= true;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务器正在维护中...");
				return false;
			}
		});
	  return isSuc;
	}, "您的号码已被注册"); 



$(document).ready(function() {
	$("#registerBtn").click(function(){
		$("#registerForm").submit();
	});
	
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
						$("#loginDialog").modal('hide');
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("服务器正在维护中...");
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
    
	//注册模块
	var validator=$("#registerForm").validate({
		errorClass:"help-inline",
		errorElement:"span",
		submitHandler:function(form){
			  $.ajax({
					type : "post",
					url : "/index.do?action=register",
					data: {nick:$("#nick").val(), phone:$("#phone").val(),psw:$("#psw").val()},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
						$("#registerDialog").modal('hide');
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("服务器正在维护中...");
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
			phone : {
					required:true,
					 isMobile:true,
					 isExistPhone:true
				},
			nick : "required",
			email : {
				required : true,
				email : true
			},
			psw : {
				required : true,
				minlength : 5
			},
			confirmpsw : {
				required : true,
				minlength : 5,
				equalTo : "#psw"
			}
		},
		messages : {
			phone :{
				required : "请输入手机号码",
				isMobile:"手机格式有误"
			},
			nick : {
				required : "请输入名称"
			},
			psw : {
				required : "请输入密码",
				minlength : jQuery.format("密码不能小于{0}个字 符")
			},
			confirmpsw : {
				required : "请输入确认密码",
				minlength : "确认密码不能小于5个字符",
				equalTo : "两次输入密码不一致不一致"
			}
		}
	});
	
	$("#registerinitBtn").click(function(){
		validator.resetForm();
		$("#registerForm .control-group").removeClass("error");
		$("#registerForm .control-group").removeClass("success");
		$("#registerForm input[type='text']").val("");
		$("#registerForm input[type='password']").val("");
	});
});