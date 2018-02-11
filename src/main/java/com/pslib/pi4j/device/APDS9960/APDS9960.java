package com.pslib.pi4j.device.APDS9960;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;

/**
 * APDS-9960 Digital Proximity, Ambient Light, RGB and Gesture Sensor
 * Converted from python to Java.(https://github.com/liske/python-apds9960)
 * 
 * @author Andyfoo
 * https://github.com/Andyfoo/pi4j-device-APDS9960
 * http://www.andyfoo.com
 * http://www.pslib.com
 *
 */
public class APDS9960 {
	// APDS9960 i2c address
	public static final int APDS9960_I2C_ADDR = 0x39;

	// APDS9960 gesture parameters
	public static final int APDS9960_GESTURE_THRESHOLD_OUT = 10;
	public static final int APDS9960_GESTURE_SENSITIVITY_1 = 50;
	public static final int APDS9960_GESTURE_SENSITIVITY_2 = 20;

	// APDS9960 device IDs
	public static final int[] APDS9960_DEV_ID = new int[] { 0xab, 0x9c, 0xa8 };

	// APDS9960 times
	public static final int APDS9960_TIME_FIFO_PAUSE = 30;

	// APDS9960 register addresses
	public static final int APDS9960_REG_ENABLE = 0x80;
	public static final int APDS9960_REG_ATIME = 0x81;
	public static final int APDS9960_REG_WTIME = 0x83;
	public static final int APDS9960_REG_AILTL = 0x84;
	public static final int APDS9960_REG_AILTH = 0x85;
	public static final int APDS9960_REG_AIHTL = 0x86;
	public static final int APDS9960_REG_AIHTH = 0x87;
	public static final int APDS9960_REG_PILT = 0x89;
	public static final int APDS9960_REG_PIHT = 0x8b;
	public static final int APDS9960_REG_PERS = 0x8c;
	public static final int APDS9960_REG_CONFIG1 = 0x8d;
	public static final int APDS9960_REG_PPULSE = 0x8e;
	public static final int APDS9960_REG_CONTROL = 0x8f;
	public static final int APDS9960_REG_CONFIG2 = 0x90;
	public static final int APDS9960_REG_ID = 0x92;
	public static final int APDS9960_REG_STATUS = 0x93;
	public static final int APDS9960_REG_CDATAL = 0x94;
	public static final int APDS9960_REG_CDATAH = 0x95;
	public static final int APDS9960_REG_RDATAL = 0x96;
	public static final int APDS9960_REG_RDATAH = 0x97;
	public static final int APDS9960_REG_GDATAL = 0x98;
	public static final int APDS9960_REG_GDATAH = 0x99;
	public static final int APDS9960_REG_BDATAL = 0x9a;
	public static final int APDS9960_REG_BDATAH = 0x9b;
	public static final int APDS9960_REG_PDATA = 0x9c;
	public static final int APDS9960_REG_POFFSET_UR = 0x9d;
	public static final int APDS9960_REG_POFFSET_DL = 0x9e;
	public static final int APDS9960_REG_CONFIG3 = 0x9f;
	public static final int APDS9960_REG_GPENTH = 0xa0;
	public static final int APDS9960_REG_GEXTH = 0xa1;
	public static final int APDS9960_REG_GCONF1 = 0xa2;
	public static final int APDS9960_REG_GCONF2 = 0xa3;
	public static final int APDS9960_REG_GOFFSET_U = 0xa4;
	public static final int APDS9960_REG_GOFFSET_D = 0xa5;
	public static final int APDS9960_REG_GOFFSET_L = 0xa7;
	public static final int APDS9960_REG_GOFFSET_R = 0xa9;
	public static final int APDS9960_REG_GPULSE = 0xa6;
	public static final int APDS9960_REG_GCONF3 = 0xaA;
	public static final int APDS9960_REG_GCONF4 = 0xaB;
	public static final int APDS9960_REG_GFLVL = 0xae;
	public static final int APDS9960_REG_GSTATUS = 0xaf;
	public static final int APDS9960_REG_IFORCE = 0xe4;
	public static final int APDS9960_REG_PICLEAR = 0xe5;
	public static final int APDS9960_REG_CICLEAR = 0xe6;
	public static final int APDS9960_REG_AICLEAR = 0xe7;
	public static final int APDS9960_REG_GFIFO_U = 0xfc;
	public static final int APDS9960_REG_GFIFO_D = 0xfd;
	public static final int APDS9960_REG_GFIFO_L = 0xfe;
	public static final int APDS9960_REG_GFIFO_R = 0xff;

	// APDS9960 bit fields
	public static final int APDS9960_BIT_PON = 0b00000001;
	public static final int APDS9960_BIT_AEN = 0b00000010;
	public static final int APDS9960_BIT_PEN = 0b00000100;
	public static final int APDS9960_BIT_WEN = 0b00001000;
	public static final int APSD9960_BIT_AIEN = 0b00010000;
	public static final int APDS9960_BIT_PIEN = 0b00100000;
	public static final int APDS9960_BIT_GEN = 0b01000000;
	public static final int APDS9960_BIT_GVALID = 0b00000001;

	// APDS9960 modes
	public static final int APDS9960_MODE_POWER = 0;
	public static final int APDS9960_MODE_AMBIENT_LIGHT = 1;
	public static final int APDS9960_MODE_PROXIMITY = 2;
	public static final int APDS9960_MODE_WAIT = 3;
	public static final int APDS9960_MODE_AMBIENT_LIGHT_INT = 4;
	public static final int APDS9960_MODE_PROXIMITY_INT = 5;
	public static final int APDS9960_MODE_GESTURE = 6;
	public static final int APDS9960_MODE_ALL = 7;

	// LED Drive values
	public static final int APDS9960_LED_DRIVE_100MA = 0;
	public static final int APDS9960_LED_DRIVE_50MA = 1;
	public static final int APDS9960_LED_DRIVE_25MA = 2;
	public static final int APDS9960_LED_DRIVE_12_5MA = 3;

	// Proximity Gain (PGAIN) values
	public static final int APDS9960_PGAIN_1X = 0;
	public static final int APDS9960_PGAIN_2X = 1;
	public static final int APDS9960_PGAIN_4X = 2;
	public static final int APDS9960_PGAIN_8X = 3;

	// ALS Gain (AGAIN) values
	public static final int APDS9960_AGAIN_1X = 0;
	public static final int APDS9960_AGAIN_4X = 1;
	public static final int APDS9960_AGAIN_16X = 2;
	public static final int APDS9960_AGAIN_64X = 3;

