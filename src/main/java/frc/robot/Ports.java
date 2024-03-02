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
			// SPARK MAX Absolute encoders
			
			public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 12;			
			public static final int FRONT_LEFT_TURNING_ABSOLUTE_ENCODER = 22;
			public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 32;
			public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 42;		
		}
		
		/**
		 * CAN Ids
		 */
		public static class CAN {

			public static final int PCM = 1;
			public static final int PDP = 0;	

			// SPARK MAX CAN IDs
			public static final int FRONT_RIGHT_DRIVING = 10;
			public static final int FRONT_RIGHT_TURNING = 11;

			public static final int FRONT_LEFT_DRIVING = 20;
			public static final int FRONT_LEFT_TURNING = 21;
			
			public static final int REAR_LEFT_DRIVING = 30;
			public static final int REAR_LEFT_TURNING = 31;

			public static final int REAR_RIGHT_DRIVING = 40;
			public static final int REAR_RIGHT_TURNING = 41;

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
