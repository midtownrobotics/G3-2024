// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.ProfiledPIDController;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.geometry.Translation2d;
//import edu.wpi.first.math.trajectory.Trajectory;
//import edu.wpi.first.math.trajectory.TrajectoryConfig;
//import edu.wpi.first.math.trajectory.TrajectoryGenerator;

//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.XboxController.Button;
//import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController; 
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
//import edu.wpi.first.wpilibj2.command.InstantCommand;
//import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import java.util.List;

//import frc.robot.Constants.AutoConstants;
//import frc.robot.Constants.DrivetrainConstants;

//import frc.robot.sensors.*;

/*import frc.robot.interfaces.IElevator;
import frc.robot.interfaces.IDrawer;
import frc.robot.interfaces.INeck;
import frc.robot.interfaces.IRoller;*/

import frc.robot.subsystems.SwerveDrivetrain;
//import frc.robot.subsystems.Elevator;
//import frc.robot.subsystems.Drawer;
//import frc.robot.subsystems.Neck;
//import frc.robot.subsystems.Roller;
//import frc.robot.subsystems.Compressor;
//import frc.robot.subsystems.Mouth;
//import frc.robot.subsystems.Indicator;

//import frc.robot.commands.drivetrain.*;
//import frc.robot.commands.elevator.*;
//import frc.robot.commands.drawer.*;
//import frc.robot.commands.neck.*;
//import frc.robot.commands.roller.*;
//import frc.robot.commands.mouth.*;
//import frc.robot.commands.indicator.*;
//import frc.robot.commands.groups.*;
//import frc.robot.commands.gamepad.*;
//import frc.robot.auton.*;
//import frc.robot.auton.common.*;


