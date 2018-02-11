package com.pslib.pi4j.device.APDS9960;

public class ADPS9960InvalidMode  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ADPS9960InvalidMode(int mode) {
		super();
	}

	public ADPS9960InvalidMode(String msg) {
		super(msg);
	}
}
