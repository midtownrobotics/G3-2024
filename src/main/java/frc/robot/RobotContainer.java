// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrajectoryConfig;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController; 
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;

import frc.robot.subsystems.SwerveDrivetrain;


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

	public static final int LX = 0;
	public static final int LY = 1;
	public static final int LT = 2;
	public static final int RT = 3;
	public static final int RX = 4;
	public static final int RY = 5;

	// choosers (for auton)
	
	public static final String AUTON_DO_NOTHING = "Do Nothing";
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
	private SendableChooser<String> autonOptionChooser = new SendableChooser<>();

	private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();

	private final Field2d field = new Field2d(); //  a representation of the field

	CommandJoystick joyMain = new CommandJoystick(Ports.USB.MAIN_JOYSTICK);
	XboxController driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);
	CommandXboxController copilotGamepad = new CommandXboxController(Ports.USB.COPILOT_GAMEPAD);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {

		// choosers (for auton)
		
		autonChooser.setDefaultOption("Do Nothing", AUTON_DO_NOTHING);
		autonChooser.addOption("My Auto", AUTON_CUSTOM);
		autonChooser.addOption("Sample Swerve", AUTON_SAMPLE_SWERVE);
		autonChooser.addOption("Sample Move Forward", AUTON_SAMPLE_MOVE_FORWARD);
		autonChooser.addOption("Sample Move In Reverse", AUTON_SAMPLE_MOVE_IN_REVERSE);
		autonChooser.addOption("Sample Move In Gamma Shape", AUTON_SAMPLE_MOVE_IN_GAMMA_SHAPE);
		autonChooser.addOption("Sample Move In L Shape In Reverse", AUTON_SAMPLE_MOVE_IN_L_SHAPE_IN_REVERSE);
		autonChooser.addOption("Test Hardcoded Move 1", AUTON_TEST_HARDCODED_MOVE_1);
		autonChooser.addOption("Test Hardcoded Move 2", AUTON_TEST_HARDCODED_MOVE_2);
		SmartDashboard.putData("Auto choices", autonChooser);

		gamePieceChooser.setDefaultOption("None", GAME_PIECE_NONE);
		gamePieceChooser.addOption("1 Cone", GAME_PIECE_1_CONE);
		gamePieceChooser.addOption("2 Cones", GAME_PIECE_2_CONES);
		gamePieceChooser.addOption("1 Cube", GAME_PIECE_1_CUBE);
		gamePieceChooser.addOption("2 Cubes", GAME_PIECE_2_CUBES);
		SmartDashboard.putData("Game piece choices", gamePieceChooser);

		startPositionChooser.setDefaultOption("Starting Position 1", START_POSITION_1);
		startPositionChooser.addOption("Starting Position 2", START_POSITION_2);
		startPositionChooser.addOption("Starting Position 3", START_POSITION_3);
		startPositionChooser.addOption("Starting Position 4", START_POSITION_4);
		startPositionChooser.addOption("Starting Position 5", START_POSITION_5);
		startPositionChooser.addOption("Starting Position 6", START_POSITION_6);
		SmartDashboard.putData("Start positions", startPositionChooser);

		mainTargetChooser.setDefaultOption("To Nowhere", MAIN_TARGET_NOWHERE);
		mainTargetChooser.addOption("Cone Node", MAIN_TARGET_CONE_NODE);
		mainTargetChooser.addOption("Cube Node", MAIN_TARGET_CUBE_NODE);
		mainTargetChooser.addOption("Two Cone Nodes", MAIN_TARGET_TWO_CONE_NODES);
		mainTargetChooser.addOption("Two Cube Nodes", MAIN_TARGET_TWO_CUBE_NODES);
		mainTargetChooser.addOption("Charging Station", MAIN_TARGET_CHARGING_STATION);
		SmartDashboard.putData("Main targets", mainTargetChooser);
		
		cameraOptionChooser.setDefaultOption("Always", CAMERA_OPTION_USE_ALWAYS);
		cameraOptionChooser.addOption("Open Loop Only", CAMERA_OPTION_USE_OPEN_LOOP_ONLY);
		cameraOptionChooser.addOption("Closed Loop Only", CAMERA_OPTION_USE_CLOSED_LOOP_ONLY);
		cameraOptionChooser.addOption("Never", CAMERA_OPTION_USE_NEVER);		
		SmartDashboard.putData("Camera options", cameraOptionChooser);
		
		sonarOptionChooser.setDefaultOption("Always", SONAR_OPTION_USE_ALWAYS);
		sonarOptionChooser.addOption("Release Only", SONAR_OPTION_USE_RELEASE_ONLY);
		sonarOptionChooser.addOption("Grasp Only", SONAR_OPTION_USE_GRASP_ONLY);		
		sonarOptionChooser.addOption("Never", SONAR_OPTION_USE_NEVER);
		SmartDashboard.putData("Sonar options", sonarOptionChooser);
		
		releaseChooser.setDefaultOption("Release", CLAW_OPTION_RELEASE);
		releaseChooser.addOption("Don't release", CLAW_OPTION_DONT_RELEASE);
		SmartDashboard.putData("Release options", releaseChooser);

		autonOptionChooser.setDefaultOption("Just Drop Cone", AUTON_OPTION_JUST_DROP_CONE);
		autonOptionChooser.setDefaultOption("Just Drop Cube", AUTON_OPTION_JUST_DROP_CUBE);
		autonOptionChooser.addOption("Also Dock", AUTON_OPTION_ALSO_DOCK);
		autonOptionChooser.addOption("Leave Community", AUTON_OPTION_LEAVE_COMMUNITY);
		autonOptionChooser.addOption("Also Pickup Cone", AUTON_OPTION_ALSO_PICKUP_CONE);
		autonOptionChooser.addOption("Also Pickup Cube", AUTON_OPTION_ALSO_PICKUP_CUBE);
	
		SmartDashboard.putData("Auton options", autonOptionChooser);
		

		// Configure the button bindings

		configureButtonBindings();
			
		drivetrain.setDefaultCommand(new RunCommand(
			() -> drivetrain.drive(
				MathUtil.applyDeadband((driver.getLeftY() * Math.abs(driver.getLeftY()))*.25, JOYSTICK_Y_AXIS_THRESHOLD),
				MathUtil.applyDeadband((driver.getLeftX() * Math.abs(driver.getLeftX()))*.25, JOYSTICK_X_AXIS_THRESHOLD),
				-MathUtil.applyDeadband((driver.getRightX() * Math.abs(driver.getRightX()))*1, JOYSTICK_Z_AXIS_THRESHOLD),
				true, false), drivetrain));
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
	private void configureButtonBindings() {

	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		autonSelected = autonChooser.getSelected();
		System.out.println("Auton selected: " + autonSelected);	

		gamePieceSelected = gamePieceChooser.getSelected();
		System.out.println("Game piece selected: " + gamePieceSelected);		

		startPosition = startPositionChooser.getSelected();
		System.out.println("Start position: " + startPosition);

		mainTarget = mainTargetChooser.getSelected();
		System.out.println("Main target: " + mainTarget);
		
		cameraOption = cameraOptionChooser.getSelected();
		System.out.println("Camera option: " + cameraOption);
		
		sonarOption = sonarOptionChooser.getSelected();
		System.out.println("Sonar option: " + sonarOption);
		
		releaseSelected = releaseChooser.getSelected();
		System.out.println("Release chosen: " + releaseSelected);

		autonOption = autonOptionChooser.getSelected();
		System.out.println("Auton option: " + autonOption);
		

		switch (autonSelected) {
			case AUTON_SAMPLE_SWERVE:

			case AUTON_DO_NOTHING:
				return null;
				//break;
				
			default:
				// nothing
				return null;
				//break;
		} // end switch
	}

	public TrajectoryConfig createTrajectoryConfig() {
		// Create config for trajectory
		TrajectoryConfig config = new TrajectoryConfig(
			AutoConstants.MAX_SPEED_METERS_PER_SECOND,
			AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
			// Add kinematics to ensure max speed is actually obeyed
			.setKinematics(DrivetrainConstants.DRIVE_KINEMATICS);

		return config;
	}

	public TrajectoryConfig createReverseTrajectoryConfig() {

		TrajectoryConfig config = createTrajectoryConfig();

		config.setReversed(true); // in reverse!

		return config;
	}

	public Field2d getField()
	{
		return field;
	}

	public SwerveDrivetrain getDrivetrain()
	{
		return drivetrain;
	}
	
	public Joystick getMainJoystick()
	{
		return joyMain.getHID();
	}

	public XboxController getCopilotGamepad()
	{
		return copilotGamepad.getHID();
	}

	public SendableChooser<String> getAutonChooser()
	{
		return autonChooser;
	}
	
	public SendableChooser<String> getGamePieceChooser()
	{
		return gamePieceChooser;
	}

	public SendableChooser<String> getStartPositionChooser()
	{
		return startPositionChooser;
	}

	public SendableChooser<String> getMainTargetChooser()
	{
		return mainTargetChooser;
	}

	public SendableChooser<String> getCameraOptionChooser()
	{
		return cameraOptionChooser;
	}

	public SendableChooser<String> getSonarOptionChooser()
	{
		return sonarOptionChooser;
	}

	public SendableChooser<String> getReleaseChooser()
	{
		return releaseChooser;
	}

	public SendableChooser<String> getAutonOptionChooser()
	{
		return autonOptionChooser;
	}
}
