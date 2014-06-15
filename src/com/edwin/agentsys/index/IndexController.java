package com.edwin.agentsys.index;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.model.QxUser;
import com.edwin.agentsys.model.QxUserDAO;
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
	@RequestMapping(params = "action=init")
	public ModelAndView index(HttpServletResponse response) throws Exception {
		logger.info("首页初始化");
		Map<String, Object> map = new HashMap<String, Object>();
		QxUserDAO qxUserDao=new QxUserDAO();
		QxUser qxUser = qxUserDao.findById(1);
		map.put("result", qxUser.getAccount());
		qxUser.setAccount("test2");
		qxUser.setName("小明");
		QxUser qxUser2 = new QxUser();
		qxUser2.setAccount("gg");
		qxUser2.setName("ggg");
		Transaction tr = HibernateSessionFactory.getSession().beginTransaction(); //开始事务  
		qxUserDao.save(qxUser);
		qxUserDao.save(qxUser2);
		 tr.commit();   //提交事务  
		HibernateSessionFactory.getSession().flush();
		return new ModelAndView("index",map);
	}
	
	public String testDeme(){
		return "test";
	}
	
}
