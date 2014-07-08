/*初始化*/
$(document).ready(function() {
	indexInit();//首页模块初始化
	loginInit();//登陆模块初始化
    showBoxInit();//商品展示框初始化
    personalInit();//个人中心初始化
    
    $(".listimg").hover(function(){
    	var tiptool=$(this).parent().find(".tiptool");
    	tiptool.css("top",$(this).position().top-10);
    	tiptool.css("left",$(this).position().left+63);
    	tiptool.show();
    },function(){$(this).parent().find(".tiptool").hide();});
});
