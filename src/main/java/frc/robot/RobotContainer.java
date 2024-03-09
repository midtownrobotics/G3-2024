// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
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

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.commands.ChangeSpeed;
import frc.robot.commands.Climb;
import frc.robot.commands.IntakeOuttake;
import frc.robot.commands.PivotIntake;
import frc.robot.commands.PivotOuttake;
import frc.robot.commands.RunFlywheel;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunOuttake;
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
	private final PneumaticsControlModule pcm = new PneumaticsControlModule(1);
	private final DoubleSolenoid PCM01 = new DoubleSolenoid(1, PneumaticsModuleType.CTREPCM, 0, 1);

	public static final double JOYSTICK_X1_AXIS_THRESHOLD = 0.1;
	public static final double JOYSTICK_Y1_AXIS_THRESHOLD = 0.1;
	public static final double JOYSTICK_X2_AXIS_THRESHOLD = 0.1;
	public static final double JOYSTICK_Y2_AXIS_THRESHOLD = 0.1;

	private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	private final Climber climber = new Climber(CAN50, CAN51, DIO0, DIO1);
	private final Outtake outtake = new Outtake(CAN33, CAN32, CAN30, CAN31, CAN34, DIO2);
	private final Intake intake = new Intake(CAN41, CAN40, PCM01);
	public void resetSpeed() {
		outtake.setSpeed(0);
	}

	private final Field2d field = new Field2d(); //  a representation of the field

	CommandXboxController driver = new CommandXboxController(Ports.USB.DRIVER_CONTROLLER);
	CommandXboxController operator = new CommandXboxController(Ports.USB.OPERATOR_CONTROLLER);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {

		// Configure the button bindings

		pcm.enableCompressorDigital();

		configureButtonBindings();
		

		double control_limiter = 1.0;
		
			
		drivetrain.setDefaultCommand(new RunCommand(
			() -> drivetrain.drive(
				MathUtil.applyDeadband((driver.getLeftY() * Math.abs(driver.getLeftY()))*control_limiter, JOYSTICK_Y1_AXIS_THRESHOLD),
				MathUtil.applyDeadband((driver.getLeftX() * Math.abs(driver.getLeftX()))*control_limiter, JOYSTICK_X1_AXIS_THRESHOLD),
				-MathUtil.applyDeadband((driver.getRightX() * Math.abs(driver.getRightX()))*control_limiter, JOYSTICK_X2_AXIS_THRESHOLD),
		 		true, false), drivetrain));
		climber.setDefaultCommand(new Climb(climber, operator));
		outtake.setDefaultCommand(new RunFlywheel(outtake));
		
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
		driver.x().whileTrue(new RunCommand(() -> drivetrain.setX(), drivetrain));
		operator.povUp().whileTrue(new PivotOuttake(outtake, .75));
		operator.povDown().whileTrue(new PivotOuttake(outtake, -.75));
		operator.rightBumper().whileTrue(new RunIntake(intake, 1));
		operator.leftBumper().whileTrue(new RunIntake(intake, -1));
		operator.leftTrigger(.1).whileTrue(new RunOuttake(outtake, -1));
		operator.rightTrigger(.1).whileTrue(new IntakeOuttake(intake, outtake, .75));
		operator.a().whileTrue(new ChangeSpeed(outtake, 1));
		operator.b().whileTrue(new ChangeSpeed(outtake, 0));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		// just taxi
		// return new RunCommand(() -> drivetrain.drive(-.5, 0, 0, true, false), drivetrain).withTimeout(2);
		// shoot & intake (from subwoofer)
		return new SequentialCommandGroup(
			new ChangeSpeed(outtake, 1).withTimeout(1),
			new IntakeOuttake(intake, outtake, .75).withTimeout(1),
			new ChangeSpeed(outtake, 0),
			new RunIntake(intake, 1).alongWith(new RunCommand(() -> drivetrain.drive(.5, 0, 0), drivetrain)).withTimeout(2)
		);
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
}
