package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.NeoMotorConstants;

public class Outtake extends SubsystemBase {

    private final CANSparkMax rightWheel;
    private final CANSparkMax rollerLeader;
    private final CANSparkMax rollerFollower;
    private final CANSparkMax pivotOuttake;
    private final CANSparkMax leftWheel;
    private final SparkPIDController pivotPID;
    private final DutyCycleEncoder pivotEncoder;
    private double speed;
    private boolean intakeOuttake;
    private String mode;

    public Outtake(CANSparkMax rightWheel, CANSparkMax leftWheel, CANSparkMax rollerLeader, CANSparkMax rollerFollower, CANSparkMax pivotOuttake, DigitalInput pivotDIO){
        this.rightWheel = rightWheel;
        this.rollerLeader = rollerLeader;
        this.rollerFollower = rollerFollower;
        this.leftWheel = leftWheel;
        this.pivotOuttake = pivotOuttake;
        this.pivotEncoder = new DutyCycleEncoder(pivotDIO);
        rightWheel.restoreFactoryDefaults();
        leftWheel.restoreFactoryDefaults();
        rollerLeader.restoreFactoryDefaults();
        rollerFollower.restoreFactoryDefaults();
        rightWheel.setIdleMode(IdleMode.kCoast);
        leftWheel.setIdleMode(IdleMode.kCoast);
        rollerLeader.setIdleMode(IdleMode.kCoast);
        rollerFollower.setIdleMode(IdleMode.kCoast);
        leftWheel.setInverted(true);
        rollerLeader.setInverted(true);
        rollerFollower.follow(rollerLeader, false);
        rightWheel.setSmartCurrentLimit(NeoMotorConstants.STANDARD_NEO_CURRENT_LIMIT);
        leftWheel.setSmartCurrentLimit(NeoMotorConstants.STANDARD_NEO_CURRENT_LIMIT);
        rollerLeader.setSmartCurrentLimit(NeoMotorConstants.ROLLER_FEED_CURRENT_LIMIT);
        rollerFollower.setSmartCurrentLimit(NeoMotorConstants.ROLLER_FEED_CURRENT_LIMIT);
        pivotPID = pivotOuttake.getPIDController();
        pivotPID.setP(0.1);
        pivotPID.setI(0);
        pivotPID.setD(0);
        pivotPID.setOutputRange(-1, 1);
        pivotOuttake.getEncoder().setPositionConversionFactor(360/4096);
        speed = 0;
        intakeOuttake = false;
    }

    public void run(double power){
        flywheel(power);
        roller(power);
    }

    public void flywheel(double power){

        rightWheel.set(power);

        if(mode == "amp" || mode == "stop") {
            leftWheel.set(power);
        } else if (mode == "speaker") {
            leftWheel.set(power * .35);
        }
    }

    public void roller(double power){
        rollerLeader.set(power);
    }

    public void pivot(double power){
        pivotOuttake.set(power);
    }

    public double getPivot() {
        return pivotEncoder.getAbsolutePosition();
    }

    public void setPivot(double setpoint) {
        pivotPID.setReference(setpoint, ControlType.kPosition);
    }

    public void setPivot() {
        // pivotPID.
    }

    public void run() {
        flywheel();
        roller(1);
    }

    public void flywheel() {
        flywheel(speed);
    }

    public void rollSpeed() {
        roller(speed);
    }

    public void setSpeed(double change) {
        speed = change;
    }

    public void setMode(String newMode) {
        mode = newMode;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRightWheelMotorTemp() {
        return rightWheel.getMotorTemperature();
    }
    
    public double getLeftWheelMotorTemp() {
        return leftWheel.getMotorTemperature();
    }

    public double getRollerLeaderMotorTemp() {
        return rollerLeader.getMotorTemperature();
    }

    public double getRollerFollowerMotorTemp() {
        return rollerFollower.getMotorTemperature();
    }

    public double getRightWheelSpeed() {
        return rightWheel.getEncoder().getVelocity();
    }

    public double getLeftWheelSpeed() {
        return leftWheel.getEncoder().getVelocity();
    }

}
