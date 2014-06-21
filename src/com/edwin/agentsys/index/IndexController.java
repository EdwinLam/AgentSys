package com.edwin.agentsys.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.ProductShowBean;
import com.edwin.agentsys.index.vo.RegisterVo;
import com.edwin.agentsys.model.AgCpPackage;
import com.edwin.agentsys.model.AgCpPackageDAO;
import com.edwin.agentsys.model.AgCpProduct;
import com.edwin.agentsys.model.AgCpProductDAO;
import com.edwin.agentsys.model.AgQxUser;
import com.edwin.agentsys.model.AgQxUserDAO;
import com.edwin.agentsys.test.HibernateSessionFactory;
import com.geloin.spring.controller.LoginController;


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
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	/**
	 * 首页界面
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "action=init")
	public ModelAndView index(HttpServletResponse response) throws Exception {
		logger.info("首页初始化");
		Map<String, Object> map = new HashMap<String, Object>();
		AgCpProductDAO agCpProductDAO=new AgCpProductDAO();
		AgCpPackageDAO agCpPackageDao=new AgCpPackageDAO();
		List<ProductShowBean> productBeanList=new ArrayList<ProductShowBean>();
		ProductShowBean productBean=null;
		AgCpProduct agCpProduct=null;
		AgCpPackage agCpPackage=null;
		List<AgCpProduct> agCpProductList =agCpProductDAO.findByPage(1, Constant.INDEX_PRODUCT_SIZE);
		  for(int    i=0;    i<agCpProductList.size();    i++)    {   
			  productBean=new ProductShowBean();
			  agCpProduct  =   agCpProductList.get(i); 
			  productBean.setTitle(agCpProduct.getName());
			  productBean.setProductId(agCpProduct.getId());
			  productBean.setImg_url(agCpProduct.getImgUrl());
			  agCpPackage=agCpPackageDao.findById(agCpProduct.getDefaultPackageId());
			  productBean.setPrice(agCpPackage.getPrice());
			  productBeanList.add(productBean);
		   }   
		map.put("productBeanList", productBeanList);
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
		logger.info("注册页面初始化");
		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("register",map);
	}
	
	@RequestMapping(params = "action=register")
	public ModelAndView register(RegisterVo registerVo) throws Exception {
		logger.info("注册页面初始化");
		AgQxUser agQxUser = new AgQxUser();
		agQxUser.setAccount(registerVo.getAccount());
		agQxUser.setPsw(registerVo.getPsw());
		agQxUser.setName(registerVo.getName());
		agQxUser.setRoleId(2);
		AgQxUserDAO agQxUserDAO = new AgQxUserDAO();
		Transaction tr = HibernateSessionFactory.getSession().beginTransaction(); //开始事务  
		agQxUserDAO.save(agQxUser);
		 tr.commit();   //提交事务  
		HibernateSessionFactory.getSession().flush();
		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("index",map);
	}
	
	public String testDeme(){
		return "test";
	}
	
}
