package com.edwin.agentsys.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.LoggerUtil;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.model.AgCpCart;
import com.edwin.agentsys.model.AgCpCartDAO;
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
		
		JsonView jsonView = new JsonView();
		if (packageId <= 0 && count <= 0) {
			jsonView.setSuc(false);
			jsonView.setMsg("参数异常!");
			return new ModelAndView(jsonView);
		}
		
		UserSessionBean userSessionBean = (UserSessionBean) request
				.getSession().getAttribute(Constant.USER_SESSION);
		List agcpCartList = agCpCartDAO.findByUserId(userSessionBean.getId());
		if(agcpCartList.size()>=Constant.CART_SIZE){
			jsonView.setSuc(false);
			jsonView.setMsg("购物车最多放"+Constant.CART_SIZE+"件商品!");
			return new ModelAndView(jsonView);
		}
		AgCpCart agCpCart = new AgCpCart();
		agCpCart.setPackageId(packageId);
		agCpCart.setUserId(userSessionBean.getId());
//		if(	agCpCartDAO.findByExample(agCpCart).size()>0){
//			jsonView.setSuc(false);
//			jsonView.setMsg("该商品已添加到购物车,不能重复添加!");
//			return new ModelAndView(jsonView);
//		}
		agCpCart.setCount(count);
		Transaction tr = HibernateSessionFactory.getSession()
				.beginTransaction(); // 开始事务
		agCpCartDAO.save(agCpCart);
		tr.commit();
		LoggerUtil.info(userSessionBean.getName() + "("
				+ userSessionBean.getAccount() + ")增加商品到购物车");
		jsonView.setSuc(true);
		jsonView.setMsg("已添加到购物车!");
		jsonView.setProperty("size", agcpCartList.size()+1);
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
	public ModelAndView getMyCart(HttpServletRequest request,int userId)
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
		jsonView.setProperty("cartInfoList", cartInfoList);
		return new ModelAndView(jsonView);
	}
}
