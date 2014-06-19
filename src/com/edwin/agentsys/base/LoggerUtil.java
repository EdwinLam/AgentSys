package com.edwin.agentsys.base;

import org.apache.log4j.Logger;

public class LoggerUtil {
	static Logger logger = Logger.getLogger(LoggerUtil.class);
	
	/**
	 * 普通日志
	 * @param message
	 */
	public static void info(String message) {
		logger.info(message);
	}
	
	/**
	 * 错误日志
	 * @param message
	 * @param t
	 */
	public static void error(String message, Throwable t) {
		logger.error(message, t);
	}

	/**
	 * 方法：
	 * @param message
	 */
	public static void debug(String message) {
		logger.debug(message);
	}
}
