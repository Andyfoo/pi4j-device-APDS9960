package com.pslib.pi4j.device.APDS9960.test;

import com.pslib.pi4j.device.APDS9960.APDS9960;

public class TestAmbient {

	public static void main(String[] args) throws Exception {
		APDS9960 apds = new APDS9960();
		try {
			apds.enableLightSensor();
			int oval = -1;
			while (true) {
				apds.sleep(250);
				int val = apds.readAmbientLight();
				if (val != oval) {
					System.out.println(String.format("AmbientLight=%s", val));
					oval = val;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
