/** 
 * 
 * @author geloin 
 * @date 2012-5-5 上午9:31:52 
 */
package com.geloin.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.bytecode.stackmap.TypeData.ClassName;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.geloin.spring.entity.Menu;
import com.geloin.spring.service.MenuService;
import org.apache.log4j.Logger;

/**
 * 
 * @author geloin
 * @date 2012-5-5 上午9:31:52
 */
@Controller
@RequestMapping("/test.do")
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class);
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午9:33:22
	 * @return
	 */
	@RequestMapping(params = "action=index")
	public ModelAndView toLogin(HttpServletResponse response) throws Exception {
		logger.info("用户登录测试:");
		Map<String, Object> map = new HashMap<String, Object>();

//		List<Menu> result = this.menuService.find();

		map.put("result", null);

		return new ModelAndView("background/index", map);
	}
}