package com.edwin.agentsys.index;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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
		return new ModelAndView("index");
	}
	
	public String testDeme(){
		return "test";
	}
	
}
