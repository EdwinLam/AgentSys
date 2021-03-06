var curPage = 1;
var isInit=true;
var masNode = $('#masonry');
var imagesLoading = false;
var productInfo = [];
var typeId=0;

/**
 * 主页初始化函数
 */
function indexInit(){
/*页面初始化*/
	getNewItems();
/*按钮事件绑定*/
	//返回顶部按钮
	$("#elevator").click(function(){topAni();});
	//购物车按钮
	$("#cart").click(function(){
		showCartListDialog();
	});
	//登出按钮
	$("#loginoutBtn").click(function(){
		 loginout();
	});
/*滚动条监听*/
	$(window).scroll(
	   function() {
		   //根据滚条条情况自动隐藏返回顶部按钮
		   autoHideTopBtn();
		   //滚动条拉倒最下时自动更新商品
		   autoLoadProduct();
	});
}

function changeType(obj,type_id){
	typeId=type_id;
	if(!$(obj).hasClass("active")){
		curPage = 1;
		masNode.masonry('destroy');
		 isInit=true;
		 productInfo = [];
		$(obj).parent().parent().find(".active").removeClass("active");
		$(obj).addClass("active");
		getNewItems();
	}
}


/**
 * 购物车展示框
 * @param id
 */
function showCartListDialog(){
	if(userInfo==null){
		base.eAlert("登陆后才能使用购物车功能! ",3);
		return;
	}
	getMyCart();
	$("#cartPhone").val(userInfo.phone);
	$("#cartAddress").val(userInfo.address);
	$("body").css("overflow","hidden");
	$("#product_show_dialog").show();
	$("#cartListShow").show();
}

/**
 * 加载新的产品
 */
function getNewItems() {
	if(isInit)	masNode.html("");
	$(".loading").show();
	var newItemStr="";
	$.ajax({
		type : "post",
		url : "/index.do?action=getproduct&curPage=" + curPage+"&typeId="+typeId,
		dataType : "json",
		success : function(data) {
			var productData = data.productData;
			$.each(productData, function(idx, item) {
				productInfo[item.id]=item;//将物品信息写入数组
				//输出
				newItemStr += "<div class=\"thumbnail dealflag\">"
						+ "<div class=\"imgs\">"
						+"<a href=\"javascript:;\" title=\""+ item.name+"\" onclick=\"showProductDialog("
						+ item.id+")\"><img src=\""+ item.img_url+"\"/></a>"
						+ "<div class=\"pricebox\">"
						+ "<div class=\"showbox\">"
						+ "<span class=\"yuan\">¥</span><span class=\"num\">"
						+ item.price+
				"</span>" + "</div><div class=\"mask\"></div></div>" + "</div>"
						+ "<div class=\"caption\">" + "	<div class=\"title\">"
						+ item.name + "</div>"
						+ "	<div class=\"content\"></div> " + " </div> "
						+ "</div>";
			});
			masNode.append($(newItemStr));
			var items=masNode.find(".dealflag").removeClass("dealflag");
			if(items.length>0){
				items.css('opacity', 0);
				items.imagesLoaded(function() {
					items.css('opacity', 1);
					if(isInit){
						isInit=false;
						masNode.masonry({
							itemSelector : '.thumbnail',
							isFitWidth : true,
						});
					}else{
						masNode.masonry('appended', items);
					}
					$(".loading").hide();
					curPage++;
					imagesLoading = false;
				});
			}else{
				imagesLoading = false;
				$(".loading").hide();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}


/**
 * 登出按钮
 */
function loginout(){
	  $.ajax({
		  	async: false,
			type : "post",
			url : "/index.do?action=loginout",
			dataType : "json",
			success : function(data) {
				base.sAlert(data.msg,3);
				userInfo=null;
				$(".afterlogin").hide();
				$(".beforelogin").show();
				$("#cart span").hide();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				return false;
			}
		});
}

/**
 * 滚动条拉倒最下时自动更新商品
 */
function autoLoadProduct(){
	if ($(document).height() - $(window).height()- $(document).scrollTop() < 10) {
		if (!imagesLoading) {
			imagesLoading=true;
			 getNewItems();
		}
	}
}

/**
 * 展示商品详情
 * @param id
 */
function showProductDialog(id){
	var prodcutEntity=productInfo[id];
	$("#productShowDialog").modal();
	$("#priceVal").html(prodcutEntity.price);
	$("#titleVal").html(prodcutEntity.name);
	$("#introduceVal").html(prodcutEntity.introduce);
	$("#imgVal").attr("src",prodcutEntity.img_url);
    $("#hidden_productId").val(prodcutEntity.id);
;
}

/**
 * 根据滚条条情况自动隐藏返回顶部按钮
 */
function autoHideTopBtn(){
	if($(document).scrollTop()<200){
		$("#elevator").css("opacity","0");
	}else{
		$("#elevator").css("opacity","1");
	}
}

/**
 * 页面返回顶部
 */
function topAni(){
	$('html,body').animate({scrollTop:0}, 300);
}