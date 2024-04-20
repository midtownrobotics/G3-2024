package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot.modeChoices;

public class SmartDashBoardUpdater {
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
	private static GenericEntry shooterSpeedSlider = shooterTab.add("Speed", 800).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 10000)).getEntry();
	private GenericEntry shooterRightTargetBox;
	private GenericEntry shooterLeftTargetBox;

	private static GenericEntry shooterLP;
	private static GenericEntry shooterLD;
	private static GenericEntry shooterRP;
	private static GenericEntry shooterRD;
	private static GenericEntry shooterLFF;
	private static GenericEntry shooterRFF;

    private static SendableChooser<modeChoices> modeChooser = new SendableChooser<>();
	private static GenericEntry pivotP;
	private static GenericEntry pivotD;
	private static GenericEntry pivotAngle;
	private static GenericEntry stop;

    public void initSmartDash(RobotContainer m_robotContainer) {
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


    public void updateToSmartDash(RobotContainer m_robotContainer)
	{
		SmartDashboard.putNumber("FrontLeftDrivingEncoderPosition", m_robotContainer.getDrivetrain().getFrontLeftModule().getDrivingEncoder().getPosition());
		SmartDashboard.putNumber("FrontLeftTurningEncoderPosition", m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningEncoder().getPosition());
		SmartDashboard.putNumber("RearLeftDrivingEncoderPosition", m_robotContainer.getDrivetrain().getRearLeftModule().getDrivingEncoder().getPosition());
		SmartDashboard.putNumber("RearLeftTurningEncoderPosition", m_robotContainer.getDrivetrain().getRearLeftModule().getTurningEncoder().getPosition());
		SmartDashboard.putNumber("FrontRightDrivingEncoderPosition", m_robotContainer.getDrivetrain().getFrontRightModule().getDrivingEncoder().getPosition());
		SmartDashboard.putNumber("FrontRightTurningEncoderPosition", m_robotContainer.getDrivetrain().getFrontRightModule().getTurningEncoder().getPosition());
		SmartDashboard.putNumber("RearRightDrivingEncoderPosition", m_robotContainer.getDrivetrain().getRearRightModule().getDrivingEncoder().getPosition());
		SmartDashboard.putNumber("RearRightTurningEncoderPosition", m_robotContainer.getDrivetrain().getRearRightModule().getTurningEncoder().getPosition());
	
		SmartDashboard.putNumber("FL Rel", m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("RL Rel", m_robotContainer.getDrivetrain().getRearLeftModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("FR Rel", m_robotContainer.getDrivetrain().getFrontRightModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("RR Rel", m_robotContainer.getDrivetrain().getRearRightModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("FL Abs", m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningAbsoluteEncoder().getAbsolutePosition());
		SmartDashboard.putNumber("RL Abs", m_robotContainer.getDrivetrain().getRearLeftModule().getTurningAbsoluteEncoder().getAbsolutePosition());
		SmartDashboard.putNumber("FR Abs", m_robotContainer.getDrivetrain().getFrontRightModule().getTurningAbsoluteEncoder().getAbsolutePosition());
		SmartDashboard.putNumber("RR Abs", m_robotContainer.getDrivetrain().getRearRightModule().getTurningAbsoluteEncoder().getAbsolutePosition());

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
		m_robotContainer.getField().setRobotPose(m_robotContainer.getDrivetrain().getPose());
		SmartDashboard.putNumber(   "Heading",             m_robotContainer.getDrivetrain().getHeading());
		SmartDashboard.putNumber("yaw", m_robotContainer.getDrivetrain().pigeon.getYaw());

		SmartDashboard.putNumber("Pivot Encoder", m_robotContainer.getOuttake().getPivot());
		SmartDashboard.putNumber("Angle", m_robotContainer.getOuttake().getAngle());


		IT.setDouble(m_robotContainer.getIntake().getInternalMotorTemp());
		CRT.setDouble(m_robotContainer.getClimber().getRightMotorTemp());
		CLT.setDouble(m_robotContainer.getClimber().getLeftMotorTemp());
		SRT.setDouble(m_robotContainer.getOuttake().getRightWheelMotorTemp());
		SLT.setDouble(m_robotContainer.getOuttake().getLeftWheelMotorTemp());
	}
}
