package com.pslib.pi4j.device.APDS9960.test;

import java.util.HashMap;
import java.util.Map;

import com.pslib.pi4j.device.APDS9960.APDS9960;

public class TestGesture {
	static Map<Integer, String> dirs = new HashMap<Integer, String>();
	static {
		dirs.put(APDS9960.APDS9960_DIR_NONE, "none");
		dirs.put(APDS9960.APDS9960_DIR_LEFT, "left");
		dirs.put(APDS9960.APDS9960_DIR_RIGHT, "right");
		dirs.put(APDS9960.APDS9960_DIR_UP, "up");
		dirs.put(APDS9960.APDS9960_DIR_DOWN, "down");
		dirs.put(APDS9960.APDS9960_DIR_NEAR, "near");
		dirs.put(APDS9960.APDS9960_DIR_FAR, "far");
	}

	public static void main(String[] args) throws Exception {

		APDS9960 apds = new APDS9960();
		try {
			apds.setProximityIntLowThreshold(50);
			apds.enableGestureSensor();
			while (true) {
				apds.sleep(500);
				if (apds.isGestureAvailable()) {
					int motion = apds.readGesture();
					System.out.println(String.format("Gesture=%s, %s", motion, dirs.getOrDefault(motion, "unknown")));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
