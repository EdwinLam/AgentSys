package com.edwin.agentsys.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.LoggerUtil;
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
@RequestMapping("/order.do")
public class OrderController {
	AgCpCartDAO agCpCartDAO = new AgCpCartDAO();
	AgCpPackageDAO agCpPackageDAO=new AgCpPackageDAO();
	AgCpProductDAO agCpProductDAO = new AgCpProductDAO();
	AgCpOrderDAO agCpOrderDAO = new AgCpOrderDAO();
	AgCpOrderdDetailDAO agCpOrderdDetailDAO= new AgCpOrderdDetailDAO();
	

	/**
	 * 添加商品到购物车
	 * 
	 * @param request
	 * @param response
	 * @param packageId
	 * @param count
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "action=addToCart_ajaxreq")
	public ModelAndView addToCart(HttpServletRequest request,
			HttpServletResponse response, int packageId, int count)
			throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request
				.getSession().getAttribute(Constant.USER_SESSION);
		JsonView jsonView =  addToCartFun( userSessionBean, packageId, count);
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
	public ModelAndView getMyOrder(HttpServletRequest request,int userId)
			throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request
				.getSession().getAttribute(Constant.USER_SESSION);
		// 读出订单参数
		AgCpPackage agCpPackage;
		AgCpProduct agCpProduct;
		Map<String,String> productInfo;
		List<AgCpOrder> agCpOrderList=agCpOrderDAO.findByUserId(userSessionBean.getId());
		for (AgCpOrder agCpOrder:agCpOrderList) {
			List<AgCpOrderdDetail> agCpOrderdDetailList=agCpOrderdDetailDAO.findByOrderId(agCpOrder.getId());
			for(AgCpOrderdDetail agCpOrderdDetail:agCpOrderdDetailList){
				productInfo=new HashMap<String,String>();
				agCpPackage=agCpPackageDAO.findById(agCpOrderdDetail.getPackageId());
				agCpProduct=agCpProductDAO.findById(agCpPackage.getProductId());
			}
		}
		return new ModelAndView(jsonView);
	}
	
	
	@RequestMapping(params = "action=ordercp_ajaxreq")
	public ModelAndView orderCp(HttpServletRequest request,int packageId,int count) throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request .getSession().getAttribute(Constant.USER_SESSION);
		AgCpOrder agCpOrder=new AgCpOrder();
		AgCpOrderdDetail agCpOrderdDetail = new AgCpOrderdDetail();
		agCpOrder.setCreateTime(new Date());
		agCpOrder.setUserId(userSessionBean.getId());
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
		agCpOrder.setCreateTime(new Date());
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
		agCpOrder.setTotalPrice(total_price);
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
	
	
	
	
}
