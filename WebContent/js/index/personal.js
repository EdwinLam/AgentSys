function personalInit(){
	$("#personalBtn").click(function(){
		$("#p_phone").val(userInfo.phone);
		$("#p_address").val(userInfo.address);
		$('#personalDialog').modal('toggle');
	});	
	$("#mdfPBtn").click(function(){
		$("#personalForm").submit();
	});
	
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
						if(data.isSuc){
							base.sAlert(data.msg,3);
							$('#personalDialog').modal('toggle');
							 userInfo.address=$("#p_address").val();
						}else{
							base.eAlert(data.msg,3);
						}
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