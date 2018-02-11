# pi4j-device-APDS9960
APDS-9960 Digital Proximity, Ambient Light, RGB and Gesture Sensor

Converted from python to Java.(https://github.com/liske/python-apds9960)
Features:
- operational voltage: 3.3V
- ambient light & RGB color sensing
- proximity sensing
- gesture detection
- operating range: 10 - 20cm
- I²C interface (hard wired I²C address: 0x39)


**test:**

    git clone https://github.com/Andyfoo/pi4j-device-APDS9960.git
    cd pi4j-device-APDS9960
    mvc package
    java -jar target/APDS9960.jar ambient|color|gesture|prox
    