package com.edwin.agentsys.index;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.Constant;
import com.edwin.agentsys.base.view.JsonView;
import com.edwin.agentsys.index.bean.UserSessionBean;
import com.edwin.agentsys.model.AgQxUser;
import com.edwin.agentsys.model.AgQxUserDAO;
import com.edwin.agentsys.test.HibernateSessionFactory;

@Controller
@RequestMapping("/user.do")
public class UserController {
	@Autowired
	AgQxUserDAO agQxUserDAO;
	@RequestMapping(params = "action=mAddress_ajaxreq")
	public ModelAndView mAddress(HttpServletRequest request,String address) throws Exception {
		JsonView jsonView=new JsonView();
		UserSessionBean userSessionBean=(UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION);
		userSessionBean.setAddress(address);
		request.getSession().setAttribute(Constant.USER_SESSION, userSessionBean);
		AgQxUser agQxUser = agQxUserDAO.findById(userSessionBean.getId());
		agQxUser.setAddress(address);
		agQxUserDAO.save(agQxUser);
		HibernateSessionFactory.getSession().flush();
		jsonView.setProperty("isSuc", true);
		jsonView.setProperty("msg", "修改成功!");
		return new ModelAndView(jsonView);
	}
}
