package com.pslib.pi4j.device.APDS9960;

public class ADPS9960InvalidDevId extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ADPS9960InvalidDevId(int dev_id, int[] valid_id) {
		super();
	}

	public ADPS9960InvalidDevId(String msg) {
		super(msg);
	}
}