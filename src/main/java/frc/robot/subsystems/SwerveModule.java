// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.SensorTimeBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants.SwerveModuleConstants;

/**
 * The {@code SwerveModule} class contains fields and methods pertaining to the function of a swerve module.
 */
public class SwerveModule {
	private final CANSparkMax m_drivingSparkMax;
	private final CANSparkMax m_turningSparkMax;

	private final RelativeEncoder m_drivingEncoder;
	private final RelativeEncoder m_turningEncoder;
	private final CANCoder m_turningAbsoluteEncoder;

	private final SparkMaxPIDController m_drivingPIDController;
	private final SparkMaxPIDController m_turningPIDController;
	private double offset = 0;



	private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

	/**
	 * Constructs a SwerveModule and configures the driving and turning motor,
	 * encoder, and PID controller.
	 */
	public SwerveModule(int drivingCANId, int turningCANId, int turningAnalogPort, double offset, boolean inverted) {
		m_drivingSparkMax = new CANSparkMax(drivingCANId, MotorType.kBrushless);
		m_drivingSparkMax.setInverted(inverted);
		m_turningSparkMax = new CANSparkMax(turningCANId, MotorType.kBrushless);
		this.offset = offset;

		// Factory reset, so we get the SPARKS MAX to a known state before configuring
		// them. This is useful in case a SPARK MAX is swapped out.
		m_drivingSparkMax.restoreFactoryDefaults();
		m_turningSparkMax.restoreFactoryDefaults();

		// Setup encoders and PID controllers for the driving and turning SPARKS MAX.
		m_drivingEncoder = m_drivingSparkMax.getEncoder();
		m_turningEncoder = m_turningSparkMax.getEncoder();
		m_turningAbsoluteEncoder = new CANCoder(turningAnalogPort, "Sensors");
		CANCoderConfiguration config = new CANCoderConfiguration();
		config.sensorCoefficient = 2*Math.PI/4096;
		config.unitString = "rad";
		config.sensorTimeBase = SensorTimeBase.PerSecond;
		config.absoluteSensorRange = AbsoluteSensorRange.Signed_PlusMinus180;
 		m_turningAbsoluteEncoder.configAllSettings(config);
		m_turningAbsoluteEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 100);

		m_drivingPIDController = m_drivingSparkMax.getPIDController();
		m_turningPIDController = m_turningSparkMax.getPIDController();
		m_drivingPIDController.setFeedbackDevice(m_drivingEncoder);
		m_turningPIDController.setFeedbackDevice(m_turningEncoder);

		// Apply position and velocity conversion factors for the driving encoder. The
		// native units for position and velocity are rotations and RPM, respectively,
		// but we want meters and meters per second to use with WPILib's swerve APIs.
		m_drivingEncoder.setPositionConversionFactor(SwerveModuleConstants.DRIVING_ENCODER_POSITION_FACTOR_METERS_PER_ROTATION);
		m_drivingEncoder.setVelocityConversionFactor(SwerveModuleConstants.DRIVING_ENCODER_VELOCITY_FACTOR_METERS_PER_SECOND_PER_RPM);

		// Apply position and velocity conversion factors for the turning encoder. We
		// want these in radians and radians per second to use with WPILib's swerve APIs.
		m_turningEncoder.setPositionConversionFactor(SwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR_RADIANS_PER_ROTATION);
		m_turningEncoder.setVelocityConversionFactor(SwerveModuleConstants.TURNING_ENCODER_VELOCITY_FACTOR_RADIANS_PER_SECOND_PER_RPM);

		// Invert the turning controller, since the output shaft rotates in the opposite direction of
		// the steering motor.
		m_turningSparkMax.setInverted(true);


		// Enable PID wrap around for the turning motor. This will allow the PID
		// controller to go through 0 to get to the setpoint i.e. going from 350 degrees
		// to 10 degrees will go through 0 rather than the other direction which is a
		// longer route.
		m_turningPIDController.setPositionPIDWrappingEnabled(true);
		m_turningPIDController.setPositionPIDWrappingMinInput(SwerveModuleConstants.TURNING_ENCODER_POSITION_PID_MIN_INPUT_RADIANS);
		m_turningPIDController.setPositionPIDWrappingMaxInput(SwerveModuleConstants.TURNING_ENCODER_POSITION_PID_MAX_INPUT_RADIANS);

		// Set the PID gains for the driving motor.
		m_drivingPIDController.setP(SwerveModuleConstants.DRIVING_P);
		m_drivingPIDController.setI(SwerveModuleConstants.DRIVING_I);
		m_drivingPIDController.setD(SwerveModuleConstants.DRIVING_D);
		m_drivingPIDController.setFF(SwerveModuleConstants.DRIVING_FF);
		m_drivingPIDController.setOutputRange(SwerveModuleConstants.DRIVING_MIN_OUTPUT_NORMALIZED, SwerveModuleConstants.DRIVING_MAX_OUTPUT_NORMALIZED);

