package com.edwin.agentsys.base;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

public class ActionFilter implements Filter {

	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String reqUrl = request.getRequestURI();
		String queryString = request.getQueryString();
		String UserId="";
		LoggerUtil.info(" " + Util.getRequestIpAddr((HttpServletRequest) req) + "  " + req.getRemotePort() + " RequestBy: " + UserId + "  "
				+ reqUrl + "?" + queryString);
	}
	
	/**
	 * 
	 * 功能描述：判断是否用户是异步请求的
	 */
	boolean isAjaxRequest(HttpServletRequest request) {
		String curReqUrl = request.getRequestURI();
		String queryString = request.getQueryString();
		System.out.println("isAjaxRequest:" + curReqUrl + "?" + queryString);
		String reqUrl = curReqUrl + "?" + queryString;
		return reqUrl.indexOf("_ajax=true") > 0;
	}
	
	/**
	 * 
	 * 功能描述：处理无登录状态的请求
	 */
	void dealLoginSeesionDisableState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isAjaxReq = isAjaxRequest(request);
		if (!isAjaxReq) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/noLoginState.htm");
			dispatcher.forward(request, response);
		} else {
			// 返回JSON结果
			JSONObject result = new JSONObject();
			result.put("user_permission_check", "on");
			result.put("user_session_rebuild_state", "-1");
			result.put("user_login_state", "-1");
			result.put("user_login_msg", "用户闲置过久，会话已过期。");
			PrintWriter out = response.getWriter();
			out.write(result.toString());
			out.close();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
