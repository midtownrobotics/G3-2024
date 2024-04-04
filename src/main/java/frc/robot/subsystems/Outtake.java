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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.NeoMotorConstants;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.Robot.modeChoices;

public class Outtake extends SubsystemBase {

    private final CANSparkMax rightWheel;
    private final CANSparkMax rollerLeader;
    private final CANSparkMax rollerFollower;
    private final CANSparkMax pivotOuttake;
    private final CANSparkMax leftWheel;
    private final PIDController pivotPID;
    private final SparkPIDController rightPID;
    private final SparkPIDController  leftPID;
    private final DutyCycleEncoder pivotEncoder;
    private double speed;
    private double angle;
    private String mode;
    private double setPoint;

    private boolean stop = false;

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
        pivotPID = new PIDController(0, 0, 0);
        pivotPID.setP(10);
        pivotPID.setI(0);
        pivotPID.setD(0);
        pivotOuttake.getEncoder().setPositionConversionFactor(360/4096);
        rightPID = rightWheel.getPIDController();
        leftPID = leftWheel.getPIDController();
        rightPID.setI(0);
        leftPID.setI(0);
        rightPID.setOutputRange(0, 1);
        leftPID.setOutputRange(0, 1);
        leftPID.setP(OuttakeConstants.FLYWHEEL_SPEED_P);
        leftPID.setD(OuttakeConstants.FLYWHEEL_SPEED_D);
        leftPID.setFF(OuttakeConstants.FLYWHEEL_SPEED_FF);
        rightPID.setP(OuttakeConstants.FLYWHEEL_SPEED_P);
        rightPID.setD(OuttakeConstants.FLYWHEEL_SPEED_D);
        rightPID.setFF(OuttakeConstants.FLYWHEEL_SPEED_FF);
        speed = 0;
        angle = 0.9;

    }

    public void changeStop(boolean to) {
        stop = to;
    }

    public void run(double power){
        flywheel(power);
        roller(power);
    }

    public void pidWheel() {
        pidWheel(speed);
    }

    public void setRightSpeed() {
        // double leftPower = leftWheel.getAppliedOutput();

        // if (mode == "amp") {
        //     rightWheel.set(leftPower);
        // } else {
        //     rightWheel.set(leftPower / .35);
        // }
    }

    @Override
    public void periodic() {
        if (stop == true) {
            leftWheel.set(0);
            rightWheel.set(0);
        }

        SmartDashboard.putNumber("P", leftPID.getP());
        SmartDashboard.putNumber("D", leftPID.getD());
    }

    public void pidWheel(double speed) {

        if (speed == 0) {

            leftPID.setReference(0, ControlType.kDutyCycle);
            rightPID.setReference(0, ControlType.kDutyCycle);
             
        } else {
        
            if (mode == "amp") {
                leftPID.setReference(speed, ControlType.kVelocity);
                rightPID.setReference(speed, ControlType.kVelocity);
            } else {
                leftPID.setReference(speed * .35, ControlType.kVelocity);
                rightPID.setReference(speed, ControlType.kVelocity);
            }

        }
        
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

    public double getMotorPivot() {
        return pivotOuttake.getEncoder().getPosition();
    }

    public void setPivot(double setpoint) {
        double pidAmount = 0;
        if (setpoint > .81 && setpoint < .99){
            pidAmount = pivotPID.calculate(getPivot(), setpoint);
            pivotOuttake.set(pidAmount);
        }
        SmartDashboard.putNumber("PID value", pidAmount);
    }

    public void setPivot() {
        setPivot(angle);
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

    public void setSpeed(double change, double angle) {
        this.angle = angle;
        setSpeed(change);
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

    public double getRightWheelTarget() {
        return rightWheel.getAppliedOutput();
    }

    public double getLeftWheelTarget() {
        return leftWheel.getAppliedOutput();
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public boolean getStop() {
        return stop;
    }
}