	// Gesture Gain (GGAIN) values
	public static final int APDS9960_GGAIN_1X = 0;
	public static final int APDS9960_GGAIN_2X = 1;
	public static final int APDS9960_GGAIN_4X = 2;
	public static final int APDS9960_GGAIN_8X = 3;

	// LED Boost values
	public static final int APDS9960_LED_BOOST_100 = 0;
	public static final int APDS9960_LED_BOOST_150 = 1;
	public static final int APDS9960_LED_BOOST_200 = 2;
	public static final int APDS9960_LED_BOOST_300 = 3;

	// Gesture wait time values
	public static final int APDS9960_GWTIME_0MS = 0;
	public static final int APDS9960_GWTIME_2_8MS = 1;
	public static final int APDS9960_GWTIME_5_6MS = 2;
	public static final int APDS9960_GWTIME_8_4MS = 3;
	public static final int APDS9960_GWTIME_14_0MS = 4;
	public static final int APDS9960_GWTIME_22_4MS = 5;
	public static final int APDS9960_GWTIME_30_8MS = 6;
	public static final int APDS9960_GWTIME_39_2MS = 7;

	// Default values
	public static final int APDS9960_DEFAULT_ATIME = 219; // 103ms
	public static final int APDS9960_DEFAULT_WTIME = 246; // 27ms
	public static final int APDS9960_DEFAULT_PROX_PPULSE = 0x87; // 16us, 8 pulses
	public static final int APDS9960_DEFAULT_GESTURE_PPULSE = 0x89; // 16us, 10 pulses
	public static final int APDS9960_DEFAULT_POFFSET_UR = 0; // 0 offset
	public static final int APDS9960_DEFAULT_POFFSET_DL = 0; // 0 offset
	public static final int APDS9960_DEFAULT_CONFIG1 = 0x60; // No 12x wait (WTIME) factor
	public static final int APDS9960_DEFAULT_LDRIVE = APDS9960_LED_DRIVE_100MA;
	public static final int APDS9960_DEFAULT_PGAIN = APDS9960_PGAIN_4X;
	public static final int APDS9960_DEFAULT_AGAIN = APDS9960_AGAIN_4X;
	public static final int APDS9960_DEFAULT_PILT = 0; // Low proximity threshold
	public static final int APDS9960_DEFAULT_PIHT = 50; // High proximity threshold
	public static final int APDS9960_DEFAULT_AILT = 0xffff; // Force interrupt for calibration
	public static final int APDS9960_DEFAULT_AIHT = 0;
	public static final int APDS9960_DEFAULT_PERS = 0x11; // 2 consecutive prox or ALS for int.
	public static final int APDS9960_DEFAULT_CONFIG2 = 0x01; // No saturation interrupts or LED boost
	public static final int APDS9960_DEFAULT_CONFIG3 = 0; // Enable all photodiodes, no SAI
	public static final int APDS9960_DEFAULT_GPENTH = 40; // Threshold for entering gesture mode
	public static final int APDS9960_DEFAULT_GEXTH = 30; // Threshold for exiting gesture mode
	public static final int APDS9960_DEFAULT_GCONF1 = 0x40; // 4 gesture events for int., 1 for exit
	public static final int APDS9960_DEFAULT_GGAIN = APDS9960_GGAIN_4X;
	public static final int APDS9960_DEFAULT_GLDRIVE = APDS9960_LED_DRIVE_100MA;
	public static final int APDS9960_DEFAULT_GWTIME = APDS9960_GWTIME_2_8MS;
	public static final int APDS9960_DEFAULT_GOFFSET = 0; // No offset scaling for gesture mode
	public static final int APDS9960_DEFAULT_GPULSE = 0xc9; // 32us, 10 pulses
	public static final int APDS9960_DEFAULT_GCONF3 = 0; // All photodiodes active during gesture
	public static final boolean APDS9960_DEFAULT_GIEN = false; // Disable gesture interrupts

	// gesture directions
	public static final int APDS9960_DIR_NONE = 0;
	public static final int APDS9960_DIR_LEFT = 1;
	public static final int APDS9960_DIR_RIGHT = 2;
	public static final int APDS9960_DIR_UP = 3;
	public static final int APDS9960_DIR_DOWN = 4;
	public static final int APDS9960_DIR_NEAR = 5;
	public static final int APDS9960_DIR_FAR = 6;
	public static final int APDS9960_DIR_ALL = 7;

	// state definitions
	public static final int APDS9960_STATE_NA = 0;
	public static final int APDS9960_STATE_NEAR = 1;
	public static final int APDS9960_STATE_FAR = 2;
	public static final int APDS9960_STATE_ALL = 3;

	I2CDevice device;
	I2CBus bus;

	int address;
	int[] valid_id;
	int gesture_ud_delta_, gesture_lr_delta_, gesture_ud_count_, gesture_lr_count_, gesture_near_count_, gesture_far_count_, gesture_state_, gesture_motion_;
	GestureData gesture_data_;
	int dev_id;

	class GestureData {
		public int[] u_data = new int[32];
		public int[] d_data = new int[32];
		public int[] l_data = new int[32];
		public int[] r_data = new int[32];
		public int index = 0;
		public int total_gestures = 0;
		public int in_threshold = 0;
		public int out_threshold = 0;
	}

	public APDS9960() throws Exception {
		this(-1, -1, null);
	}

