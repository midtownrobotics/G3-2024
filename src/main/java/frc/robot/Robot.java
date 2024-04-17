// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.net.PortForwarder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeBeamBreak;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	private RobotContainer m_robotContainer;
	private IntakeBeamBreak m_intakeBeamBreak;

	private double timer;
	public boolean noteSensorBoolean;
	private boolean noteSensorBooleanLast;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	
	private GenericEntry IT;
	private GenericEntry CRT;
	private GenericEntry CLT;
	private GenericEntry SRT;
	private GenericEntry SLT;
	private GenericEntry noteSensorShuffleBox;
	private GenericEntry speedBoostShuffleBox;
	private GenericEntry shooterLeftSpeedShuffleBox;
	private GenericEntry shooterRightSpeedShuffleBox;
	private GenericEntry shooterOnOffShuffleBox;
	private GenericEntry shooterAngle;
	private static ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
	public static GenericEntry shooterSpeedSlider = shooterTab.add("Speed", 800).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 10000)).getEntry();
	private GenericEntry shooterRightTargetBox;
	private GenericEntry shooterLeftTargetBox;

	public static GenericEntry shooterLP;
	public static GenericEntry shooterLD;
	public static GenericEntry shooterRP;
	public static GenericEntry shooterRD;
	public static GenericEntry shooterLFF;
	public static GenericEntry shooterRFF;

	public static enum modeChoices {
		AMP,
		SPEAKER
	}

	public static SendableChooser<modeChoices> modeChooser = new SendableChooser<>();
	public static GenericEntry pivotP;
	public static GenericEntry pivotD;
	public static GenericEntry pivotAngle;
	public static GenericEntry stop;

	@Override
	public void robotInit() {
		// Port forwarders for LimeLight
		// Do not place these function calls in any periodic functions
		// PortForwarder.add(5800, "limelight.local", 5800);
		// PortForwarder.add(5801, "limelight.local", 5801);
		// PortForwarder.add(5802, "limelight.local", 5802);
		// PortForwarder.add(5803, "limelight.local", 5803);
		// PortForwarder.add(5804, "limelight.local", 5804);
		// PortForwarder.add(5805, "limelight.local", 5805);

		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();
		m_intakeBeamBreak = new IntakeBeamBreak();

		SmartDashboard.putData("Swerve Odometry", m_robotContainer.getField());		

		ShuffleboardTab tempTab = Shuffleboard.getTab("Motor Tempuratures");

		IT = tempTab.add("Intake T", 0).getEntry();
		CRT = tempTab.add("Climber R T", 0).getEntry();
		CLT = tempTab.add("Climber L T", 0).getEntry();
		SRT = tempTab.add("Shooter R T", 0).getEntry();
		SLT = tempTab.add("Shooter L T", 0).getEntry();

		ShuffleboardTab pivotTab = Shuffleboard.getTab("Pivot");

		pivotP = pivotTab.add("Pivot P", 0).getEntry();
		pivotD = pivotTab.add("Pivot D", 0).getEntry();
		pivotAngle = pivotTab.add("Pivot Angle", 0.872).getEntry();
		stop = pivotTab.add("Stop", false).getEntry();

		

		ShuffleboardTab gameTab = Shuffleboard.getTab("Game");

		noteSensorShuffleBox = gameTab.add("Note Detected", false).withSize(2, 2).withPosition(6, 2).getEntry();
		speedBoostShuffleBox = gameTab.add("Boosting Speed", false).withSize(2, 2).withPosition(8, 2).getEntry();
		shooterLeftSpeedShuffleBox = gameTab.add("Shooter Left Speed", 0).withWidget(BuiltInWidgets.kDial).withSize(2, 2).withProperties(Map.of("min", 0, "max", 8000)).withPosition(6, 0).getEntry();
		shooterRightSpeedShuffleBox = gameTab.add("Shooter Right Speed", 0).withWidget(BuiltInWidgets.kDial).withSize(2, 2).withProperties(Map.of("min", 0, "max", 8000)).withPosition(8, 0).getEntry();
		shooterOnOffShuffleBox = gameTab.add("Shooter On Off", false).withSize(2, 2).withPosition(4, 0).getEntry();
		shooterAngle = gameTab.add("Shooter Angle", 0).getEntry();

		shooterLeftTargetBox = gameTab.add("Shooter L Target", 0).withWidget(BuiltInWidgets.kGraph).withPosition(4, 2).getEntry();
		shooterRightTargetBox = gameTab.add("Shooter R Target", 0).withWidget(BuiltInWidgets.kGraph).withPosition(5, 2).getEntry();

		gameTab.addCamera("Camera", "Camera", "http://10.16.48.11:5800/").withPosition(0, 0).withSize(4, 4);

		modeChooser.setDefaultOption("Amp", modeChoices.AMP);
		modeChooser.addOption("Speaker", modeChoices.SPEAKER);
		shooterTab.add("Mode", modeChooser).withSize(2, 1);

		shooterLP = shooterTab.add("L P", 0).getEntry();
		shooterLD = shooterTab.add("L D", 0).getEntry();
		shooterRP = shooterTab.add("R P", 0).getEntry();
		shooterRD = shooterTab.add("R D", 0).getEntry();

		shooterLFF = shooterTab.add("L FF", 0).getEntry();
		shooterRFF = shooterTab.add("R FF",0).getEntry();

	}

	/**
	 * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
	 * that you want ran during disabled, autonomous, teleoperated and test.
	 *
	 * <p>This runs after the mode specific periodic functions, but before LiveWindow and
	 * SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.  This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		// m_robotContainer.getDistanceThing();
		CommandScheduler.getInstance().run();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {

		updateToSmartDash();
	}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();
		m_robotContainer.resetSpeed();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {

		updateToSmartDash();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		m_robotContainer.resetSpeed();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
		
		updateToSmartDash();

		timer ++;

		noteSensorBoolean = m_robotContainer.getIntake().getNoteSensor();

		if (noteSensorBoolean != noteSensorBooleanLast) {
			noteSensorBooleanLast = noteSensorBoolean;
			if (noteSensorBoolean) {
				m_intakeBeamBreak.onTrue();
				timer = 0;
			}
		}

		double rumbleTimeSeconds = 0.25;

		if (timer >= (rumbleTimeSeconds * 1000 / 20)) {
			m_intakeBeamBreak.stop();
		}
	}

	public void updateToSmartDash()
	{


		SmartDashboard.putNumber("limelight x", m_robotContainer.getLimelight().getX());
		SmartDashboard.putNumber("limelight y", m_robotContainer.getLimelight().getY());
		SmartDashboard.putNumber("limelight z", m_robotContainer.getLimelight().getZ());
		SmartDashboard.putNumber("Angle Offset", Math.toDegrees(m_robotContainer.getLimelight().getAngleOffset()));


		// SmartDashboard.putNumber("FrontLeftDrivingEncoderPosition", m_robotContainer.getDrivetrain().getFrontLeftModule().getDrivingEncoder().getPosition());
		// SmartDashboard.putNumber("FrontLeftTurningEncoderPosition", m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningEncoder().getPosition());
		
		// SmartDashboard.putNumber("RearLeftDrivingEncoderPosition", m_robotContainer.getDrivetrain().getRearLeftModule().getDrivingEncoder().getPosition());
		// SmartDashboard.putNumber("RearLeftTurningEncoderPosition", m_robotContainer.getDrivetrain().getRearLeftModule().getTurningEncoder().getPosition());
		
		// SmartDashboard.putNumber("FrontRightDrivingEncoderPosition", m_robotContainer.getDrivetrain().getFrontRightModule().getDrivingEncoder().getPosition());
		// SmartDashboard.putNumber("FrontRightTurningEncoderPosition", m_robotContainer.getDrivetrain().getFrontRightModule().getTurningEncoder().getPosition());
		
		// SmartDashboard.putNumber("RearRightDrivingEncoderPosition", m_robotContainer.getDrivetrain().getRearRightModule().getDrivingEncoder().getPosition());
		// SmartDashboard.putNumber("RearRightTurningEncoderPosition", m_robotContainer.getDrivetrain().getRearRightModule().getTurningEncoder().getPosition());
	
		// SmartDashboard.putNumber("FL Rel", m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningAbsoluteEncoder().getPosition());
		// SmartDashboard.putNumber("RL Rel", m_robotContainer.getDrivetrain().getRearLeftModule().getTurningAbsoluteEncoder().getPosition());
		// SmartDashboard.putNumber("FR Rel", m_robotContainer.getDrivetrain().getFrontRightModule().getTurningAbsoluteEncoder().getPosition());
		// SmartDashboard.putNumber("RR Rel", m_robotContainer.getDrivetrain().getRearRightModule().getTurningAbsoluteEncoder().getPosition());

		// SmartDashboard.putNumber("FL Abs", m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningAbsoluteEncoder().getAbsolutePosition());
		// SmartDashboard.putNumber("RL Abs", m_robotContainer.getDrivetrain().getRearLeftModule().getTurningAbsoluteEncoder().getAbsolutePosition());
		// SmartDashboard.putNumber("FR Abs", m_robotContainer.getDrivetrain().getFrontRightModule().getTurningAbsoluteEncoder().getAbsolutePosition());
		// SmartDashboard.putNumber("RR Abs", m_robotContainer.getDrivetrain().getRearRightModule().getTurningAbsoluteEncoder().getAbsolutePosition());

		//SmartDashboard.putBoolean("Note Sensor", m_robotContainer.getIntake().getNoteSensor());
		SmartDashboard.putNumber("Shooter Angle", m_robotContainer.getOuttake().getPivot());
		SmartDashboard.putNumber("Shooter Motor", m_robotContainer.getOuttake().getMotorPivot());

		noteSensorShuffleBox.setBoolean(m_robotContainer.getIntake().getNoteSensor());
		speedBoostShuffleBox.setBoolean(RobotContainer.doSpeedBoost);
		shooterLeftSpeedShuffleBox.setDouble(m_robotContainer.getOuttake().getLeftWheelSpeed() * (61/36));
		shooterRightSpeedShuffleBox.setDouble(m_robotContainer.getOuttake().getRightWheelSpeed() * (61/36));
		shooterLeftTargetBox.setDouble(m_robotContainer.getOuttake().getLeftWheelTarget());
		shooterRightTargetBox.setDouble(m_robotContainer.getOuttake().getRightWheelTarget());
		shooterOnOffShuffleBox.setBoolean(m_robotContainer.getOuttake().getSpeed() > 0.5);
		shooterAngle.setDouble(m_robotContainer.getOuttake().getAngle());
		stop.setBoolean(m_robotContainer.getOuttake().getStop());
		/* 
		SmartDashboard.putString("Mode", modeChooser.getSelected().toString());
	
		// SmartDashboard.putNumber("FrontLeftTurningDesiredState", m_robotContainer.getDrivetrain().getFrontLeftModule().getDesiredState().angle.getRadians());
		// SmartDashboard.putNumber("RearLeftTurningDesiredState", m_robotContainer.getDrivetrain().getRearLeftModule().getDesiredState().angle.getRadians());
		// SmartDashboard.putNumber("FrontRightTurningDesiredState", m_robotContainer.getDrivetrain().getFrontRightModule().getDesiredState().angle.getRadians());
		// SmartDashboard.putNumber("RearRightTurningDesiredState", m_robotContainer.getDrivetrain().getRearRightModule().getDesiredState().angle.getRadians());

		/* Display 6-axis Processed Angle Data                                      
		SmartDashboard.putBoolean(  "IMU_Connected",        m_robotContainer.getDrivetrain().getImu().isConnected());
		SmartDashboard.putBoolean(  "IMU_IsCalibrating",    m_robotContainer.getDrivetrain().getImu().isCalibrating());
		SmartDashboard.putNumber(   "IMU_Yaw",              m_robotContainer.getDrivetrain().getImu().getYaw());
		SmartDashboard.putNumber(   "IMU_Pitch",            m_robotContainer.getDrivetrain().getImu().getPitch());
		SmartDashboard.putNumber(   "IMU_Roll",             m_robotContainer.getDrivetrain().getImu().getRoll());
		*/
		m_robotContainer.getField().setRobotPose(m_robotContainer.getDrivetrain().getPose());
		SmartDashboard.putNumber(   "Heading",             m_robotContainer.getDrivetrain().getHeading());
		SmartDashboard.putNumber("yaw", m_robotContainer.getDrivetrain().pigeon.getYaw());

		SmartDashboard.putNumber("Pivot Encoder", m_robotContainer.getOuttake().getPivot());
		SmartDashboard.putNumber("Angle", m_robotContainer.getOuttake().getAngle());


		// Temps

		// Intake
		IT.setDouble(m_robotContainer.getIntake().getInternalMotorTemp());
		// Climber
		CRT.setDouble(m_robotContainer.getClimber().getRightMotorTemp());
		CLT.setDouble(m_robotContainer.getClimber().getLeftMotorTemp());
		// Shooter
		SRT.setDouble(m_robotContainer.getOuttake().getRightWheelMotorTemp());
		SLT.setDouble(m_robotContainer.getOuttake().getLeftWheelMotorTemp());


		// SmartDashboard.putNumber("distance", m_robotContainer.getDistanceThing());
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {}
}
