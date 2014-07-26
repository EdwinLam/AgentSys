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

import com.edwin.agentsys.index.bean.UserSessionBean;

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
		UserSessionBean userSessionBean=(UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION);
		String reqUrl = request.getRequestURI();
		String queryString = request.getQueryString();
		String url=reqUrl+"?"+queryString;
		boolean isHtmlResource=isHtmlResource(url);
		boolean isAjax=isAajaxUrl(url);
		if((queryString==null&&!isHtmlResource)||(userSessionBean==null&& !isFilterUrl(url)&&!isHtmlResource)){
			if(isAjax){
				// 返回JSON结果
				JSONObject result = new JSONObject();
				result.put("status", "403");
				result.put("isSuc", "false");
				result.put("msg", "您还没有登录!");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write(result.toString());
				out.close();
			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.do?action=init");
				dispatcher.forward(request, response);
			}
			return;
		}
		if(!isHtmlResource){
			String UserId="";
			LoggerUtil.info(" " + Util.getRequestIpAddr((HttpServletRequest) req) + "  " + req.getRemotePort() + " RequestBy: " + UserId + "  "
					+ reqUrl + "?" + queryString);
		}
		chain.doFilter(request, response);
	}
	
	boolean isHtmlResource(String reqUrl) {
		if (reqUrl == null)
			return false;
		if ("/".equals(reqUrl))
			return true;
		reqUrl = reqUrl.toLowerCase();
		if (reqUrl.indexOf(".ico") >= 0 || reqUrl.indexOf(".jpg") >= 0 || reqUrl.indexOf(".gif") >= 0 || reqUrl.indexOf(".png") >= 0
				|| reqUrl.indexOf(".swf") >= 0) {
			return true;
		}
		if (reqUrl.indexOf(".htm") >= 0 || reqUrl.indexOf(".html") >= 0 || reqUrl.indexOf(".css") >= 0 || reqUrl.indexOf(".js") >= 0) {
			return true;
		}
		if (reqUrl.indexOf(".doc") >= 0 || reqUrl.indexOf(".xls") >= 0 || reqUrl.indexOf(".ppt") >= 0 || reqUrl.indexOf(".docx") >= 0
				|| reqUrl.indexOf(".xlsx") >= 0) {
			return true;
		}
		if (reqUrl.indexOf(".apk") >= 0 || reqUrl.indexOf(".ipa") >= 0 || reqUrl.indexOf(".plist") >= 0 ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 不检查session的url
	 * @param url
	 * @return
	 */
	private boolean isFilterUrl(String url){
		url=url.substring(url.indexOf("/"));
		boolean isFilter=false;
		if(url.indexOf("index.do")!=-1){
			isFilter=true;
		}
		return isFilter;
	}
	
	/**
	 * 是否ajaxUrl
	 * @param url
	 * @return
	 */
	private boolean isAajaxUrl(String url){
		url=url.substring(url.indexOf("/"));
		boolean isAjaxUrl=false;
		if(url.indexOf("_ajaxreq")!=-1
			){
			isAjaxUrl=true;
		}
		return isAjaxUrl;
	}
	

	/**
	 * 
	 * 功能描述：处理无登录状态的请求
	 */
	void dealLoginSeesionDisableState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/noLoginState.htm");
			dispatcher.forward(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
