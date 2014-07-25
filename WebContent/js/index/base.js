function Base(){};
var SUC_ALERT_CLASS="alert alert-success";
var ERROR_ALERT_CLASS="alert alert-error";
var WARNING_ALERT_CLASS="alert";
var INFO_ALERT_CLASS="alert alert-info";
var WEB_CONTEXT="";

Base.prototype = {
		sAlert:function(msg,time){
			if(time==null) time=0;
			$("#alertMsg").html(msg);
			$("#globalAlert").attr("class",SUC_ALERT_CLASS);
			$("#globalAlert").stop();
			$("#globalAlert").fadeTo(1,1);
			$("#globalAlert").show();
			if(time!=0){
				$("#globalAlert").fadeTo(time*1000,0,function(){
					$("#globalAlert").hide();
				});
			}
		},
		wAlert:function(msg,time){
			if(time==null) time=0;
			$("#alertMsg").html(msg);
			$("#globalAlert").attr("class",WARNING_ALERT_CLASS);
			$("#globalAlert").stop();
			$("#globalAlert").fadeTo(1,1);
			$("#globalAlert").show();
			if(time!=0){
				$("#globalAlert").fadeTo(time*1000,0,function(){
					$("#globalAlert").hide();
				});
			}
		},
		eAlert:function(msg,time){
			if(time==null) time=0;
			$("#alertMsg").html(msg);
			$("#globalAlert").attr("class",ERROR_ALERT_CLASS);
			$("#globalAlert").stop();
			$("#globalAlert").fadeTo(1,1);
			$("#globalAlert").show();
			if(time!=0){
				$("#globalAlert").fadeTo(time*1000,0,function(){
					$("#globalAlert").hide();
				});
			}
		},
		iAlert:function(msg,time){
			if(time==null) time=0;
			$("#alertMsg").html(msg);
			$("#globalAlert").attr("class",INFO_ALERT_CLASS);
			$("#globalAlert").stop();
			$("#globalAlert").fadeTo(1,1);
			$("#globalAlert").show();
			if(time!=0){
				$("#globalAlert").fadeTo(time*1000,0,function(){
					$("#globalAlert").hide();
				});
			}
		},
		doReq:function(url,params,fun){
			 var context=this;
			  $.ajax({
				  	async: false,
				  	data:params,
					type : "post",
					url : url,
					dataType : "json",
					cache:false,
					success :function(data){
						if(data.status=="403"){
							context.eAlert("您登陆已超时请重新登陆");
						}else{
							fun.call(this,data);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						context.eAlert("服务器连接异常..");
						return false;
					}
				});
		}
};

//生成base类实例
var base = null;
$(document).ready(function(){
	base = new Base();
});