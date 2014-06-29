package com.edwin.agentsys.base.view;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.View;


/**
 * JSON视图，返回AJAX请求时后的JSON字符串
 * 
 */
public class JsonView implements View {

	private String msg;

	private boolean isSuc = true;

	/**
	 * 用于存储各属性和值
	 */
	private Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * 构造方法，若采用此构造方法，则返回的jsonstring不包含msg和success属性，用于非提示信息类型的JSON构造，例如下拉列表
	 */
	public JsonView(){
		
	}
	
	public JsonView(String msg) {
		this.msg = StringUtils.trimToEmpty(msg);
	}

	public JsonView(boolean isSuc, String msg) {
		this.msg = StringUtils.trimToEmpty(msg);
		this.isSuc = isSuc;
	}

	public String getContentType() {
		return "text/plain;charset=GBK";
	}

	/**
	 * 设置返回到客户端的json对象的属性，success和message属性不允许设置。
	 */
	public void setProperty(String key, Object value) {
		if ("sucess".equals(key) || "message".equals(key))
			return;
		map.put(key, value);
	}
	public Object getProperty(String key) {
		return map.get(key);
	}

	/**
	 * 使用json返回数据供前台js进行解析.
	 */
	public void render(Map arg0, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.write(this.getJSONString());
		out.close();
		out = null;
	}

	/**
	 * 获取json字符串
	 */
	private String getJSONString() {
		JSONObject obj = JSONObject.fromMap(this.map);
		if(msg != null){
			obj.put("isSuc", isSuc);
			obj.put("msg", msg);
			//此处为了兼容前端JS的代码
		}
		return obj.toString();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuc() {
		return isSuc;
	}

	public void setSuc(boolean isSuc) {
		this.isSuc = isSuc;
	}
	
	
	
}
