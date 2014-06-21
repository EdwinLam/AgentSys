var curPage = 1;
var isInit=true;
var masNode = $('#masonry');
var ghostMasNode=$("#masonry_ghost");
var imagesLoading = false;


function processNewItems(items) {
	items.each(function() {
				var $this = $(this);
				var imgsNode = $this.find('.imgs');
				var title = $this.find('.title').text();
				var author = $this.find('.author').text();
				title += '&nbsp;&nbsp;(' + author + ')';
				var lightboxName = 'lightbox_'; // + imgNames[0];
				var imgNames = imgsNode.find('input[type=hidden]').val()
						.split(',');
				jQuery
						.each(
								imgNames,
								function(index, item) {
									imgsNode
											.append('<a href="http://fineui.com/case/images/large/'
													+ item
													+ '" data-lightbox="'
													+ lightboxName
													+ '" title="'
													+ title
													+ '"><img src="'
													+ item
													+ '" /></a>');
								});
			});
}


function getNewItems() {

	$(".loading").show();
	var newItemStr="";
	$.ajax({
		type : "post",
		url : "/index.do?action=getproduct&curPage=" + curPage,
		dataType : "json",
		success : function(data) {
			var productData = data.productData;
			$.each(productData, function(idx, item) {
				//输出
				newItemStr += "<div class=\"thumbnail dealflag\">"
						+ "<div class=\"imgs\">"
						+"<a href=\"#\" title=\""+ item.name+"\"><img src=\""+ item.img_url+"\"/></a>"
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
							isFitWidth : true
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


function topAni(){
	$('html,body').animate({scrollTop:0}, 300);
}

$(function() {
	getNewItems();
	$("#elevator").click(function(){topAni();});
	$(window).scroll(
			function() {
				if($(document).scrollTop()<200){
					$("#elevator_item").hide();
				}else{
					$("#elevator_item").show();
				}
				
				if ($(document).height() - $(window).height()- $(document).scrollTop() < 10) {
					if (!imagesLoading) {
						imagesLoading=true;
						 getNewItems();
					}
				}
			});

});