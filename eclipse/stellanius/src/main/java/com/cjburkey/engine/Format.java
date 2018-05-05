package com.cjburkey.engine;

public class Format {
	
	public static String format(double decimal, int places) {
		return String.format("%." + ((places < 1) ? 1 : places) + "d", decimal);
	}
	
	public static String format1(double decimal) {
		return format(decimal, 1);
	}
	
	public static String format2(double decimal) {
		return format(decimal, 1);
	}
	
	public static String format3(double decimal) {
		return format(decimal, 1);
	}
	
	public static String format4(double decimal) {
		return format(decimal, 1);
	}
	
}