		// Set the PID gains for the turning motor.
		m_turningPIDController.setP(SwerveModuleConstants.TURNING_P);
		m_turningPIDController.setI(SwerveModuleConstants.TURNING_I);
		m_turningPIDController.setD(SwerveModuleConstants.TURNING_D);
		m_turningPIDController.setFF(SwerveModuleConstants.TURNING_FF);
		m_turningPIDController.setOutputRange(SwerveModuleConstants.TURNING_MIN_OUTPUT_NORMALIZED, SwerveModuleConstants.TURNING_MAX_OUTPUT_NORMALIZED);

		m_drivingSparkMax.setIdleMode(SwerveModuleConstants.DRIVING_MOTOR_IDLE_MODE);
		m_turningSparkMax.setIdleMode(SwerveModuleConstants.TURNING_MOTOR_IDLE_MODE);
		m_drivingSparkMax.setSmartCurrentLimit(SwerveModuleConstants.DRIVING_MOTOR_CURRENT_LIMIT_AMPS);
		m_turningSparkMax.setSmartCurrentLimit(SwerveModuleConstants.TURNING_MOTOR_CURRENT_LIMIT_AMPS);

		// Save the SPARK MAX configurations. If a SPARK MAX browns out during
		// operation, it will maintain the above configurations.
		m_drivingSparkMax.burnFlash();
		m_turningSparkMax.burnFlash();

		m_desiredState.angle = new Rotation2d(m_turningEncoder.getPosition());
		m_drivingEncoder.setPosition(0);
	}

	/**
	 * Returns the current state of the module.
	 *
	 * @return The current state of the module.
	 */
	public SwerveModuleState getState() {
		return new SwerveModuleState(m_drivingEncoder.getVelocity(),
			new Rotation2d(m_turningEncoder.getPosition()));
	}

	/**
	 * Returns the current position of the module.
	 *
	 * @return The current position of the module.
	 */
	public SwerveModulePosition getPosition() {
		return new SwerveModulePosition(
			m_drivingEncoder.getPosition(),
			new Rotation2d(m_turningEncoder.getPosition()));
	}

	/**
	 * Sets the desired state for the module.
	 *
	 * @param desiredState Desired state with speed and angle.
	 */
	public void setDesiredState(SwerveModuleState desiredState) {
		// Apply chassis angular offset to the desired state.
		SwerveModuleState correctedDesiredState = new SwerveModuleState();
		correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
		correctedDesiredState.angle = desiredState.angle;

		// Optimize the reference state to avoid spinning further than 90 degrees.
		SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(correctedDesiredState,
			new Rotation2d(m_turningEncoder.getPosition()));

		// the purpose of the condition heruender is to avoid the noise that the swerve modules make when they are idle
		if (Math.abs(optimizedDesiredState.speedMetersPerSecond) < 0.001 // less than 1 mm per sec
			&& Math.abs(optimizedDesiredState.angle.getRadians() - m_turningEncoder.getPosition()) < Rotation2d.fromDegrees(1).getRadians()) // less than 1 degree
		{
			m_drivingSparkMax.set(0); // no point in doing anything
			m_turningSparkMax.set(0);
		}
		else
		{
			// Command driving and turning SPARKS MAX towards their respective setpoints.
			m_drivingPIDController.setReference(optimizedDesiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);
			m_turningPIDController.setReference(optimizedDesiredState.angle.getRadians(), CANSparkMax.ControlType.kPosition);
		}

		m_desiredState = desiredState;
	}

	/** Zeroes all the SwerveModule relative encoders. */
	public void resetEncoders() {

		m_drivingEncoder.setPosition(0); // arbitrarily set driving encoder to zero

		// temp
		//m_turningAbsoluteEncoder.resetVirtualPosition();
		// the reading and setting of the calibrated absolute turning encoder values is done in the Drivetrain's constructor

		m_turningSparkMax.set(0); // no moving during reset of relative turning encoder

		m_turningEncoder.setPosition(m_turningAbsoluteEncoder.getAbsolutePosition()+offset); // set relative position based on virtual absolute position
	}

	/** Calibrates the virtual position (i.e. sets position offset) of the absolute encoder. */
	public void calibrateVirtualPosition(double angle)
	{
		this.offset = angle;
	}

	public double getOffset(){
		return offset;
	}

	public RelativeEncoder getDrivingEncoder()
	{
		return m_drivingEncoder;
	}

	public RelativeEncoder getTurningEncoder()
	{
		return m_turningEncoder;
	}

	public CANCoder getTurningAbsoluteEncoder()
	{
		return m_turningAbsoluteEncoder;
	}

	public SwerveModuleState getDesiredState()
	{
		return m_desiredState;
	}

	public double getDrivingTemp() {
		return m_drivingSparkMax.getMotorTemperature();
	}

	public double getTurningTemp() {
		return m_turningSparkMax.getMotorTemperature();
	}

}
