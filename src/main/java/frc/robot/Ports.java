package frc.robot;


/**
 * Contains the definitions of all the ports
 */
public class Ports {

		// IP (v4) addresses
		// The purpose of this section is to serve as a reminder of what static IP (v4) addresses are used so they are consistent
		// between the competition and practice robots.
		//
		// The radio is automatically set to 10.24.95.1
		// The Rio is set to static 10.24.95.2, mask 255.255.255.0
		// The Limelight is set to 10.24.95.11, mask 255.255.255.0, gateway 10.24.95.1
		// but note that pressing the reset button will revert to DHCP.
		// The Raspberry Pi running FRCVision is set to static 10.24.95.12, mask 255.255.255.0, gateway 10.24.95.1, DNS blank
		//
		// If a device cannot be accessed (e.g. because its address was somehow obtained via DHCP and mDNS is not working),
		// use Angry IP Scanner to find it!


		/**
		 * Digital ports
		 */
		public static class Digital {
		}
		
		/**
		 * Analog ports
		 */
		public static class Analog {
			//public static final int SONAR = 3;
			//public static final int PRESSURE_SENSOR = 1;

			// 2023 Off-season
			// SPARK MAX Absolute encoders
			
			public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 15;			
			public static final int FRONT_LEFT_TURNING_ABSOLUTE_ENCODER = 12;
			public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 21;
			public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 18;

			// TODO
			// public static final int MODULE_1_CANCODER = 12;
			// public static final int MODULE_2_CANCODER = 15;
			// public static final int MODULE_3_CANCODER = 18;
			// public static final int MODULE_4_CANCODER = 21;
		}
		
		/**
		 * CAN Ids
		 */
		public static class CAN {

			//2023 Off-season
			public static final int PCM = 1;
			public static final int PDP = 0;	

			// SPARK MAX CAN IDs
			public static final int FRONT_RIGHT_DRIVING = 13;
			public static final int FRONT_RIGHT_TURNING = 14;

			public static final int FRONT_LEFT_DRIVING = 10;
			public static final int FRONT_LEFT_TURNING = 11;
			
			public static final int REAR_LEFT_DRIVING = 19;
			public static final int REAR_LEFT_TURNING = 20;

			public static final int REAR_RIGHT_DRIVING = 61;
			public static final int REAR_RIGHT_TURNING = 62;
			;
			
			// TODO
			// public static final int MODULE_1_DRIVING = 10;
			// public static final int MODULE_1_TURNING = 11;

			// public static final int MODULE_2_DRIVING = 13;
			// public static final int MODULE_2_TURNING = 14;

			// public static final int MODULE_3_DRIVING = 16;
			// public static final int MODULE_3_TURNING = 17;

			// public static final int MODULE_4_DRIVING = 19;
			// public static final int MODULE_4_TURNING = 20;

		}
		
		/**
		 * USB ports
		 */
		public static class USB {
			public static final int DRIVER_CONTROLLER = 0;
			public static final int OPERATOR_CONTROLLER = 1;
		}
		
		/**
		 * PCM ports
		 */
		public static class PCM {
		}

		/**
		 * PWM ports
		 */
		public static class PWM {
		}

		/**
		 * USB cameras
		 */
		public static class UsbCamera {
		}
}
