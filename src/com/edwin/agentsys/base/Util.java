package com.edwin.agentsys.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class Util {
	/**
	 * 获取用户IP
	 */
   public static String getRequestIpAddr(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		     ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		     ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		     ip = request.getRemoteAddr();
		}
		return ip;
    }
   
   /**
    * 返回16+6位订单号
 * @return
 */
public static String getOrderNo(){
	  Random rd = new Random();
	  String n="";
	  int getNum;
	  do {
	   getNum = Math.abs(rd.nextInt())%10 + 48;//产生数字0-9的随机数
	   char num1 = (char)getNum;
	   String dn = Character.toString(num1);
	   n += dn;
	  } while (n.length()<6);
	
	   String orderNo="";
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	   orderNo=format.format(new Date());
	   return orderNo+n;
	   
   }
   
   public static void main(String[] args){
	   System.out.println(getOrderNo());
   }
}
