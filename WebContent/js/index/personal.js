function personalInit(){
	$("#personalBtn").click(function(){
		$("#p_phone").val($("#hidphone").val());
		$("#p_address").val($("#hidaddress").val());
		$('#personalDialog').modal('toggle');
	});	
	$("#mdfPBtn").click(function(){
		$("#personalForm").submit();
	});
	
	//登陆模块
	$("#personalForm").validate({
		errorClass:"help-inline",
		errorElement:"span",
		submitHandler:function(form){
			  $.ajax({
					type : "post",
					url : "/user.do?action=mAddress_ajaxreq",
					data: {address:$("#p_address").val()},
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
			p_address : {
					required:true
				}
		},
		messages : {
			p_address :{
				required : "请输入地址"
			}
		}
	});
}