package com.pslib.pi4j.device.APDS9960;

public class ADPS9960InvalidDevIdException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ADPS9960InvalidDevIdException(int dev_id, int[] valid_id) {
		super(String.format("invalid device id[<%s> not in {%s}]", dev_id, join(valid_id, ", ")));
	}

	public ADPS9960InvalidDevIdException(String msg) {
		super(msg);
	}
	
	public static String join(int[] arr, String split) {
		String str = "";
		if(arr!=null&&arr.length>0) {
			for(int v : arr) {
				str += v+split;
			}
			str = str.substring(0, str.length() - split.length());
		}
		return str;
	}
}