	public APDS9960(int busPort, int address, int[] valid_id) throws Exception {
		// I2C stuff
		this.address = address == -1 ? APDS9960_I2C_ADDR : address;
		this.valid_id = valid_id == null ? APDS9960_DEV_ID : valid_id;

		bus = I2CFactory.getInstance(busPort == -1 ? I2CBus.BUS_1 : busPort);
		device = bus.getDevice(this.address);

		// instance variables for gesture detection
		this.gesture_ud_delta_ = 0;
		this.gesture_lr_delta_ = 0;

		this.gesture_ud_count_ = 0;
		this.gesture_lr_count_ = 0;

		this.gesture_near_count_ = 0;
		this.gesture_far_count_ = 0;

		this.gesture_state_ = 0;
		this.gesture_motion_ = APDS9960_DIR_NONE;

		this.gesture_data_ = new GestureData();

		// check device id
		this.dev_id = _read_byte_data(APDS9960_REG_ID);
		if (!contains(this.valid_id, this.dev_id)) {
			throw new ADPS9960InvalidDevIdException(this.dev_id, this.valid_id);
		}
		// disable all features
		setMode(APDS9960_MODE_ALL, false);
		// set default values for ambient light and proximity registers
		_write_byte_data(APDS9960_REG_ATIME, APDS9960_DEFAULT_ATIME);
		_write_byte_data(APDS9960_REG_WTIME, APDS9960_DEFAULT_WTIME);
		_write_byte_data(APDS9960_REG_PPULSE, APDS9960_DEFAULT_PROX_PPULSE);
		_write_byte_data(APDS9960_REG_POFFSET_UR, APDS9960_DEFAULT_POFFSET_UR);
		_write_byte_data(APDS9960_REG_POFFSET_DL, APDS9960_DEFAULT_POFFSET_DL);
		_write_byte_data(APDS9960_REG_CONFIG1, APDS9960_DEFAULT_CONFIG1);

		setLEDDrive(APDS9960_DEFAULT_LDRIVE);
		setProximityGain(APDS9960_DEFAULT_PGAIN);
		setAmbientLightGain(APDS9960_DEFAULT_AGAIN);
		setProxIntLowThresh(APDS9960_DEFAULT_PILT);
		setProxIntHighThresh(APDS9960_DEFAULT_PIHT);
		setLightIntLowThreshold(APDS9960_DEFAULT_AILT);
		setLightIntHighThreshold(APDS9960_DEFAULT_AIHT);

		_write_byte_data(APDS9960_REG_PERS, APDS9960_DEFAULT_PERS);
		_write_byte_data(APDS9960_REG_CONFIG2, APDS9960_DEFAULT_CONFIG2);
		_write_byte_data(APDS9960_REG_CONFIG3, APDS9960_DEFAULT_CONFIG3);

		// set default values for gesture sense registers
		setGestureEnterThresh(APDS9960_DEFAULT_GPENTH);
		setGestureExitThresh(APDS9960_DEFAULT_GEXTH);
		_write_byte_data(APDS9960_REG_GCONF1, APDS9960_DEFAULT_GCONF1);

		setGestureGain(APDS9960_DEFAULT_GGAIN);
		setGestureLEDDrive(APDS9960_DEFAULT_GLDRIVE);
		setGestureWaitTime(APDS9960_DEFAULT_GWTIME);
		_write_byte_data(APDS9960_REG_GOFFSET_U, APDS9960_DEFAULT_GOFFSET);
		_write_byte_data(APDS9960_REG_GOFFSET_D, APDS9960_DEFAULT_GOFFSET);
		_write_byte_data(APDS9960_REG_GOFFSET_L, APDS9960_DEFAULT_GOFFSET);
		_write_byte_data(APDS9960_REG_GOFFSET_R, APDS9960_DEFAULT_GOFFSET);
		_write_byte_data(APDS9960_REG_GPULSE, APDS9960_DEFAULT_GPULSE);
		_write_byte_data(APDS9960_REG_GCONF3, APDS9960_DEFAULT_GCONF3);
		setGestureIntEnable(APDS9960_DEFAULT_GIEN);
	}
	public static boolean contains(int[] array, int value) {
		return indexOf(array, value) != -1;
	}
	public static int indexOf(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	public int getMode() throws IOException {
		return _read_byte_data(APDS9960_REG_ENABLE);
	}

	public void setMode(int mode, boolean enable) throws Exception {
		// read ENABLE register
		int reg_val = getMode();
		if (mode < 0 || mode > APDS9960_MODE_ALL) {
			throw new ADPS9960InvalidModeException(mode);
		}
		// change bit(s) in ENABLE register
		if (mode == APDS9960_MODE_ALL) {
			reg_val = enable ? 0x7f : 0x00;
		} else {
			if (enable) {
				reg_val |= 1 << mode;
			} else {
				reg_val &= ~(1 << mode);
			}
		}
		// write value to ENABLE register
		_write_byte_data(APDS9960_REG_ENABLE, reg_val);
	}

	// start the light (R/G/B/Ambient) sensor
	// enableLightSensor(self, interrupts=True):
	public void enableLightSensor(boolean interrupts) throws Exception {
		setAmbientLightGain(APDS9960_DEFAULT_AGAIN);
		setAmbientLightIntEnable(interrupts);
		enablePower();
		setMode(APDS9960_MODE_AMBIENT_LIGHT, true);
	}
	public void enableLightSensor() throws Exception {
		enableLightSensor(true);
	}

	// stop the light sensor
	// disableLightSensor(self):
	public void disableLightSensor() throws Exception {
		setAmbientLightIntEnable(false);
		setMode(APDS9960_MODE_AMBIENT_LIGHT, false);
	}

	// start the proximity sensor
	// enableProximitySensor(self, interrupts=True):
	public void enableProximitySensor(boolean interrupts) throws Exception {
		setProximityGain(APDS9960_DEFAULT_PGAIN);
		setLEDDrive(APDS9960_DEFAULT_LDRIVE);
		setProximityIntEnable(interrupts);
		enablePower();
		setMode(APDS9960_MODE_PROXIMITY, true);
	}
	public void enableProximitySensor() throws Exception {
		enableProximitySensor(true);
	}

	// stop the proximity sensor
	// disableProximitySensor(self):
	public void disableProximitySensor() throws Exception {
		setProximityIntEnable(false);
		setMode(APDS9960_MODE_PROXIMITY, false);
	}

	// start the gesture recognition engine
	// enableGestureSensor(self, interrupts=True):
	public void enableGestureSensor(boolean interrupts) throws Exception {
		resetGestureParameters();
		_write_byte_data(APDS9960_REG_WTIME, 0xff);
		_write_byte_data(APDS9960_REG_PPULSE, APDS9960_DEFAULT_GESTURE_PPULSE);
		setLEDBoost(APDS9960_LED_BOOST_300);
		setGestureIntEnable(interrupts);
		setGestureMode(true);
		enablePower();
		setMode(APDS9960_MODE_WAIT, true);
		setMode(APDS9960_MODE_PROXIMITY, true);
		setMode(APDS9960_MODE_GESTURE, true);
	}
	public void enableGestureSensor() throws Exception {
		enableGestureSensor(true);
	}

	// stop the gesture recognition engine
	// disableGestureSensor(self):
	public void disableGestureSensor() throws Exception {
		resetGestureParameters();
		setGestureIntEnable(false);
		setGestureMode(false);
		setMode(APDS9960_MODE_GESTURE, false);
	}

	// check if there is a gesture available
	// isGestureAvailable(self):
	public boolean isGestureAvailable() throws IOException {
		int val = _read_byte_data(APDS9960_REG_GSTATUS);
		// shift & mask out GVALID bit;
		val &= APDS9960_BIT_GVALID;
		return (val == APDS9960_BIT_GVALID);
	}

	// processes a gesture event and returns best guessed gesture
	// readGesture(self):
	public int readGesture() throws IOException {
		int fifo_level = 0;
		//int bytes_read = 0;
		List<Byte> fifo_data;
		// make sure that power & gesture is on & data is valid;
		if (!((getMode() & 0b01000001) > 0) || !isGestureAvailable()) {
			return APDS9960_DIR_NONE;
		}
		// keep looping as long as gesture data is valid;
		while (isGestureAvailable()) {
			// read the current FIFO level;
			fifo_level = _read_byte_data(APDS9960_REG_GFLVL);
			// if there's stuff in the FIFO, read it into our data block;
			if (fifo_level > 0) {
				fifo_data = new ArrayList<Byte>();
				for (int i = 0; i < fifo_level; i++) {
					byte[] bytes = _read_i2c_block_data(APDS9960_REG_GFIFO_U, 4);
					fifo_data.add(bytes[0]);
					fifo_data.add(bytes[1]);
					fifo_data.add(bytes[2]);
					fifo_data.add(bytes[3]);
				}
				// if at least 1 set of data, sort the data into U/D/L/R
				if (fifo_data.size() >= 4) {
					for (int i = 0; i < fifo_data.size(); i += 4) {
						gesture_data_.u_data[gesture_data_.index] = fifo_data.get(i + 0)&0xff;
						gesture_data_.d_data[gesture_data_.index] = fifo_data.get(i + 1)&0xff;
						gesture_data_.l_data[gesture_data_.index] = fifo_data.get(i + 2)&0xff;
						gesture_data_.r_data[gesture_data_.index] = fifo_data.get(i + 3)&0xff;
						gesture_data_.index += 1;
						gesture_data_.total_gestures += 1;
					}
					// filter and process gesture data, decode near/far state
					if (processGestureData()) {
						if (decodeGesture()) {
							// ***TODO: U-Turn Gestures
						}
					}

					// reset data
					gesture_data_.index = 0;
					gesture_data_.total_gestures = 0;
				}

			}
			// wait some time to collect next batch of FIFO data
			sleep(APDS9960_TIME_FIFO_PAUSE);
		}
		// determine best guessed gesture & clean up;
		sleep(APDS9960_TIME_FIFO_PAUSE);
		decodeGesture();
		int motion = gesture_motion_;
		resetGestureParameters();
		return motion;
	}

	// turn the APDS-9960 on
	// enablePower(self):
	public void enablePower() throws Exception {
		setMode(APDS9960_MODE_POWER, true);
	}

	// disablePower(self):
	public void disablePower() throws Exception {
		// turn the APDS-9960 off;
		setMode(APDS9960_MODE_POWER, false);
	}

	// *******************************************************************************;
	// ambient light & color sensor controls;
	// *******************************************************************************;
	// reads the ambient (clear) light level as a 16-bit value
	// readAmbientLight(self):
	public int readAmbientLight() throws IOException {
		// read value from clear channel, low byte register;
		int l = _read_byte_data(APDS9960_REG_CDATAL);
		// read value from clear channel, high byte register;
		int h = _read_byte_data(APDS9960_REG_CDATAH);
		return l + (h << 8);
	}

	// reads the red light level as a 16-bit value
	// readRedLight(self):
	public int readRedLight() throws IOException {
		// read value from red channel, low byte register;
		int l = _read_byte_data(APDS9960_REG_RDATAL);
		// read value from red channel, high byte register;
		int h = _read_byte_data(APDS9960_REG_RDATAH);
		return l + (h << 8);
	}

	// reads the green light level as a 16-bit value
	// readGreenLight(self):
	public int readGreenLight() throws IOException {
		// read value from green channel, low byte register;
		int l = _read_byte_data(APDS9960_REG_GDATAL);
		// read value from green channel, high byte register;
		int h = _read_byte_data(APDS9960_REG_GDATAH);
		return l + (h << 8);
	}

	// reads the blue light level as a 16-bit value
	// readBlueLight(self):
	public int readBlueLight() throws IOException {
		// read value from blue channel, low byte register;
		int l = _read_byte_data(APDS9960_REG_BDATAL);
		// read value from blue channel, high byte register;
		int h = _read_byte_data(APDS9960_REG_BDATAH);
		return l + (h << 8);
	}

	// *******************************************************************************;
	// Proximity sensor controls;
	// *******************************************************************************;
	// reads the proximity level as an 8-bit value
	// readProximity(self):
	public int readProximity() throws IOException {
		return _read_byte_data(APDS9960_REG_PDATA);
	}

	// *******************************************************************************;
	// High-level gesture controls;
	// *******************************************************************************;
	// resetGestureParameters(self):
	public void resetGestureParameters() {
		gesture_data_.index = 0;
		gesture_data_.total_gestures = 0;
		gesture_ud_delta_ = 0;
		gesture_lr_delta_ = 0;
		gesture_ud_count_ = 0;
		gesture_lr_count_ = 0;
		gesture_near_count_ = 0;
		gesture_far_count_ = 0;
		gesture_state_ = 0;
		gesture_motion_ = APDS9960_DIR_NONE;
	}

	// processGestureData(self):
	public boolean processGestureData() {
		// Processes the raw gesture data to determine swipe direction
		// Returns:
		// bool: True if near or far state seen, False otherwise.
		int u_first = 0;
		int d_first = 0;
		int l_first = 0;
		int r_first = 0;
		int u_last = 0;
		int d_last = 0;
		int l_last = 0;
		int r_last = 0;
		// if we have less than 4 total gestures, that's not enough
		if (gesture_data_.total_gestures <= 4) {
			return false;
		}
		// check to make sure our data isn't out of bounds
		if (gesture_data_.total_gestures <= 32 && gesture_data_.total_gestures > 0) {
			// find the first value in U/D/L/R above the threshold
			for (int i = 0; i < gesture_data_.total_gestures; i++) {
				if (gesture_data_.u_data[i] > APDS9960_GESTURE_THRESHOLD_OUT && gesture_data_.d_data[i] > APDS9960_GESTURE_THRESHOLD_OUT
						&& gesture_data_.l_data[i] > APDS9960_GESTURE_THRESHOLD_OUT && gesture_data_.r_data[i] > APDS9960_GESTURE_THRESHOLD_OUT) {
					u_first = gesture_data_.u_data[i];
					d_first = gesture_data_.d_data[i];
					l_first = gesture_data_.l_data[i];
					r_first = gesture_data_.r_data[i];
					break;
				}
			}
			// if one of the _first values is 0, then there is no good data
			if (u_first == 0 || d_first == 0 || l_first == 0 || r_first == 0)
				return false;
			// find the last value in U/D/L/R above the threshold
			for (int i = gesture_data_.total_gestures - 1; i >= 0; i--) {
				if (gesture_data_.u_data[i] > APDS9960_GESTURE_THRESHOLD_OUT && gesture_data_.d_data[i] > APDS9960_GESTURE_THRESHOLD_OUT
						&& gesture_data_.l_data[i] > APDS9960_GESTURE_THRESHOLD_OUT && gesture_data_.r_data[i] > APDS9960_GESTURE_THRESHOLD_OUT) {
					u_last = gesture_data_.u_data[i];
					d_last = gesture_data_.d_data[i];
					l_last = gesture_data_.l_data[i];
					r_last = gesture_data_.r_data[i];
					break;
				}

			}
			// calculate the first vs. last ratio of up/down and left/right
			int ud_ratio_first = ((u_first - d_first) * 100) / (u_first + d_first);
			int lr_ratio_first = ((l_first - r_first) * 100) / (l_first + r_first);
			int ud_ratio_last = ((u_last - d_last) * 100) / (u_last + d_last);
			int lr_ratio_last = ((l_last - r_last) * 100) / (l_last + r_last);

			// determine the difference between the first and last ratios
			int ud_delta = ud_ratio_last - ud_ratio_first;
			int lr_delta = lr_ratio_last - lr_ratio_first;

			// accumulate the UD and LR delta values
			gesture_ud_delta_ += ud_delta;
			gesture_lr_delta_ += lr_delta;

			// determine U/D gesture
			if (gesture_ud_delta_ >= APDS9960_GESTURE_SENSITIVITY_1) {
				gesture_ud_count_ = 1;
			} else if (gesture_ud_delta_ <= -APDS9960_GESTURE_SENSITIVITY_1) {
				gesture_ud_count_ = -1;
			} else {
				gesture_ud_count_ = 0;
			}
			// determine L/R gesture
			if (gesture_lr_delta_ >= APDS9960_GESTURE_SENSITIVITY_1) {
				gesture_lr_count_ = 1;
			} else if (gesture_lr_delta_ <= -APDS9960_GESTURE_SENSITIVITY_1) {
				gesture_lr_count_ = -1;
			} else {
				gesture_lr_count_ = 0;
			}
			// determine Near/Far gesture
			if (gesture_ud_count_ == 0 && gesture_lr_count_ == 0) {
				if (Math.abs(ud_delta) < APDS9960_GESTURE_SENSITIVITY_2 && Math.abs(lr_delta) < APDS9960_GESTURE_SENSITIVITY_2) {
					if (ud_delta == 0 && lr_delta == 0) {
						gesture_near_count_ += 1;
					} else if (ud_delta != 0 || lr_delta != 0) {
						gesture_far_count_ += 1;
					}
					if (gesture_near_count_ >= 10 && gesture_far_count_ >= 2) {
						if (ud_delta == 0 && lr_delta == 0) {
							gesture_state_ = APDS9960_STATE_NEAR;
						} else if (ud_delta != 0 && lr_delta != 0) {
							gesture_state_ = APDS9960_STATE_FAR;
						}
						return true;
					}

				}

			} else {
				if (Math.abs(ud_delta) < APDS9960_GESTURE_SENSITIVITY_2 && Math.abs(lr_delta) < APDS9960_GESTURE_SENSITIVITY_2) {
					if (ud_delta == 0 && lr_delta == 0) {
						gesture_near_count_ += 1;
					}
					if (gesture_near_count_ >= 10) {
						gesture_ud_count_ = 0;
						gesture_lr_count_ = 0;
						gesture_ud_delta_ = 0;
						gesture_lr_delta_ = 0;
					}
				}
			}

		}

		return false;
	}

	// decodeGesture(self):
	public boolean decodeGesture() {
		// Determines swipe direction or near/far state.
		// return if near or far event is detected
		if (gesture_state_ == APDS9960_STATE_NEAR) {
			gesture_motion_ = APDS9960_DIR_NEAR;
			return true;
		}
		if (gesture_state_ == APDS9960_STATE_FAR) {
			gesture_motion_ = APDS9960_DIR_FAR;
			return true;
		}
		// determine swipe direction
		if (gesture_ud_count_ == -1 && gesture_lr_count_ == 0)
			gesture_motion_ = APDS9960_DIR_UP;
		else if (gesture_ud_count_ == 1 && gesture_lr_count_ == 0)
			gesture_motion_ = APDS9960_DIR_DOWN;
		else if (gesture_ud_count_ == 0 && gesture_lr_count_ == 1)
			gesture_motion_ = APDS9960_DIR_RIGHT;
		else if (gesture_ud_count_ == 0 && gesture_lr_count_ == -1)
			gesture_motion_ = APDS9960_DIR_LEFT;
		else if (gesture_ud_count_ == -1 && gesture_lr_count_ == 1)
			if (Math.abs(gesture_ud_delta_) > Math.abs(gesture_lr_delta_))
				gesture_motion_ = APDS9960_DIR_UP;
			else
				gesture_motion_ = APDS9960_DIR_DOWN;
		else if (gesture_ud_count_ == 1 && gesture_lr_count_ == -1)
			if (Math.abs(gesture_ud_delta_) > Math.abs(gesture_lr_delta_))
				gesture_motion_ = APDS9960_DIR_DOWN;
			else
				gesture_motion_ = APDS9960_DIR_LEFT;
		else if (gesture_ud_count_ == -1 && gesture_lr_count_ == -1)
			if (Math.abs(gesture_ud_delta_) > Math.abs(gesture_lr_delta_))
				gesture_motion_ = APDS9960_DIR_UP;
			else
				gesture_motion_ = APDS9960_DIR_LEFT;
		else if (gesture_ud_count_ == 1 && gesture_lr_count_ == 1)
			if (Math.abs(gesture_ud_delta_) > Math.abs(gesture_lr_delta_))
				gesture_motion_ = APDS9960_DIR_DOWN;
			else
				gesture_motion_ = APDS9960_DIR_RIGHT;
		else
			return false;
		return true;
	}

	// *******************************************************************************
	// Getters and setters for register values
	// *******************************************************************************

	// getProxIntLowThresh(self):
	public int getProxIntLowThresh() throws IOException {
		// Returns the lower threshold for proximity detection
		return _read_byte_data(APDS9960_REG_PILT);
	}

	// setProxIntLowThresh(self, threshold):
	public void setProxIntLowThresh(int threshold) throws IOException {
		// Sets the lower threshold for proximity detection.
		_write_byte_data(APDS9960_REG_PILT, threshold);
	}

	// getProxIntHighThresh(self):
	public int getProxIntHighThresh() throws IOException {
		// Returns the high threshold for proximity detection.
		return _read_byte_data(APDS9960_REG_PIHT);
	}

	// setProxIntHighThresh(self, threshold):
	public void setProxIntHighThresh(int threshold) throws IOException {
		// Sets the high threshold for proximity detection.
		_write_byte_data(APDS9960_REG_PIHT, threshold);
	}

	// getLEDDrive(self):
	public int getLEDDrive() throws IOException {
		// Returns LED drive strength for proximity and ALS.
		/*
		 * Value LED Current 0 100 mA 1 50 mA 2 25 mA 3 12.5 mA
		 * 
		 * Returns: int: the value of the LED drive strength
		 */
		int val = _read_byte_data(APDS9960_REG_CONTROL);
		// shift and mask out LED drive bits
		return (val >> 6) & 0b00000011;
	}

	// setLEDDrive(self, drive):
	public void setLEDDrive(int drive) throws IOException {
		/*
		 * Sets LED drive strength for proximity and ALS.
		 * 
		 * Value LED Current 0 100 mA 1 50 mA 2 25 mA 3 12.5 mA
		 * 
		 * Args: drive (int): value for the LED drive strength
		 */
		int val = _read_byte_data(APDS9960_REG_CONTROL);

		// set bits in register to given value
		drive &= 0b00000011;
		drive = drive << 6;
		val &= 0b00111111;
		val |= drive;

		_write_byte_data(APDS9960_REG_CONTROL, val);

	}

	// getProximityGain(self):
	public int getProximityGain() throws IOException {
		// Returns receiver gain for proximity detection.
		/*
		 * Value Gain 0 1x 1 2x 2 4x 3 8x
		 * 
		 * Returns: int: the value of the proximity gain
		 */
		int val = _read_byte_data(APDS9960_REG_CONTROL);
		// shift and mask out PDRIVE bits
		return (val >> 2) & 0b00000011;
	}

	// setProximityGain(self, drive):
	public void setProximityGain(int drive) throws IOException {
		/*
		 * Returns receiver gain for proximity detection.
		 * 
		 * Value Gain 0 1x 1 2x 2 4x 3 8x
		 * 
		 * Args: drive (int): value for the proximity gain
		 */
		int val = _read_byte_data(APDS9960_REG_CONTROL);

		// set bits in register to given value
		drive &= 0b00000011;
		drive = drive << 2;
		val &= 0b11110011;
		val |= drive;

		_write_byte_data(APDS9960_REG_CONTROL, val);
	}

	// getAmbientLightGain(self):
	public int getAmbientLightGain() throws IOException {
		// Returns receiver gain for the ambient light sensor (ALS).
		/*
		 * Value Gain 0 1x 1 4x 2 16x 3 64x
		 * 
		 * Returns: int: the value of the ALS gain
		 */
		int val = _read_byte_data(APDS9960_REG_CONTROL);

		// shift and mask out ADRIVE bits
		return (val & 0b00000011);
	}

	// setAmbientLightGain(self, drive):
	public void setAmbientLightGain(int drive) throws IOException {
		/*
		 * Sets the receiver gain for the ambient light sensor (ALS).
		 * 
		 * Value Gain 0 1x 1 4x 2 16x 3 64x
		 * 
		 * Args: drive (int): value for the ALS gain
		 */
		int val = _read_byte_data(APDS9960_REG_CONTROL);
		drive &= 0b00000011;
		val &= 0b11111100;
		val |= drive;

		_write_byte_data(APDS9960_REG_CONTROL, val);
	}

	// getLEDBoost(self):
	public int getLEDBoost() throws IOException {
		// Get the current LED boost value
		/*
		 * Value Gain 0 100% 1 150% 2 200% 3 300%
		 * 
		 * Returns: int: the LED boost value
		 */
		int val = _read_byte_data(APDS9960_REG_CONFIG2);
		// shift and mask out LED_BOOST bits
		return (val >> 4) & 0b00000011;
	}

	// setLEDBoost(self, boost):
	public void setLEDBoost(int boost) throws IOException {
		// Sets the LED current boost value.
		/*
		 * Value Gain 0 100% 1 150% 2 200% 3 300%
		 * 
		 * Args: boost (int): value for the LED boost
		 */
		int val = _read_byte_data(APDS9960_REG_CONFIG2);

		// set bits in register to given value
		boost &= 0b00000011;
		boost = boost << 4;
		val &= 0b11001111;
		val |= boost;
		_write_byte_data(APDS9960_REG_CONFIG2, val);
	}

	// getProxGainCompEnable(self):
	public boolean getProxGainCompEnable() throws IOException {
		/*
		 * Gets proximity gain compensation enable.
		 * 
		 * Returns: bool: True if compensation is enabled, False if not
		 */
		int val = _read_byte_data(APDS9960_REG_CONFIG3);

		// Shift and mask out PCMP bits
		val = (val >> 5) & 0b00000001;
		return val == 1;
	}

	// setProxGainCompEnable(self, enable):
	public void setProxGainCompEnable(boolean enable) throws IOException {
		/*
		 * Sets the proximity gain compensation enable.
		 * 
		 * Args: enable (bool): True to enable compensation, False to disable
		 */
		int val = _read_byte_data(APDS9960_REG_CONFIG3);

		// set bits in register to given value
		val &= 0b11011111;
		if (enable)
			val |= 0b00100000;

		_write_byte_data(APDS9960_REG_CONFIG3, val);

	}

	// getProxPhotoMask(self):
	public int getProxPhotoMask() throws IOException {
		/*
		 * Gets the current mask for enabled/disabled proximity photodiodes.
		 * 
		 * Bit Photodiode 3 UP 2 DOWN 1 LEFT 0 RIGHT
		 * 
		 * 1 = disabled, 0 = enabled
		 * 
		 * Returns: int: Current proximity mask for photodiodes.
		 */
		int val = _read_byte_data(APDS9960_REG_CONFIG3);

		// mask out photodiode enable mask bits
		return val & 0b00001111;

	}

	// setProxPhotoMask(self, mask):
	public void setProxPhotoMask(int mask) throws IOException {
		/*
		 * Sets the mask for enabling/disabling proximity photodiodes.
		 * 
		 * Bit Photodiode 3 UP 2 DOWN 1 LEFT 0 RIGHT
		 * 
		 * 1 = disabled, 0 = enabled
		 * 
		 * Args: mask (int): 4-bit mask value
		 */
		int val = _read_byte_data(APDS9960_REG_CONFIG3);

		// set bits in register to given value
		mask &= 0b00001111;
		val &= 0b11110000;
		val |= mask;

		_write_byte_data(APDS9960_REG_CONFIG3, val);

	}

	// getGestureEnterThresh(self):
	public int getGestureEnterThresh() throws IOException {
		/*
		 * Gets the entry proximity threshold for gesture sensing.
		 * 
		 * Returns: int: current entry proximity threshold
		 */
		return _read_byte_data(APDS9960_REG_GPENTH);
	}

	// setGestureEnterThresh(self, threshold):
	public void setGestureEnterThresh(int threshold) throws IOException {
		/*
		 * Sets the entry proximity threshold for gesture sensing.
		 * 
		 * Args: threshold (int) throws IOException{ threshold proximity value needed to
		 * start gesture mode
		 */
		_write_byte_data(APDS9960_REG_GPENTH, threshold);

	}

	// getGestureExitThresh(self):
	public int getGestureExitThresh() throws IOException {
		/*
		 * Gets the exit proximity threshold for gesture sensing.
		 * 
		 * Returns: int: current exit proximity threshold
		 */
		return _read_byte_data(APDS9960_REG_GEXTH);

	}

	// setGestureExitThresh(self, threshold):
	public void setGestureExitThresh(int threshold) throws IOException {
		/*
		 * Sets the exit proximity threshold for gesture sensing.
		 * 
		 * Args: threshold (int) throws IOException{ threshold proximity value needed to
		 * end gesture mode
		 */
		_write_byte_data(APDS9960_REG_GEXTH, threshold);

	}

	// getGestureGain(self):
	public int getGestureGain() throws IOException {
		/*
		 * Gets the gain of the photodiode during gesture mode.
		 * 
		 * Value Gain 0 1x 1 2x 2 4x 3 8x
		 * 
		 * Returns: int: the current photodiode gain
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF2);

		// shift and mask out PDRIVE bits
		return (val >> 5) & 0b00000011;

	}

	// setGestureGain(self, gain):
	public void setGestureGain(int gain) throws IOException {
		/*
		 * Sets the gain of the photodiode during gesture mode.
		 * 
		 * Value Gain 0 1x 1 2x 2 4x 3 8x
		 * 
		 * Args: gain (int) throws IOException{ the value for the photodiode gain
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF2);

		// set bits in register to given value
		gain &= 0b00000011;
		gain = gain << 5;
		val &= 0b10011111;
		val |= gain;

		_write_byte_data(APDS9960_REG_GCONF2, val);

	}

	// getGestureLEDDrive(self):
	public int getGestureLEDDrive() throws IOException {
		/*
		 * Gets the drive current of the LED during gesture mode.
		 * 
		 * Value LED Current 0 100 mA 1 50 mA 2 25 mA 3 12.5 mA
		 * 
		 * Returns: int: the LED drive current value
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF2);

		// shift and mask out LED drive bits
		return (val >> 3) & 0b00000011;

	}
	// setGestureLEDDrive(self, drive):

	public void setGestureLEDDrive(int drive) throws IOException {
		/*
		 * Sets LED drive strength for proximity and ALS.
		 * 
		 * Value LED Current 0 100 mA 1 50 mA 2 25 mA 3 12.5 mA
		 * 
		 * Args: drive (int) throws IOException{ value for the LED drive current
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF2);

		// set bits in register to given value
		drive &= 0b00000011;
		drive = drive << 3;
		val &= 0b11100111;
		val |= drive;

		_write_byte_data(APDS9960_REG_GCONF2, val);

	}

	// getGestureWaitTime(self):
	public int getGestureWaitTime() throws IOException {
		/*
		 * Gets the time in low power mode between gesture detections.
		 * 
		 * Value Wait time 0 0 ms 1 2.8 ms 2 5.6 ms 3 8.4 ms 4 14.0 ms 5 22.4 ms 6 30.8
		 * ms 7 39.2 ms
		 * 
		 * Returns: int: the current wait time between gestures
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF2);

		// shift and mask out LED drive bits
		return val & 0b00000111;
	}

	// setGestureWaitTime(self, time):
	public void setGestureWaitTime(int time) throws IOException {
		/*
		 * Sets the time in low power mode between gesture detections.
		 * 
		 * Value Wait time 0 0 ms 1 2.8 ms 2 5.6 ms 3 8.4 ms 4 14.0 ms 5 22.4 ms 6 30.8
		 * ms 7 39.2 ms
		 * 
		 * Args: time (int) throws IOException{ value for the wait time
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF2);

		// set bits in register to given value
		time &= 0b00000111;
		val &= 0b11111000;
		val |= time;

		_write_byte_data(APDS9960_REG_GCONF2, val);

	}

	// setLightIntLowThreshold(self, threshold):
	public void setLightIntLowThreshold(int threshold) throws IOException {
		/**
		 * Sets the low threshold for ambient light interrupts.
		 * 
		 * Args: threshold (int): low threshold value for interrupt to trigger
		 */
		// break 16-bit threshold into 2 8-bit values
		_write_byte_data(APDS9960_REG_AILTL, threshold & 0x00ff);
		_write_byte_data(APDS9960_REG_AILTH, (threshold & 0xff00) >> 8);
	}

	// getLightIntLowThreshold(self):
	public int getLightIntLowThreshold() throws IOException {
		/*
		 * Gets the low threshold for ambient light interrupts.
		 * 
		 * Returns: int: threshold current low threshold stored on the APDS9960
		 */
		return _read_byte_data(APDS9960_REG_AILTL) | (_read_byte_data(APDS9960_REG_AILTH) << 8);

	}

	// getLightIntHighThreshold(self):
	public int getLightIntHighThreshold() throws IOException {
		/*
		 * Gets the high threshold for ambient light interrupts.
		 * 
		 * Returns: int: threshold current low threshold stored on the APDS9960
		 */
		return _read_byte_data(APDS9960_REG_AIHTL) | (_read_byte_data(APDS9960_REG_AIHTH) << 8);

	}

	// setLightIntHighThreshold(self, threshold):
	public void setLightIntHighThreshold(int threshold) throws IOException {
		/*
		 * Sets the high threshold for ambient light interrupts.
		 * 
		 * Args: threshold (int) throws IOException{ high threshold value for interrupt
		 * to trigger
		 */
		// break 16-bit threshold into 2 8-bit values
		_write_byte_data(APDS9960_REG_AIHTL, threshold & 0x00ff);
		_write_byte_data(APDS9960_REG_AIHTH, (threshold & 0xff00) >> 8);

	}

	// getProximityIntLowThreshold(self):
	public int getProximityIntLowThreshold() throws IOException {
		/*
		 * Gets the low threshold for proximity interrupts.
		 * 
		 * Returns: int: threshold current low threshold stored on the APDS9960
		 */
		return _read_byte_data(APDS9960_REG_PILT);

	}

	// setProximityIntLowThreshold(self, threshold):
	public void setProximityIntLowThreshold(int threshold) throws IOException {
		/*
		 * Sets the low threshold for proximity interrupts.
		 * 
		 * Args: threshold (int) throws IOException{ low threshold value for interrupt
		 * to trigger
		 */
		_write_byte_data(APDS9960_REG_PILT, threshold);

	}

	// getProximityIntHighThreshold(self):
	public int getProximityIntHighThreshold() throws IOException {
		/*
		 * Gets the high threshold for proximity interrupts.
		 * 
		 * Returns: int: threshold current high threshold stored on the APDS9960
		 */
		return _read_byte_data(APDS9960_REG_PIHT);

	}

	// setProximityIntHighThreshold(self, threshold):
	public void setProximityIntHighThreshold(int threshold) throws IOException {
		/*
		 * Sets the high threshold for proximity interrupts.
		 * 
		 * Args: threshold (int) throws IOException{ high threshold value for interrupt
		 * to trigger
		 */
		_write_byte_data(APDS9960_REG_PIHT, threshold);

	}

	// getAmbientLightIntEnable(self):
	public boolean getAmbientLightIntEnable() throws IOException {
		/*
		 * Gets if ambient light interrupts are enabled or not.
		 * 
		 * Returns: bool: True if interrupts are enabled, False if not
		 */
		int val = _read_byte_data(APDS9960_REG_ENABLE);
		return ((val >> 4) & 0b00000001) == 1;
	}

	// setAmbientLightIntEnable(self, enable):
	public void setAmbientLightIntEnable(boolean enable) throws IOException {
		/*
		 * Turns ambient light interrupts on or off.
		 * 
		 * Args: enable (bool) throws IOException{ True to enable interrupts, False to
		 * turn them off
		 */
		int val = _read_byte_data(APDS9960_REG_ENABLE);

		// set bits in register to given value
		val &= 0b11101111;
		if (enable)
			val |= 0b00010000;

		_write_byte_data(APDS9960_REG_ENABLE, val);

	}

	// getProximityIntEnable(self):
	public boolean getProximityIntEnable() throws IOException {
		/*
		 * Gets if proximity interrupts are enabled or not.
		 * 
		 * Returns: bool: True if interrupts are enabled, False if not
		 */
		int val = _read_byte_data(APDS9960_REG_ENABLE);
		return ((val >> 5) & 0b00000001) == 1;

	}

	// setProximityIntEnable(self, enable):
	public void setProximityIntEnable(boolean enable) throws IOException {
		/*
		 * Turns proximity interrupts on or off.
		 * 
		 * Args: enable (bool) throws IOException{ True to enable interrupts, False to
		 * turn them off
		 */
		int val = _read_byte_data(APDS9960_REG_ENABLE);

		// set bits in register to given value
		val &= 0b11011111;
		if (enable)
			val |= 0b00100000;

		_write_byte_data(APDS9960_REG_ENABLE, val);

	}

	// getGestureIntEnable(self):
	public boolean getGestureIntEnable() throws IOException {
		/*
		 * Gets if gesture interrupts are enabled or not.
		 * 
		 * Returns: bool: True if interrupts are enabled, False if not
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF4);
		return ((val >> 1) & 0b00000001) == 1;
	}

	// setGestureIntEnable(self, enable):
	public void setGestureIntEnable(boolean enable) throws IOException {
		/*
		 * Turns gesture-related interrupts on or off.
		 * 
		 * Args: enable (bool) throws IOException{ True to enable interrupts, False to
		 * turn them off
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF4);

		// set bits in register to given value
		val &= 0b11111101;
		if (enable)
			val |= 0b00000010;

		_write_byte_data(APDS9960_REG_GCONF4, val);
	}

	// clearAmbientLightInt(self):
	public void clearAmbientLightInt() throws IOException {
		/*
		 * Clears the ambient light interrupt.
		 */
		_read_byte_data(APDS9960_REG_AICLEAR);
	}

	// clearProximityInt(self):
	public void clearProximityInt() throws IOException {
		/*
		 * Clears the proximity interrupt.
		 */
		_read_byte_data(APDS9960_REG_PICLEAR);

	}

	// getGestureMode(self):
	public boolean getGestureMode() throws IOException {
		/*
		 * Tells if the gesture state machine is currently running.
		 * 
		 * Returns: bool: True if gesture state machine is running, False if not
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF4);
		return (val & 0b00000001) == 1;
	}
	// setGestureMode(self, enable):

	public void setGestureMode(boolean enable) throws IOException {
		/*
		 * Turns gesture-related interrupts on or off.
		 * 
		 * Args: enable (bool) throws IOException{ True to enter gesture state machine,
		 * False to turn them off
		 */
		int val = _read_byte_data(APDS9960_REG_GCONF4);

		// set bits in register to given value
		val &= 0b11111110;
		if (enable)
			val |= 0b00000001;

		_write_byte_data(APDS9960_REG_GCONF4, val);
	}

	/**
	 * #
	 * *******************************************************************************
	 * # Raw I2C Reads and Writes #
	 * *******************************************************************************
	 */
	public byte[] _read_i2c_block_data(int cmd, int num) throws IOException {
		byte[] buffer = new byte[num];
		device.read(cmd, buffer, 0, num);
		//System.out.println(JsonSerializer.create().serialize(buffer));
		return buffer;
	}

	public int _read_byte_data(int cmd) throws IOException {
		return device.read(cmd);
	}

	public void _write_byte_data(int cmd, byte val) throws IOException {
		device.write(cmd, val);
	}

	public void _write_byte_data(int cmd, int val) throws IOException {
		device.write(cmd, (byte) val);
	}

	public void sleep(int s) {
		Gpio.delay(s);
	}

	public static void main(String[] args) throws Exception {
	}

}
