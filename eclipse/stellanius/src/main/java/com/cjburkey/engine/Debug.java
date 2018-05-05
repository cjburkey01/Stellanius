package com.cjburkey.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Debug {
	
	public static final Logger logger = LogManager.getLogger();
	
	public static void log(Object msg) {
		logger.info(sanitize(msg));
	}
	
	public static void log(Object msg, Object... param) {
		logger.info(sanitize(msg), param);
	}
	
	public static void warn(Object msg) {
		logger.warn(sanitize(msg));
	}
	
	public static void warn(Object msg, Object... arg) {
		logger.warn(sanitize(msg), arg);
	}
	
	public static void error(Object msg) {
		logger.error(sanitize(msg));
	}
	
	public static void error(Object msg, Object... arg) {
		logger.error(sanitize(msg), arg);
	}
	
	private static String sanitize(Object msg) {
		String str = (msg == null) ? "null" : msg.toString();
		return (str == null) ? "null" : str;
	}
	
}