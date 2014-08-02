package com.edwin.agentsys.index;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.model.User;
import com.edwin.agentsys.service.UserService;

@Controller
@RequestMapping("/user.do")
public class UserController {
	@Resource(name="userService")
	UserService userService;
	@RequestMapping(params = "action=mAddress_ajaxreq")
	public ModelAndView mAddress(HttpServletRequest request,String address) throws Exception {
		JsonView jsonView=new JsonView();
		UserSessionBean userSessionBean=(UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION);
		userSessionBean.setAddress(address);
		request.getSession().setAttribute(Constant.USER_SESSION, userSessionBean);
		User user = userService.findById(userSessionBean.getId());
		user.setAddress(address);
		userService.updateUser(user);
		jsonView.setProperty("isSuc", true);
		jsonView.setProperty("msg", "修改成功!");
		return new ModelAndView(jsonView);
	}
}
