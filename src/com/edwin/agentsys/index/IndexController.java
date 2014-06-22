package com.edwin.agentsys.index;

import java.util.ArrayList;
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
import com.edwin.agentsys.base.MD5Util;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.ProductShowBean;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.index.vo.LoginVo;
import com.edwin.agentsys.index.vo.RegisterVo;
import com.edwin.agentsys.model.AgCpPackage;
import com.edwin.agentsys.model.AgCpPackageDAO;
import com.edwin.agentsys.model.AgCpProduct;
import com.edwin.agentsys.model.AgCpProductDAO;
import com.edwin.agentsys.model.AgQxUser;
import com.edwin.agentsys.model.AgQxUserDAO;
import com.edwin.agentsys.test.HibernateSessionFactory;


/**   
*    
* 项目名称：AgentSys   
* 类名称：IndexController   
* 类描述：   
* 创建人：Edwin   
* 创建时间：2014-6-13 下午2:40:42   
* @version    
*    
*/
@Controller
@RequestMapping("/index.do")
public class IndexController {
	
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
		return new ModelAndView("index",map);
	}
	
	/**
	 * 按页获取商品信息
	 * @param response
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "action=getproduct")
	public ModelAndView getProdcut(HttpServletResponse response,int curPage) throws Exception {
		JsonView jsonView=new JsonView();
		AgCpProductDAO agCpProductDAO=new AgCpProductDAO();
		AgCpPackageDAO agCpPackageDao=new AgCpPackageDAO();
		List<Map<String,String>> productMapList=new ArrayList<Map<String,String>>();
		AgCpProduct agCpProduct=null;
		AgCpPackage agCpPackage=null;
		  Map<String,String> productInfo=null;
		List<AgCpProduct> agCpProductList =agCpProductDAO.findByPage(curPage, Constant.INDEX_PRODUCT_SIZE);
		  for(int    i=0;    i<agCpProductList.size();    i++)    {   
			  productInfo=new HashMap<String,String>();
			  agCpProduct  =   agCpProductList.get(i); 
			  agCpPackage=agCpPackageDao.findById(agCpProduct.getDefaultPackageId());
			  productInfo.put("id", agCpProduct.getId()+"");
			  productInfo.put("name", agCpProduct.getName());
			  productInfo.put("price", agCpPackage.getPrice()+"");
			  productInfo.put("img_url", agCpProduct.getImgUrl()+"");
			  productMapList.add(productInfo);
		   }
		  jsonView.setProperty("productData", productMapList);
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
		AgQxUser agQxUser = new AgQxUser();
		agQxUser.setAccount(registerVo.getPhone());
		agQxUser.setPsw(MD5Util.Encrypt(registerVo.getPsw()));
		agQxUser.setName(registerVo.getNick());
		agQxUser.setPhone(registerVo.getPhone());
		agQxUser.setRoleId(2);
		AgQxUserDAO agQxUserDAO = new AgQxUserDAO();
		if(agQxUserDAO.findByAccount(registerVo.getPhone()).size()>0||agQxUserDAO.findByName(registerVo.getNick()).size()>0){
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "名称或号码已存在，请重新检查!");
			return new ModelAndView(jsonView);
		}
		Transaction tr = HibernateSessionFactory.getSession().beginTransaction(); //开始事务  
		agQxUserDAO.save(agQxUser);
		 tr.commit();   //提交事务  
		HibernateSessionFactory.getSession().flush();
		jsonView.setProperty("isSuc", true);
		jsonView.setProperty("msg", "注册成功!");
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
		AgQxUserDAO agQxUserDAO = new AgQxUserDAO();
		List userList=agQxUserDAO.findByAccount(loginVo.getL_phone());
		if(userList.size()==0){
			jsonView.setProperty("isSuc", false);	
			jsonView.setProperty("msg", "账号或者密码错误!");
			return new ModelAndView(jsonView);
		}
		AgQxUser agQxUser =(AgQxUser)userList.get(0);
		//密码验证
		if(MD5Util.validPassword(loginVo.getL_psw(), agQxUser.getPsw())){
			UserSessionBean userSessionBean=new UserSessionBean();
			userSessionBean.setAccount(agQxUser.getAccount());
			userSessionBean.setName(agQxUser.getName());
			userSessionBean.setPhone(agQxUser.getPhone());
			request.getSession().setAttribute(Constant.USER_SESSION, userSessionBean);
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
		AgQxUserDAO agQxUserDAO = new AgQxUserDAO();
		if(agQxUserDAO.findByAccount(phone).size()>0){
			jsonView.setProperty("isExist", "1"); 
		}else{
			jsonView.setProperty("isExist", "0"); 
		}
		return new ModelAndView(jsonView);
	}
	
	
	
	public String testDeme(){
		return "test";
	}
	
}
