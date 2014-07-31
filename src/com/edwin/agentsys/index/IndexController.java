package com.edwin.agentsys.index;

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
import com.edwin.agentsys.base.LoggerUtil;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.index.vo.LoginVo;
import com.edwin.agentsys.index.vo.RegisterVo;
import com.edwin.agentsys.model.Cart;
import com.edwin.agentsys.model.Product;
import com.edwin.agentsys.model.User;
import com.edwin.agentsys.service.CartService;
import com.edwin.agentsys.service.ProductService;
import com.edwin.agentsys.service.UserService;

@Controller
@RequestMapping("/index.do")
public class IndexController {
	@Resource(name="userService")
	UserService userService;
	@Resource(name="cartService")
	CartService cartService;
	@Resource(name="productService")
	ProductService productService;

	
	/**
	 * 首页界面
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "action=init")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoggerUtil.info("首页初始化");
		Map<String, Object> map = new HashMap<String, Object>();
		UserSessionBean userSessionBean=(UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION);
		map.put("userSessionBean", userSessionBean);
		if(userSessionBean!=null){
			map.put("loginDis", "");
			map.put("unloginDis", "none");
			List<Cart> CartList = cartService.findByUserId(userSessionBean.getId());
			map.put("cartSize",CartList.size());
			map.put("hidaddress", userSessionBean.getAddress());
			map.put("hidphone", userSessionBean.getPhone());
		}else{
			map.put("loginDis", "none");
			map.put("unloginDis", "");
			map.put("cartSize",0);
			map.put("hidaddress", "");
			map.put("hidphone", "");
		}
		return new ModelAndView("index",map);
	}
	
	/**
	 * 按页获取商品信息
	 * @param response
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "action=getproduct")
	public ModelAndView getProdcut(HttpServletResponse response,int curPage,int typeId) throws Exception {
		JsonView jsonView=new JsonView();
		List<Map<String,String>> productMapList=new ArrayList<Map<String,String>>();
		List<Product> productList =productService.indexFind(curPage, Constant.INDEX_PRODUCT_SIZE, typeId,null);
		  jsonView.setProperty("productData", productList);
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=registerinit")
	public ModelAndView registerinit(HttpServletResponse response) throws Exception {
		LoggerUtil.info("注册页面初始化");
		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("register",map);
	}
	
	@RequestMapping(params = "action=register")
	public ModelAndView register(RegisterVo registerVo) throws Exception {
		JsonView jsonView=new JsonView();
		LoggerUtil.info("用户注册");
		if(registerVo.getPhone()==""||registerVo.getNick()==""||registerVo.getPsw()==""){
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "参数异常!");
			return new ModelAndView(jsonView);
		}
		User user = new User();
		user.setAccount(registerVo.getPhone());
		//agQxUser.setPsw(MD5Util.Encrypt(registerVo.getPsw()));
		user.setPsw(registerVo.getPsw());
		user.setName(registerVo.getNick());
		user.setPhone(registerVo.getPhone());
		user.setRole_id(2);
		if(userService.findByPhone(registerVo.getPhone()).size()>0){
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "名称或号码已存在，请重新检查!");
			return new ModelAndView(jsonView);
		}
		userService.insertUser(user);
		jsonView.setProperty("isSuc", true);
		jsonView.setProperty("msg", "注册成功!请点击登陆按钮进行登陆!");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=loginout")
	public ModelAndView loginout(HttpServletRequest request) throws Exception {
		JsonView jsonView=new JsonView();
		request.getSession().removeAttribute(Constant.USER_SESSION);
		jsonView.setProperty("isSuc", true);	
		jsonView.setProperty("msg", "登出成功!");
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=login")
	public ModelAndView login(LoginVo loginVo,HttpServletRequest request) throws Exception {
		JsonView jsonView=new JsonView();
		LoggerUtil.info("用户登陆");
		if(loginVo.getL_phone()==""||loginVo.getL_psw()==""){
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "参数异常!");
			return new ModelAndView(jsonView);
		}
		List userList=userService.findByPhone(loginVo.getL_phone());
		if(userList.size()==0){
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "账号或者密码错误!");
			return new ModelAndView(jsonView);
		}
		User user =(User)userList.get(0);
		//密码验证
		if(loginVo.getL_psw().equals(user.getPsw())){
			UserSessionBean userSessionBean=new UserSessionBean();
			userSessionBean.setAccount(user.getAccount());
			userSessionBean.setName(user.getName());
			userSessionBean.setPhone(user.getPhone());
			userSessionBean.setId((int)user.getId());
			userSessionBean.setAddress(user.getAddress());
			request.getSession().setAttribute(Constant.USER_SESSION, userSessionBean);
			List<Cart> CartList = cartService.findByUserId(userSessionBean.getId());
			jsonView.setProperty("cartSize",CartList.size());
			jsonView.setProperty("userInfo", userSessionBean);
			jsonView.setProperty("isSuc", true);
			jsonView.setProperty("msg", "登陆成功!");
		}else{
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "账号或者密码错误!");
			return new ModelAndView(jsonView);
		}
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(params = "action=checkPhone")
	public ModelAndView checkPhone(String phone) throws Exception {
		JsonView jsonView=new JsonView();
		LoggerUtil.info("注册用户号码唯一性检查");
		if(userService.findByPhone(phone).size()>0){
			jsonView.setProperty("isExist", "1"); 
		}else{
			jsonView.setProperty("isExist", "0"); 
		}
		return new ModelAndView(jsonView);
	}
	
}
