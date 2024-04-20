// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.IntakeBeamBreak;

public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	private RobotContainer m_robotContainer;
	private IntakeBeamBreak m_intakeBeamBreak;
	// Just contains shuffle board functions so they can be in a different file
	private SmartDashBoardUpdater m_smartDashBoardUpdater;

	private double timer;
	public boolean noteSensorBoolean;
	private boolean noteSensorBooleanLast;

	public static enum modeChoices {
		AMP,
		SPEAKER
	}


	@Override
	public void robotInit() {
		m_robotContainer = new RobotContainer();
		m_intakeBeamBreak = new IntakeBeamBreak();
		m_smartDashBoardUpdater = new SmartDashBoardUpdater();
		m_smartDashBoardUpdater.initSmartDash(m_robotContainer);
	}


	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		m_smartDashBoardUpdater.updateToSmartDash(m_robotContainer);
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();
		m_robotContainer.resetSpeed();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	@Override
	public void autonomousPeriodic() {
		m_smartDashBoardUpdater.updateToSmartDash(m_robotContainer);
	}

	@Override
	public void teleopInit() {
		m_robotContainer.resetSpeed();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {

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

	

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {}
}
