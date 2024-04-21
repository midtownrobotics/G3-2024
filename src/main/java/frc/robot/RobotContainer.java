// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.commands.BoostSpeed;
import frc.robot.commands.ChangeSpeed;
import frc.robot.commands.Climb;
import frc.robot.commands.DoNothing;
import frc.robot.commands.IntakeOuttake;
import frc.robot.commands.PivotOuttake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunOuttake;
import frc.robot.commands.SpeedPID;
import frc.robot.commands.IntervalAdjustSpeed;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;
import frc.robot.subsystems.SwerveDrivetrain;


/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

	private final CANSparkMax CAN30 = new CANSparkMax(30, MotorType.kBrushless);
	private final CANSparkMax CAN31 = new CANSparkMax(31, MotorType.kBrushless);
	private final CANSparkMax CAN32 = new CANSparkMax(32, MotorType.kBrushless);
	private final CANSparkMax CAN33 = new CANSparkMax(33, MotorType.kBrushless);
	private final CANSparkMax CAN34 = new CANSparkMax(34, MotorType.kBrushless);
	private final CANSparkMax CAN40 = new CANSparkMax(40, MotorType.kBrushless);
	private final CANSparkMax CAN41 = new CANSparkMax(41, MotorType.kBrushless);
	// private final CANSparkMax CAN42 = new CANSparkMax(42, MotorType.kBrushless);
	private final CANSparkMax CAN50 = new CANSparkMax(50, MotorType.kBrushless);
	private final CANSparkMax CAN51 = new CANSparkMax(51, MotorType.kBrushless);
	private final DigitalInput DIO0 = new DigitalInput(0);
	private final DigitalInput DIO1 = new DigitalInput(1);
	private final DigitalInput DIO2 = new DigitalInput(2);
	private final DigitalInput DIO6 = new DigitalInput(6);
	private final PneumaticsControlModule pcm = new PneumaticsControlModule(1);
	private final DoubleSolenoid PCM01 = new DoubleSolenoid(1, PneumaticsModuleType.CTREPCM, 0, 1);

	public static final double JOYSTICK_X1_AXIS_THRESHOLD = 0.1;
	public static final double JOYSTICK_Y1_AXIS_THRESHOLD = 0.1;
	public static final double JOYSTICK_X2_AXIS_THRESHOLD = 0.1;
	public static final double JOYSTICK_Y2_AXIS_THRESHOLD = 0.1;

	private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	private final Climber climber = new Climber(CAN50, CAN51, DIO0, DIO1);
	private final Outtake outtake = new Outtake(CAN33, CAN32, CAN30, CAN31, CAN34, DIO2);
	private final Intake intake = new Intake(CAN41, CAN40, PCM01, DIO6);
	public void resetSpeed() {
		outtake.setSpeed(0);
	}

	private final Field2d field = new Field2d(); //  a representation of the field

	CommandXboxController driver = new CommandXboxController(Ports.USB.DRIVER_CONTROLLER);
	CommandXboxController operator = new CommandXboxController(Ports.USB.OPERATOR_CONTROLLER);

	public static enum Auton {
		STRAIGHT_TAXI,
		SHOOT,
		SHOOT_STRAIGHT_TAXI,
		TWO_NOTE
	}

	private final ShuffleboardTab autonTab = Shuffleboard.getTab("Auton");
	private final SendableChooser<Auton> autonChooser = new SendableChooser<>();
	private final GenericEntry intakeTimer;

	public static boolean doSpeedBoost = false;
	

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {

		intakeTimer = Shuffleboard.getTab("External").add("Intake Timer", 0).withWidget(BuiltInWidgets.kTextView).getEntry();

		autonChooser.setDefaultOption("Straight Taxi", Auton.STRAIGHT_TAXI);
		autonChooser.addOption("Shoot", Auton.SHOOT);
		autonChooser.addOption("Shoot & Straight Taxi", Auton.SHOOT_STRAIGHT_TAXI);
		autonChooser.addOption("Two Note", Auton.TWO_NOTE);
		autonTab.add("Auton Mode Chooser", autonChooser).withSize(2, 1);

		// Configure the button bindings

		pcm.enableCompressorDigital();

		configureButtonBindings();
		

		double control_limiter = 1;
		
			
		drivetrain.setDefaultCommand(new RunCommand(
			() -> drivetrain.drivePID(
				RobotContainer.deadzone(driver.getLeftY(), driver.getLeftX(), driver.getRightX(), JOYSTICK_Y1_AXIS_THRESHOLD)*control_limiter,
				RobotContainer.deadzone(driver.getLeftX(), driver.getLeftY(), driver.getRightX(), JOYSTICK_X1_AXIS_THRESHOLD)*control_limiter,
				RobotContainer.deadzone(driver.getRightX(), driver.getLeftY(), driver.getLeftX(), JOYSTICK_X2_AXIS_THRESHOLD)*control_limiter,
		 		doSpeedBoost), drivetrain));
		climber.setDefaultCommand(new Climb(climber, operator));
		outtake.setDefaultCommand(new SpeedPID(outtake));
	}

	// public double getDistanceThing () {
	// 	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	// 	NetworkTableEntry ty = table.getEntry("ty");
	// 	double targetOffsetAngle_Vertical = ty.getDouble(0.0);	

	// 	// how many degrees back is your limelight rotated from perfectly vertical?
	// 	double limelightMountAngleDegrees = 25.0; 

	// 	// distance from the center of the Limelight lens to the floor
	// 	double limelightLensHeightInches = 20.0; 

	// 	// distance from the target to the floor
	// 	double goalHeightInches = 60.0; 

	// 	double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
	// 	double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

	// 	//calculate distance
	// 	return table.getEntry("tv").getDouble(0.0);
		
	// }

	public static double deadzone(double a, double b, double c, double zone) {
		if (Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2)+Math.pow(c, 2)) > zone) {
			return a * Math.abs(a);
		} else {
			return 0;
		}
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by
	 * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
	 * subclasses ({@link
	 * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
	 * passing it to a
	 * {@link JoystickButton}
	 */
	private void configureButtonBindings() {
		driver.x().whileTrue(new RunCommand(() -> drivetrain.setX(), drivetrain));
		driver.leftTrigger(.1).whileTrue(new BoostSpeed());
		driver.a().whileTrue(new RunCommand(() -> drivetrain.zeroHeading(), drivetrain));
		operator.povUp().whileTrue(new PivotOuttake(outtake, true));
		operator.povDown().whileTrue(new PivotOuttake(outtake, false));
		operator.povRight().whileTrue(new IntervalAdjustSpeed(outtake, true));
		operator.povLeft().whileTrue(new IntervalAdjustSpeed(outtake, false));
		operator.rightBumper().whileTrue(new RunIntake(intake, outtake, 1));
		operator.leftBumper().whileTrue(new RunIntake(intake, outtake, -1));
		operator.leftTrigger(.1).whileTrue(new RunOuttake(outtake, -1));
		operator.rightTrigger(.1).whileTrue(new IntakeOuttake(intake, outtake, .75));
		operator.a().whileTrue(new ChangeSpeed(outtake, OuttakeConstants.SPEAKER_SPEED, "speaker"));
		operator.y().whileTrue(new ChangeSpeed(outtake, OuttakeConstants.SPEAKER_SPEED, "bottom"));
		operator.x().whileTrue(new ChangeSpeed(outtake, OuttakeConstants.AMP_SPEED, "amp"));
		operator.b().whileTrue(new ChangeSpeed(outtake, 0, "stop"));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 * 
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		Command autoCommand = new DoNothing();
		switch (autonChooser.getSelected()) {
			case SHOOT:
				autoCommand = new SequentialCommandGroup(
					new ChangeSpeed(outtake, OuttakeConstants.SPEAKER_SPEED, "speaker").withTimeout(0.1),
					new SpeedPID(outtake).withTimeout(2),
					new IntakeOuttake(intake, outtake, .75).withTimeout(1),
					new ChangeSpeed(outtake, 0, "stop").withTimeout(0.1),
					new SpeedPID(outtake).withTimeout(0.1)
				);
				break;
			case STRAIGHT_TAXI:
				autoCommand = new RunCommand(() -> drivetrain.drive(-.5, 0, 0, false), drivetrain).withTimeout(2);
				break;
			case SHOOT_STRAIGHT_TAXI:
				autoCommand = new SequentialCommandGroup(
					new ChangeSpeed(outtake, OuttakeConstants.SPEAKER_SPEED, "speaker").withTimeout(0.1),
					new SpeedPID(outtake).withTimeout(2),
					new IntakeOuttake(intake, outtake, .75).withTimeout(2),
					new ChangeSpeed(outtake, 0, "stop").withTimeout(0.1),
					new SpeedPID(outtake).withTimeout(0.1),
					new RunIntake(intake, outtake, .67).alongWith(new RunCommand(() -> drivetrain.drive(-.5, 0, 0, false), drivetrain).withTimeout(1.9)).withTimeout(1.9)
				);
				break;
			case TWO_NOTE:
				autoCommand = new SequentialCommandGroup(
					new ChangeSpeed(outtake, OuttakeConstants.SPEAKER_SPEED, "speaker").withTimeout(2.1),
					new SpeedPID(outtake).withTimeout(2),
					new IntakeOuttake(intake, outtake, .75).withTimeout(2),
					new RunIntake(intake, outtake, .67).alongWith(new RunCommand(() -> drivetrain.drive(-.5, 0, 0, false), drivetrain).withTimeout(1.9)).withTimeout(1.9),
					new RunCommand(() -> drivetrain.drive(0, 0, 0, false), drivetrain).withTimeout(0),
					new RunCommand(() -> drivetrain.drive(.5, 0, 0, false), drivetrain).alongWith(new SpeedPID(outtake).withTimeout(2.8)).withTimeout(2.3),	
					new IntakeOuttake(intake, outtake, .75).withTimeout(2.1),
					new ChangeSpeed(outtake, 0, "stop").withTimeout(0.1),
					new SpeedPID(outtake).withTimeout(0.1)
				);
				break;
			default:
				break;
		}
		return autoCommand;
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

	public Outtake getOuttake() {
		return outtake;
	}

	public Intake getIntake() {
		return intake;
	}

	public Climber getClimber() {
		return climber;
	}
}
