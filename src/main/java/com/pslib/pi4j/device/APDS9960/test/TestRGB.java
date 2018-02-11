package com.pslib.pi4j.device.APDS9960.test;

import com.pslib.pi4j.device.APDS9960.APDS9960;

public class TestRGB {

	public static void main(String[] args) throws Exception {
		APDS9960 apds = new APDS9960();
		try {
			apds.enableLightSensor();
			int red_oval = -1;
			int green_oval = -1;
			int blue_oval = -1;
			while (true) {
				apds.sleep(250);
				int red_val = apds.readRedLight();
				int green_val = apds.readGreenLight();
				int blue_val = apds.readBlueLight();
				if (red_val != red_oval || green_val != green_oval || blue_val != blue_oval) {
					System.out.println(String.format("RGB Light=%s, %s, %s", red_val, green_val, blue_val));
					red_oval = red_val;
					green_oval = green_val;
					blue_oval = blue_val;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
