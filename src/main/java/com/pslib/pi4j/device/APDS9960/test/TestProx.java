package com.pslib.pi4j.device.APDS9960.test;

import com.pslib.pi4j.device.APDS9960.APDS9960;

public class TestProx {
	public static void main(String[] args) throws Exception {

		APDS9960 apds = new APDS9960();
		try {
			apds.setProximityIntLowThreshold(50);
			apds.enableProximitySensor();
			int oval = -1;
			while (true) {
				apds.sleep(250);
				int val = apds.readProximity();
				if (val != oval) {
					System.out.println(String.format("proximity=%s", val));
					oval = val;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
