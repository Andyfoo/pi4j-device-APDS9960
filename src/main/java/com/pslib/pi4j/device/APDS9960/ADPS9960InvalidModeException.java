package com.pslib.pi4j.device.APDS9960;

public class ADPS9960InvalidModeException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ADPS9960InvalidModeException(int mode) {
		super(String.format("invalid mode [%s]", mode));
	}

	public ADPS9960InvalidModeException(String msg) {
		super(msg);
	}
}
