package com.edwin.agentsys.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.index.vo.RegisterVo;
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
//		AgCpProductDAO agCpProductDAO=new AgCpProductDAO();
//		List<AgCpProductDAO> agCpProduct =agCpProductDAO.findAll();
//		map.put("agCpProduct", agCpProduct);
		map.put("test", "这是测试");
//		Transaction tr = HibernateSessionFactory.getSession().beginTransaction(); //开始事务  
//		 tr.commit();   //提交事务  
//		HibernateSessionFactory.getSession().flush();
		return new ModelAndView("index",map);
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
