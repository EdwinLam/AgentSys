package com.edwin.agentsys.index;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.model.Product;
import com.edwin.agentsys.service.CartService;
import com.edwin.agentsys.service.ProductService;

@Controller
@RequestMapping("/product.do")
public class ProductController {
	@Resource(name="cartService")
	CartService cartService;
	@Resource(name="productService")
	ProductService productService;
	
	@RequestMapping(params = "action=getProductById_ajaxreq")
	public ModelAndView getProductById(HttpServletRequest request,int id)
			throws Exception {
		JsonView jsonView = new JsonView();
		Product product =productService.findById(id);
		
		jsonView.setSuc(true);
		jsonView.setMsg("获取成功!");
		jsonView.setProperty("type_id", product.getType_id());
		jsonView.setProperty("id",product.getId());
		File f=new File(request.getSession().getServletContext().getRealPath("/") +product.getImg_url());
		if(!f.exists()){
			jsonView.setProperty("img_url", File.separator+ "image"+File.separator+"product"+File.separator+"default.jpg");
		}else
			jsonView.setProperty("img_url", product.getImg_url());
		jsonView.setProperty("introduce", product.getIntroduce());
		jsonView.setProperty("name", product.getName());
		jsonView.setProperty("price", product.getPrice());
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=mdfProduct_ajaxreq")
	public ModelAndView mdfProduc(HttpServletRequest request,int id,float price,String introduce,String name,int type_id,String imgUrl)
			throws Exception {
		JsonView jsonView = new JsonView();
		
		Product product =productService.findById(id);
		
		if(imgUrl==""){
			jsonView.setMsg("请上传一张预览图!");
			jsonView.setSuc(false);
			return new ModelAndView(jsonView);
		}
		product.setImg_url(imgUrl);
		product.setIntroduce(introduce);
		product.setName(name);
		product.setType_id(type_id);
		product.setPrice(price);
		
		
		jsonView.setSuc(true);
		jsonView.setMsg("保存成功!");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=delProduct_ajaxreq")
	public ModelAndView delProduc(HttpServletRequest request,int id)
			throws Exception {
		JsonView jsonView = new JsonView();
		productService.deleteById(id);
		jsonView.setSuc(true);
		jsonView.setMsg("删除成功!");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=addProduct_ajaxreq")
	public ModelAndView addProduc(HttpServletRequest request,float price,String introduce,String name,int type_id,String imgUrl)
			throws Exception {
		name=URLDecoder.decode(name, "UTF-8");
		introduce=URLDecoder.decode(introduce, "UTF-8");
		JsonView jsonView = new JsonView();

		if(imgUrl==""){
			jsonView.setMsg("请上传一张预览图!");
			jsonView.setSuc(false);
			return new ModelAndView(jsonView);
		}
		Product product =new Product();
		product.setImg_url(imgUrl);
		product.setIntroduce(introduce);
		product.setName(name);
		product.setType_id(type_id);
		productService.insertProduct(product);
		jsonView.setSuc(true);
		jsonView.setMsg("保存成功!");
		return new ModelAndView(jsonView);
	}
	
	
	@RequestMapping(params = "action=list_ajaxreq")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,int page,String productName)
			throws Exception {
		JsonView jsonView = new JsonView();
		List<Map> productInfoList=new ArrayList<Map>();
		List<Product> productList=productService.indexFind(page, Constant.LIST_PRODUCT_SIZE,0,productName);
		for(Product product:productList){
			Map<String,String> productInfo=new HashMap<String,String>();
			productInfo.put("id", product.getId()+"");
			productInfo.put("name", product.getName());
			productInfo.put("img_url", product.getImg_url());
			productInfo.put("introduce",product.getIntroduce());
			productInfo.put("typeName", Constant.productType.get(product.getType_id()+""));
			productInfo.put("price", product.getPrice()+"");
			productInfoList.add(productInfo);
		}
		jsonView.setProperty("page",page);
		jsonView.setProperty("pageSize",Constant.LIST_PRODUCT_SIZE);
		jsonView.setProperty("totalPage","10");

		jsonView.setProperty("productInfoList", productInfoList);
		jsonView.setMsg("产品获取成功!");
		jsonView.setSuc(true);
		return new ModelAndView(jsonView);
	}
	
	
	@RequestMapping(params = "action=delCart_ajaxreq")
	public ModelAndView delCart(HttpServletRequest request,
			HttpServletResponse response, int cartId)
			throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request
				.getSession().getAttribute(Constant.USER_SESSION);
		cartService.deleteById(cartId);
		List agcpCartList = cartService.findByUserId(userSessionBean.getId());
		jsonView.setProperty("size", agcpCartList.size());
		jsonView.setSuc(true);
		jsonView.setMsg("删除成功!");
		return new ModelAndView(jsonView);
	}
}
