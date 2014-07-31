package com.edwin.agentsys.index;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.edwin.agentsys.base.LoggerUtil;
import com.edwin.agentsys.base.Util;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.model.Cart;
import com.edwin.agentsys.model.Order;
import com.edwin.agentsys.model.OrderDetail;
import com.edwin.agentsys.model.Product;
import com.edwin.agentsys.service.CartService;
import com.edwin.agentsys.service.OrderDetailService;
import com.edwin.agentsys.service.OrderService;
import com.edwin.agentsys.service.ProductService;

@Controller
@RequestMapping("/order.do")
public class OrderController {
	@Resource(name="cartService")
	CartService cartService;
	@Resource(name="productService")
	ProductService productService;
	@Resource(name="orderService")
	OrderService orderService;
	@Resource(name="orderDetailService")
	OrderDetailService orderDetailService;
	
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
		cartService.deleteById(cartId);
		List agcpCartList = cartService.findByUserId(userSessionBean.getId());
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
		List<Cart> cartList = cartService.findByUserId(userSessionBean.getId());
		List<Map<String, String>> cartInfoList = new ArrayList<Map<String, String>>();
		Map<String, String> cartInfo = null;
		Cart cart = null;
		Product product = null;
		for (int i = 0; i < cartList.size(); i++) {
			cart = cartList.get(i);
			product = productService.findById(cart.getProduct_id());
			cartInfo = new HashMap<String, String>();
			cartInfo.put("cartId", cart.getId() + "");
			cartInfo.put("count",cart.getCount()+"");
			cartInfo.put("img_url", product.getImg_url());
			cartInfo.put("name", product.getName());
			cartInfo.put("introduce", product.getIntroduce());
			cartInfo.put("price",product.getPrice()+"");
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
		Product product;
		Map<String,String> productInfo;
		List<Order> orderList=orderService.orderFind(sUserId,page,Constant.MYORDER_PAGE_SIZE,status,orderNo);
		for (Order order:orderList) {
			Map<String,Object> orderDetailItenMap = new HashMap<String,Object>();
			orderDetailItenMap.put("orderNo", order.getOrderNo());
			orderDetailItenMap.put("id", order.getId());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			orderDetailItenMap.put("ordertime",  formatter.format(order.getCreateTime()));
			orderDetailItenMap.put("address", order.getAddress());
			orderDetailItenMap.put("phone", order.getPhone());
			orderDetailItenMap.put("totalprice", order.getTotal_price());
			orderDetailItenMap.put("statusval",order.getStatus());
			List<OrderDetail> OrderDetailList=orderDetailService.findByOrderId((int)order.getId());
			List<Map<String,String>> productInfoList=new ArrayList<Map<String,String>>();
			for(OrderDetail orderDetail:OrderDetailList){
				productInfo=new HashMap<String,String>();
				product=productService.findById(orderDetail.getProductId());
				productInfo.put("price", product.getPrice()+"");
				productInfo.put("name", product.getName());
				productInfo.put("img_url",product.getImg_url());
				productInfo.put("count",orderDetail.getCount()+"");
				productInfoList.add(productInfo);
			}
			orderDetailItenMap.put("productInfoList", productInfoList);
			orderDetailList.add(orderDetailItenMap);
		}
		jsonView.setSuc(true);
		jsonView.setMsg("获取我的订单成功!");
		jsonView.setProperty("orderDetailList", orderDetailList);
		jsonView.setProperty("totalPage", orderService.orderSumUp(sUserId, status, orderNo));
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
		Order order= (Order)orderService.findById(orderId);
		order.setStatus(flag);
		orderService.updateOrder(order);
		jsonView.setSuc(true);
		jsonView.setMsg("修改状态成功成功!");
		return new ModelAndView(jsonView);
	}
	
	
	@RequestMapping(params = "action=ordercpcart_ajaxreq")
	public ModelAndView orderCpByCart(HttpServletRequest request,int[] cartIds,int[] counts,String phone,String address,String msg) throws Exception {
		JsonView jsonView = new JsonView();
		UserSessionBean userSessionBean = (UserSessionBean) request .getSession().getAttribute(Constant.USER_SESSION);
		//下订单
		Order order=new Order();
		 order.setCreateTime(new Timestamp(new Date().getTime()));
		 order.setUserId(userSessionBean.getId());
		 order.setPhone(phone);
		 order.setAddress(address);
		 order.setRemark(msg);
		 orderService.insertOrder(order);
		OrderDetail orderDetail ;
		Product product;
		float total_price=0;
		Cart cart;
		for(int i=0;i<cartIds.length;i++){
			cart=cartService.findById(cartIds[i]);
			product=productService.findById(cart.getId());
			orderDetail = new OrderDetail();
			orderDetail.setOrderId((int)order.getId());
			orderDetail.setProductId(cart.getProduct_id());
			orderDetail.setCount(counts[i]);
			total_price+=counts[i]*product.getPrice();
			orderDetailService.insertOrderDetail(orderDetail);
			cartService.deleteById(cart.getId());
		}
		order.setOrderNo(Util.getOrderNo());
		order.setTotal_price(total_price);
		order.setStatus(0);
		orderService.updateOrder(order);
		List agcpCartList = cartService.findByUserId(userSessionBean.getId());
		jsonView.setProperty("size", agcpCartList.size());
		jsonView.setSuc(true);
		jsonView.setMsg("生成订单成功!");
		return new ModelAndView(jsonView);
	}
	
	
//*****************************************************************************************************************
	

	/**
	 * 添加商品到购物车
	 * @param userSessionBean
	 * @param packageId
	 * @param count
	 * @return
	 */
	public JsonView addToCartFun(UserSessionBean userSessionBean,int product_id,int count){
		JsonView jsonView = new JsonView();

		List agcpCartList = cartService.findByUserId(userSessionBean.getId());
		if(agcpCartList.size()>=Constant.CART_SIZE){
			jsonView.setSuc(false);
			jsonView.setMsg("购物车最多放"+Constant.CART_SIZE+"件商品!");
			return jsonView;
		}
		
		Cart cart = new Cart();
		cart.setProduct_id(product_id);
		cart.setUserId(userSessionBean.getId());
		cart.setCount(count);
		cartService.insertCart(cart);
		LoggerUtil.info(userSessionBean.getName() + "("
				+ userSessionBean.getAccount() + ")增加商品到购物车");
		jsonView.setSuc(true);
		jsonView.setMsg("已添加商品["+userSessionBean.getName()+"]到购物车!");
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
