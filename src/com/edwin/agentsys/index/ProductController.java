package com.edwin.agentsys.index;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.LoggerUtil;
import com.edwin.agentsys.base.Util;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.model.AgCpCart;
import com.edwin.agentsys.model.AgCpCartDAO;
import com.edwin.agentsys.model.AgCpOrder;
import com.edwin.agentsys.model.AgCpOrderDAO;
import com.edwin.agentsys.model.AgCpOrderdDetail;
import com.edwin.agentsys.model.AgCpOrderdDetailDAO;
import com.edwin.agentsys.model.AgCpPackage;
import com.edwin.agentsys.model.AgCpPackageDAO;
import com.edwin.agentsys.model.AgCpProduct;
import com.edwin.agentsys.model.AgCpProductDAO;
import com.edwin.agentsys.test.HibernateSessionFactory;

@Controller
@RequestMapping("/product.do")
public class ProductController {
	@Autowired
	AgCpCartDAO agCpCartDAO;
	@Autowired
	AgCpPackageDAO agCpPackageDAO;
	@Autowired
	AgCpProductDAO agCpProductDAO;
	@Autowired
	AgCpOrderDAO agCpOrderDAO;
	@Autowired
	AgCpOrderdDetailDAO agCpOrderdDetailDAO;
	
	@RequestMapping(params = "action=getProductById_ajaxreq")
	public ModelAndView getProductById(HttpServletRequest request,int id)
			throws Exception {
		JsonView jsonView = new JsonView();
		AgCpProduct agCpProduct =agCpProductDAO.findById(id);
		AgCpPackage agCpPackage =agCpPackageDAO.findById(agCpProduct.getDefaultPackageId());
		jsonView.setSuc(true);
		jsonView.setMsg("获取成功!");
		jsonView.setProperty("type_id", agCpProduct.getTypeId());
		jsonView.setProperty("id", agCpProduct.getId());
		File f=new File(request.getSession().getServletContext().getRealPath("/") +agCpProduct.getImgUrl());
		if(!f.exists()){
			jsonView.setProperty("img_url", File.separator+ "image"+File.separator+"product"+File.separator+"default.jpg");
		}else
			jsonView.setProperty("img_url", agCpProduct.getImgUrl());
		jsonView.setProperty("introduce", agCpProduct.getIntroduce());
		jsonView.setProperty("name", agCpProduct.getName());
		jsonView.setProperty("price", agCpPackage.getPrice());
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=mdfProduct_ajaxreq")
	public ModelAndView mdfProduc(HttpServletRequest request,int id,float price,String introduce,String name,int type_id,String imgUrl)
			throws Exception {
		JsonView jsonView = new JsonView();
		
		AgCpProduct agCpProduct =agCpProductDAO.findById(id);
		List<AgCpProduct> tmpList=agCpProductDAO.findByName(name);
		if(tmpList.size()>0&&tmpList.get(0).getId()!=agCpProduct.getId()){
			jsonView.setMsg("已存在产品名["+name+"]的产品");
			jsonView.setSuc(false);
			return new ModelAndView(jsonView);
		}
		if(imgUrl==""){
			jsonView.setMsg("请上传一张预览图!");
			jsonView.setSuc(false);
			return new ModelAndView(jsonView);
		}
		AgCpPackage agCpPackage =agCpPackageDAO.findById(agCpProduct.getDefaultPackageId());
		agCpProduct.setImgUrl(imgUrl);
		agCpProduct.setIntroduce(introduce);
		agCpProduct.setName(name);
		agCpProduct.setTypeId(type_id);
		agCpPackage.setPrice(price);
		agCpProductDAO.save(agCpProduct);
		agCpPackageDAO.save(agCpPackage);
		
		jsonView.setSuc(true);
		jsonView.setMsg("保存成功!");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=delProduct_ajaxreq")
	public ModelAndView delProduc(HttpServletRequest request,int id)
			throws Exception {
		JsonView jsonView = new JsonView();
		AgCpProduct agCpProduct =agCpProductDAO.findById(id);
		AgCpPackage agCpPackage =agCpPackageDAO.findById(agCpProduct.getDefaultPackageId());
		agCpProductDAO.delete(agCpProduct);
		agCpPackageDAO.delete(agCpPackage);
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
		if(agCpProductDAO.findByName(name).size()>0){
			jsonView.setMsg("已存在产品名["+name+"]的产品");
			jsonView.setSuc(false);
			return new ModelAndView(jsonView);
		}
		if(imgUrl==""){
			jsonView.setMsg("请上传一张预览图!");
			jsonView.setSuc(false);
			return new ModelAndView(jsonView);
		}
		AgCpProduct agCpProduct =new AgCpProduct();
		agCpProduct.setImgUrl(imgUrl);
		agCpProduct.setIntroduce(introduce);
		agCpProduct.setName(name);
		agCpProduct.setTypeId(type_id);
		agCpProductDAO.save(agCpProduct);
		
		AgCpPackage agCpPackage =new AgCpPackage();
		agCpPackage.setName(name);
		agCpPackage.setProductId(agCpProduct.getId());
		agCpPackage.setPrice(price);
		agCpPackageDAO.save(agCpPackage);

		agCpProduct.setDefaultPackageId(agCpPackage.getId());

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
		List<AgCpProduct> agCpProductList=agCpProductDAO.findByPage(page, Constant.LIST_PRODUCT_SIZE,productName,0);
		for(AgCpProduct agCpProduct:agCpProductList){
			Map<String,String> productInfo=new HashMap<String,String>();
			productInfo.put("id", agCpProduct.getId()+"");
			productInfo.put("package_id", agCpProduct.getDefaultPackageId()+"");
			productInfo.put("name", agCpProduct.getName());
			productInfo.put("img_url", agCpProduct.getImgUrl());
			productInfo.put("introduce", agCpProduct.getIntroduce());
			productInfo.put("typeName", Constant.productType.get(agCpProduct.getTypeId()+""));
			AgCpPackage agCpPackage=agCpPackageDAO.findById(agCpProduct.getDefaultPackageId());
			productInfo.put("price", agCpPackage.getPrice()+"");
			productInfoList.add(productInfo);
		}
		jsonView.setProperty("page",page);
		jsonView.setProperty("pageSize",Constant.LIST_PRODUCT_SIZE);
		jsonView.setProperty("totalPage",agCpProductDAO.getTotalCount(productName));

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
		Transaction tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpCartDAO.delete(agCpCartDAO.findById(cartId));
		tr.commit();
		HibernateSessionFactory.getSession().flush();
		List agcpCartList = agCpCartDAO.findByUserId(userSessionBean.getId());
		jsonView.setProperty("size", agcpCartList.size());
		jsonView.setSuc(true);
		jsonView.setMsg("删除成功!");
		return new ModelAndView(jsonView);
	}

	/**
	 * 读取我的购物车
	 * 
	 * @param request
	 * @param response
	 * @param packageId
	 * @param count
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "action=getMyCart_ajaxreq")
	public ModelAndView getMyCart(HttpServletRequest request)
			throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request
				.getSession().getAttribute(Constant.USER_SESSION);
		// 读出购物车参数
		List agcpCartList = agCpCartDAO.findByUserId(userSessionBean.getId());
		List<Map<String, String>> cartInfoList = new ArrayList<Map<String, String>>();
		Map<String, String> cartInfo = null;
		AgCpCart agcpCart = null;
		AgCpPackage agCpPackage = null;
		AgCpProduct agCpProduct = null;
		for (int i = 0; i < agcpCartList.size(); i++) {
			agcpCart = (AgCpCart) agcpCartList.get(i);
			agCpPackage = agCpPackageDAO.findById(agcpCart.getPackageId());
			agCpProduct = agCpProductDAO.findById(agCpPackage.getProductId());
			cartInfo = new HashMap<String, String>();
			cartInfo.put("cartId", agcpCart.getId() + "");
			cartInfo.put("packageId", agcpCart.getPackageId() + "");
			cartInfo.put("count", agcpCart.getCount() + "");
			cartInfo.put("img_url", agCpProduct.getImgUrl());
			cartInfo.put("name", agCpProduct.getName());
			cartInfo.put("introduce", agCpProduct.getIntroduce());
			cartInfo.put("price", agCpPackage.getPrice() + "");
			cartInfoList.add(cartInfo);
		}
		jsonView.setSuc(true);
		jsonView.setMsg("成功获取!");
		jsonView.setProperty("cartInfoList", cartInfoList);
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=getMyOrder_ajaxreq")
	public ModelAndView getMyOrder(HttpServletRequest request,int page,int status,String orderNo)
			throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request
				.getSession().getAttribute(Constant.USER_SESSION);
		userSessionBean.setRoleType(1);
		// 读出订单参数
		int sUserId=userSessionBean.getRoleType()==1?-1:userSessionBean.getId();
		List<Map<String,Object>> orderDetailList=new ArrayList<Map<String,Object>> ();
		AgCpPackage agCpPackage;
		AgCpProduct agCpProduct;
		Map<String,String> productInfo;
		List<AgCpOrder> agCpOrderList=agCpOrderDAO.findByUserId(sUserId,page,Constant.MYORDER_PAGE_SIZE,status,orderNo);
		for (AgCpOrder agCpOrder:agCpOrderList) {
			Map<String,Object> orderDetailItenMap = new HashMap<String,Object>();
			orderDetailItenMap.put("orderid", agCpOrder.getOrderId());
			orderDetailItenMap.put("id", agCpOrder.getId());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			orderDetailItenMap.put("ordertime",  formatter.format(agCpOrder.getCreateTime()));
			orderDetailItenMap.put("address", agCpOrder.getAddress());
			orderDetailItenMap.put("phone", agCpOrder.getPhone());
			orderDetailItenMap.put("totalprice", agCpOrder.getTotalPrice());
			orderDetailItenMap.put("statusval",agCpOrder.getStatus());
			List<AgCpOrderdDetail> agCpOrderdDetailList=agCpOrderdDetailDAO.findByOrderId(agCpOrder.getId());
			List<Map<String,String>> productInfoList=new ArrayList<Map<String,String>>();
			for(AgCpOrderdDetail agCpOrderdDetail:agCpOrderdDetailList){
				productInfo=new HashMap<String,String>();
				agCpPackage=agCpPackageDAO.findById(agCpOrderdDetail.getPackageId());
				agCpProduct=agCpProductDAO.findById(agCpPackage.getProductId());
				productInfo.put("price", agCpPackage.getPrice()+"");
				productInfo.put("name", agCpProduct.getName());
				productInfo.put("img_url",agCpProduct.getImgUrl());
				productInfo.put("count",agCpOrderdDetail.getCount()+"");
				productInfoList.add(productInfo);
			}
			orderDetailItenMap.put("productInfoList", productInfoList);
			orderDetailList.add(orderDetailItenMap);
		}
		jsonView.setSuc(true);
		jsonView.setMsg("获取我的订单成功!");
		jsonView.setProperty("orderDetailList", orderDetailList);
		jsonView.setProperty("totalPage", agCpOrderDAO.getTotalCountByUserId(sUserId,status,orderNo));
		jsonView.setProperty("roletype", userSessionBean.getRoleType());
		jsonView.setProperty("page", page);
		jsonView.setProperty("pageSize", Constant.MYORDER_PAGE_SIZE+"");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=flagorder_ajaxreq")
	public ModelAndView flagOrder(HttpServletRequest request,int orderId,int flag) throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request .getSession().getAttribute(Constant.USER_SESSION);
		userSessionBean.setRoleType(1);
		if(userSessionBean.getRoleType()!=1){
			jsonView.setSuc(false);
			jsonView.setMsg("您不能执行该操作!");
			return new ModelAndView(jsonView);
		}
		AgCpOrder agCpOrder= (AgCpOrder)agCpOrderDAO.findById(orderId);
		agCpOrder.setStatus(flag);
		Transaction tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpOrderDAO.save(agCpOrder);
		tr.commit();
		HibernateSessionFactory.getSession().flush();
		jsonView.setSuc(true);
		jsonView.setMsg("修改状态成功成功!");
		return new ModelAndView(jsonView);
	}
	
	
	@RequestMapping(params = "action=ordercp_ajaxreq")
	public ModelAndView orderCp(HttpServletRequest request,int packageId,int count) throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request .getSession().getAttribute(Constant.USER_SESSION);
		AgCpOrder agCpOrder=new AgCpOrder();
		AgCpOrderdDetail agCpOrderdDetail = new AgCpOrderdDetail();
		agCpOrder.setCreateTime(new Timestamp(new Date().getTime()));
		agCpOrder.setOrderId(Util.getOrderNo());
		agCpOrder.setUserId(userSessionBean.getId());
		agCpOrder.setStatus(0);
		AgCpPackage agCpPackage=agCpPackageDAO.findById(packageId);
		agCpOrder.setTotalPrice(agCpPackage.getPrice()*count);
		
//		if(agCpPackage.getPrice()*count<Constant.MIN_ORDER_PRICE){
//			jsonView =  addToCartFun( userSessionBean, packageId, count);
//			if(jsonView.isSuc()){
//				jsonView.setMsg("未满"+Constant.MIN_ORDER_PRICE+"元，不能下订单，已加入购物车!");
//			}
//			return new ModelAndView(jsonView);
//		}
		Transaction tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpOrderDAO.save(agCpOrder);
		tr.commit();
		tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpOrderdDetail.setOrderId(agCpOrder.getId());
		agCpOrderdDetail.setPackageId(packageId);
		agCpOrderdDetail.setCount(count);
		agCpOrderdDetail.setPrice(agCpPackage.getPrice());
		agCpOrderdDetailDAO.save(agCpOrderdDetail);
		tr.commit();
		HibernateSessionFactory.getSession().flush();
		jsonView.setSuc(true);
		jsonView.setMsg("购买成功!");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=ordercpcart_ajaxreq")
	public ModelAndView orderCpByCart(HttpServletRequest request,int[] cartIds,int[] counts) throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request .getSession().getAttribute(Constant.USER_SESSION);
		//下订单
		AgCpOrder agCpOrder=new AgCpOrder();
		agCpOrder.setCreateTime(new Timestamp(new Date().getTime()));
		agCpOrder.setUserId(userSessionBean.getId());
		Transaction tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpOrderDAO.save(agCpOrder);
		AgCpOrderdDetail agCpOrderdDetail ;
		tr.commit();
		HibernateSessionFactory.getSession().flush();
		tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		float total_price=0;
		AgCpCart agCpCart;
		AgCpPackage agCpPackage;
		for(int i=0;i<cartIds.length;i++){
			agCpCart=agCpCartDAO.findById(cartIds[i]);
			agCpCart.getPackageId();
			agCpPackage=agCpPackageDAO.findById(agCpCart.getPackageId());
			agCpOrderdDetail = new AgCpOrderdDetail();
			agCpOrderdDetail.setOrderId(agCpOrder.getId());
			agCpOrderdDetail.setPackageId(agCpCart.getPackageId());
			agCpOrderdDetail.setPrice(agCpPackage.getPrice());
			agCpOrderdDetail.setCount(counts[i]);
			total_price+=counts[i]*agCpPackage.getPrice();
			agCpOrderdDetailDAO.save(agCpOrderdDetail);
			agCpCartDAO.delete(agCpCart);
		}
		agCpOrder.setOrderId(Util.getOrderNo());
		agCpOrder.setTotalPrice(total_price);
		agCpOrder.setStatus(0);
		agCpOrderDAO.save(agCpOrder);
		tr.commit();
		HibernateSessionFactory.getSession().flush();
		jsonView.setSuc(true);
		jsonView.setMsg("购买成功!");
		return new ModelAndView(jsonView);
	}
	
	
//*****************************************************************************************************************
	
	/**
	 * 获取购物车总价格
	 * @param agcpCartList
	 * @return
	 */
	public float getTotalCartPrice(List<AgCpCart> agcpCartList){
		float totalprice=0;
		AgCpPackage agCpPackage;
		for(AgCpCart agCpCart:agcpCartList){
			agCpPackage=agCpPackageDAO.findById(agCpCart.getPackageId());
			totalprice+=agCpPackage.getPrice()*agCpCart.getCount();
		}
		return totalprice;
	}
	
	/**
	 * 添加商品到购物车
	 * @param userSessionBean
	 * @param packageId
	 * @param count
	 * @return
	 */
	public JsonView addToCartFun(UserSessionBean userSessionBean,int packageId,int count){
		JsonView jsonView = new JsonView();
		if (packageId <= 0 && count <= 0) {
			jsonView.setSuc(false);
			jsonView.setMsg("参数异常!");
			return jsonView;
		}
		List agcpCartList = agCpCartDAO.findByUserId(userSessionBean.getId());
		if(agcpCartList.size()>=Constant.CART_SIZE){
			jsonView.setSuc(false);
			jsonView.setMsg("购物车最多放"+Constant.CART_SIZE+"件商品!");
			return jsonView;
		}
		AgCpCart agCpCart = new AgCpCart();
		agCpCart.setPackageId(packageId);
		agCpCart.setUserId(userSessionBean.getId());
//		if(	agCpCartDAO.findByExample(agCpCart).size()>0){
//			jsonView.setSuc(false);
//			jsonView.setMsg("该商品已添加到购物车,不能重复添加!");
//			return jsonView;
//		}
		agCpCart.setCount(count);
		Transaction tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpCartDAO.save(agCpCart);
		tr.commit();
		HibernateSessionFactory.getSession().flush();
		LoggerUtil.info(userSessionBean.getName() + "("
				+ userSessionBean.getAccount() + ")增加商品到购物车");
		jsonView.setSuc(true);
		jsonView.setMsg("已添加到购物车!");
		jsonView.setProperty("size", agcpCartList.size()+1);
		return jsonView;
	}
	
	private String getStatusNameById(Integer status){
		if(status==null){
			return "暂无状态";
		}
		if(status==0){
			return "未处理";
		}else if(status==1){
			return "处理中";
		}else if(status==2){
			return "已处理";
		}else{
			return "NOT FOUND";
		}
	}
	
	
	
	
}
