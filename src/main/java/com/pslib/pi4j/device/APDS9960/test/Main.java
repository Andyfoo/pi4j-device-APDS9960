package com.pslib.pi4j.device.APDS9960.test;

public class Main {

	public static void main(String[] args) throws Exception {
		String type = args!=null&&args.length>0?args[0]:"";
		switch(type) {
		case "ambient":
			TestAmbient.main(args);
			break;
		case "color":
			TestColor.main(args);
			break;
		case "gesture":
			TestGesture.main(args);
			break;
		case "prox":
			TestProx.main(args);
			break;
			default:
				System.out.println("java -jar APDS9960.jar ambient|color|gesture|prox");
		}
	}

}