/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

	public static final double GAMEPAD_AXIS_THRESHOLD = 0.15;
	public static final double JOYSTICK_X_AXIS_THRESHOLD = 0.15;
	public static final double JOYSTICK_Y_AXIS_THRESHOLD = 0.15;
	public static final double JOYSTICK_Z_AXIS_THRESHOLD = 0.25;

	/*public static final int LX = 0;
	public static final int LY = 1;
	public static final int LT = 2;
	public static final int RT = 3;
	public static final int RX = 4;
	public static final int RY = 5;*/

	// choosers (for auton)
	
	/*public static final String AUTON_DO_NOTHING = "Do Nothing";
	public static final String AUTON_CUSTOM = "My Auto";
	public static final String AUTON_SAMPLE_SWERVE = "Sample Swerve";
	public static final String AUTON_SAMPLE_MOVE_FORWARD = "Sample Move Forward";
	public static final String AUTON_SAMPLE_MOVE_IN_REVERSE = "Sample Move In Reverse";
	public static final String AUTON_SAMPLE_MOVE_IN_GAMMA_SHAPE = "Sample Move In Gamma Shape";
	public static final String AUTON_SAMPLE_MOVE_IN_L_SHAPE_IN_REVERSE = "Sample Move In L Shape In Reverse";
	public static final String AUTON_TEST_HARDCODED_MOVE_1 = "Test Hardcoded Move 1";
	public static final String AUTON_TEST_HARDCODED_MOVE_2 = "Test Hardcoded Move 2";
	private String autonSelected;
	private SendableChooser<String> autonChooser = new SendableChooser<>();

	public static final String GAME_PIECE_NONE = "None";
	public static final String GAME_PIECE_1_CONE = "1 Cone";
	public static final String GAME_PIECE_2_CONES = "2 Cones";
	public static final String GAME_PIECE_1_CUBE = "1 Cube";
	public static final String GAME_PIECE_2_CUBES = "2 Cubes";
	private String gamePieceSelected;
	private SendableChooser<String> gamePieceChooser = new SendableChooser<>();
	
	public static final String START_POSITION_1 = "Starting Position 1";
	public static final String START_POSITION_2 = "Starting Position 2";
	public static final String START_POSITION_3 = "Starting Position 3";
	public static final String START_POSITION_4 = "Starting Position 4";
	public static final String START_POSITION_5 = "Starting Position 5";
	public static final String START_POSITION_6 = "Starting Position 6";
	private String startPosition;
	private SendableChooser<String> startPositionChooser = new SendableChooser<>();

	public static final String MAIN_TARGET_CONE_NODE = "Cone Node";
	public static final String MAIN_TARGET_CUBE_NODE = "Cube Node";
	public static final String MAIN_TARGET_TWO_CONE_NODES = "Two Cone Nodes";
	public static final String MAIN_TARGET_TWO_CUBE_NODES = "Two Cube Nodes";
	public static final String MAIN_TARGET_CHARGING_STATION = "Charging Station";
	public static final String MAIN_TARGET_NOWHERE = "Nowhere";
	private String mainTarget;
	private SendableChooser<String> mainTargetChooser = new SendableChooser<>();
	
	public static final String CAMERA_OPTION_USE_ALWAYS = "Always";
	public static final String CAMERA_OPTION_USE_OPEN_LOOP_ONLY = "Open Loop Only";
	public static final String CAMERA_OPTION_USE_CLOSED_LOOP_ONLY = "Closed Loop Only";
	public static final String CAMERA_OPTION_USE_NEVER = "Never";
	private String cameraOption;
	private SendableChooser<String> cameraOptionChooser = new SendableChooser<>();
	
	public static final String SONAR_OPTION_USE_ALWAYS = "Always";
	public static final String SONAR_OPTION_USE_RELEASE_ONLY = "Release Only";
	public static final String SONAR_OPTION_USE_GRASP_ONLY = "Grasp Only";
	public static final String SONAR_OPTION_USE_NEVER = "Never";
	private String sonarOption;
	private SendableChooser<String> sonarOptionChooser = new SendableChooser<>();
	
	public static final String CLAW_OPTION_RELEASE = "Release";
	public static final String CLAW_OPTION_DONT_RELEASE = "Don't Release"; 
	private String releaseSelected;
	private SendableChooser<String> releaseChooser = new SendableChooser<>();

	public static final String AUTON_OPTION_JUST_DROP_CONE = "Just Drop Cone";
	public static final String AUTON_OPTION_JUST_DROP_CUBE = "Just Drop Cube";
	public static final String AUTON_OPTION_ALSO_DOCK = "Also Dock";
	public static final String AUTON_OPTION_LEAVE_COMMUNITY = "Leave Community";
	public static final String AUTON_OPTION_ALSO_PICKUP_CONE = "Also Pickup Cone";
	public static final String AUTON_OPTION_ALSO_PICKUP_CUBE = "Also Pickup Cube";
	private String autonOption;
	private SendableChooser<String> autonOptionChooser = new SendableChooser<>();*/

	// sensors

	// private final HMAccelerometer accelerometer = new HMAccelerometer();

	// motorized devices

	private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();

	/*
	private final WPI_TalonSRX drawer_master = new WPI_TalonSRX(Ports.CAN.DRAWER);

	private final Drawer drawer = new Drawer(drawer_master);

	private final WPI_TalonSRX elevator_master = new WPI_TalonSRX(Ports.CAN.ELEVATOR_MASTER);

	private final WPI_VictorSPX elevator_follower = new WPI_VictorSPX(Ports.CAN.ELEVATOR_FOLLOWER);

	private final Elevator elevator = new Elevator(elevator_master, elevator_follower);

	private final WPI_TalonFX neck_master = new WPI_TalonFX(Ports.CAN.NECK);
	
	private final Neck neck = new Neck(neck_master);

	private final WPI_TalonSRX roller_master = new WPI_TalonSRX(Ports.CAN.ROLLER);
	
	private final Roller roller = new Roller(roller_master);
	
	// pneumatic devices

	private final Compressor compressor = new Compressor();

	private final Mouth mouth = new Mouth();
*/
	// misc

	private final Field2d field = new Field2d(); //  a representation of the field

	//private final Indicator indicator = new Indicator(null);

	// The driver's and copilot's joystick(s) and controller(s)

	/*CommandJoystick joyLeft = new CommandJoystick(Ports.USB.LEFT_JOYSTICK);
	CommandJoystick joyRight = new CommandJoystick(Ports.USB.RIGHT_JOYSTICK);*/
	//CommandJoystick joyMain = new CommandJoystick(Ports.USB.MAIN_JOYSTICK);
	XboxController driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);
	//CommandXboxController driverGamepad = new CommandXboxController(Ports.USB.DRIVER_GAMEPAD);
	//CommandXboxController copilotGamepad = new CommandXboxController(Ports.USB.COPILOT_GAMEPAD);
	

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {

		// choosers (for auton)
		

		// Configure the button bindings


		// Configure default commands

		// drivetrain.setDefaultCommand(
		// 	// The left stick controls translation of the robot.
		// 	// Turning is controlled by the X axis of the right stick.
		// 	// We are inverting LeftY because Xbox controllers return negative values when we push forward.
		// 	// We are inverting LeftX because we want a positive value when we pull to the left. Xbox controllers return positive values when you pull to the right by default.
		// 	// We are also inverting RightX because we want a positive value when we pull to the left (CCW is positive in mathematics).
		// 	new RunCommand(
		// 		() -> drivetrain.drive(
		// 			-MathUtil.applyDeadband(joyMain.getY(), JOYSTICK_Y_AXIS_THRESHOLD),
		// 			-MathUtil.applyDeadband(joyMain.getX(), JOYSTICK_X_AXIS_THRESHOLD),
		// 			-MathUtil.applyDeadband(joyMain.getZ(), JOYSTICK_Z_AXIS_THRESHOLD),
		// 			true, true),
		// 		drivetrain));
			
		drivetrain.setDefaultCommand(new RunCommand(
			() -> drivetrain.drive(
				MathUtil.applyDeadband((driver.getLeftY() * Math.abs(driver.getLeftY()))*.25, JOYSTICK_Y_AXIS_THRESHOLD),
				MathUtil.applyDeadband((driver.getLeftX() * Math.abs(driver.getLeftX()))*.25, JOYSTICK_X_AXIS_THRESHOLD),
				-MathUtil.applyDeadband((driver.getRightX() * Math.abs(driver.getRightX()))*1, JOYSTICK_Z_AXIS_THRESHOLD),
				true, false), drivetrain));
		
/*		roller.setDefaultCommand(new RollerStopForever(roller)); // we stop by default

		compressor.checkCompressor(); //we compress in the background

		indicator.setDefaultCommand(new IndicatorScrollRainbow(indicator)); // temp
*/
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by
	 * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
	 * subclasses ({@link
	 * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
	 * passing it to a
	 * {@link JoystickButton}.
	 */

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
		

	/*public Trajectory createExampleTrajectory() {
		// An example trajectory to follow. All units in meters.
		Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
			// Start at the origin facing the +X direction
			new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
			// Pass through these two interior waypoints, making an 's' curve path
			List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
			// End 3 meters straight ahead of where we started, facing forward
			new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
			createTrajectoryConfig());

		return exampleTrajectory;
	}*/
	
	/*public Command createSwerveControllerCommand(Trajectory trajectory) {

		ProfiledPIDController thetaController = new ProfiledPIDController(
			AutoConstants.THETA_CONTROLLER_P, 0, 0, AutoConstants.THETA_CONTROLLER_CONSTRAINTS);
			
		thetaController.enableContinuousInput(-Math.PI, Math.PI);

		SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
			trajectory, // trajectory to follow
			drivetrain::getPose, // Functional interface to feed supplier
			DrivetrainConstants.DRIVE_KINEMATICS, // kinematics of the drivetrain
			new PIDController(AutoConstants.X_CONTROLLER_P, 0, 0), // trajectory tracker PID controller for x position
			new PIDController(AutoConstants.Y_CONTROLLER_P, 0, 0), // trajectory tracker PID controller for y position
			thetaController, // trajectory tracker PID controller for rotation
			drivetrain::setModuleStates, // raw output module states from the position controllers
			drivetrain); // subsystems to require

		// Reset odometry to the starting pose of the trajectory.
		drivetrain.resetOdometry(trajectory.getInitialPose()); // WARNING: https://github.com/REVrobotics/MAXSwerve-Java-Template/issues/13

		field.getObject("trajectory").setTrajectory(trajectory);

		// Run path following command, then stop at the end.
		return swerveControllerCommand.andThen(() -> drivetrain.drive(0, 0, 0, false, false));
	}*/

	public Field2d getField()
	{
		return field;
	}

	// public HMAccelerometer getAccelerometer()
	// {
	// 	return accelerometer;
	// }

	public SwerveDrivetrain getDrivetrain()
	{
		return drivetrain;
	}
/*

	public Elevator getElevator()
	{
		return elevator;
	}

	public Drawer getDrawer()
	{
		return drawer;
	}

	public Neck getNeck()
	{
		return neck;
	}

	public Roller getRoller()
	{
		return roller;
	}

	public Mouth getMouth()
	{
		return mouth;
	}
*/
}
