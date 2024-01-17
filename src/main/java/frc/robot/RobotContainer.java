// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import frc.robot.subsystems.SwerveDrivetrain;

public class RobotContainer {

	public static final double GAMEPAD_AXIS_THRESHOLD = 0.15;
	public static final double JOYSTICK_X_AXIS_THRESHOLD = 0.15;
	public static final double JOYSTICK_Y_AXIS_THRESHOLD = 0.15;
	public static final double JOYSTICK_Z_AXIS_THRESHOLD = 0.25;

	private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	private final Field2d field = new Field2d(); //  a representation of the field

	XboxController driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);

	public RobotContainer() {

		drivetrain.setDefaultCommand(new RunCommand(
			() -> drivetrain.drive(
				MathUtil.applyDeadband((driver.getLeftY() * Math.abs(driver.getLeftY()))*.25, JOYSTICK_Y_AXIS_THRESHOLD),
				MathUtil.applyDeadband((driver.getLeftX() * Math.abs(driver.getLeftX()))*.25, JOYSTICK_X_AXIS_THRESHOLD),
				-MathUtil.applyDeadband((driver.getRightX() * Math.abs(driver.getRightX()))*1, JOYSTICK_Z_AXIS_THRESHOLD),
				true, false), drivetrain));

	}

	public Field2d getField()
	{
		return field;
	}

	public SwerveDrivetrain getDrivetrain()
	{
		return drivetrain;
	}

}
