package frc.robot.subsystems;

import javax.management.relation.Relation;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.NeoMotorConstants;

public class Climber extends SubsystemBase {
    
    private final CANSparkMax leftWinch;
    private final CANSparkMax rightWinch;
    private final DigitalInput leftSensor;
    private final DigitalInput rightSensor;
    private final SparkPIDController leftPID;
    private final SparkPIDController rightPID;

    public Climber(CANSparkMax leftWinch, CANSparkMax rightWinch, DigitalInput leftSensor, DigitalInput rightSensor) {
        this.leftWinch = leftWinch;
        this.rightWinch = rightWinch;
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        leftWinch.restoreFactoryDefaults();
        rightWinch.restoreFactoryDefaults();
        leftWinch.setIdleMode(IdleMode.kBrake);
        rightWinch.setIdleMode(IdleMode.kBrake);
        leftWinch.setInverted(true);
        rightWinch.setInverted(false);
        leftWinch.setSmartCurrentLimit(NeoMotorConstants.STANDARD_NEO_CURRENT_LIMIT);
        rightWinch.setSmartCurrentLimit(NeoMotorConstants.STANDARD_NEO_CURRENT_LIMIT);
        
        leftPID = leftWinch.getPIDController();
        leftPID.setP(0);
        leftPID.setI(0);
        leftPID.setD(0);
        leftPID.setOutputRange(-1, 1);

        rightPID = rightWinch.getPIDController();
        rightPID.setP(0);
        rightPID.setI(0);
        rightPID.setD(0);
        rightPID.setOutputRange(-1, 1);

    }

    public void winch(double left, double right){
        if (leftWinch.getOutputCurrent() >= ClimberConstants.CLIMBER_STOP_CURRENT){
            leftWinch.set(0);
        } else {
            leftWinch.set(left);
        }
        if (rightWinch.getOutputCurrent() >= ClimberConstants.CLIMBER_STOP_CURRENT){
            rightWinch.set(0);
        } else {
            rightWinch.set(right);
        }
    }

    public void pidWinch(double left, double right){
        leftPID.setReference(left, ControlType.kPosition);
        rightPID.setReference(right, ControlType.kPosition);
    }

    public double getLeftWinchEncoder() {
        return leftWinch.getEncoder().getPosition();
    }

    public double getRightWinchEncoder() {
        return rightWinch.getEncoder().getPosition();
    }

    public boolean getLeftSensor() {
        return leftSensor.get();
    }

    public boolean getRightSensor() {
        return rightSensor.get();
    }

    public double getRightMotorTemp() {
        return rightWinch.getMotorTemperature();
    }

    public double getLeftMotorTemp() {
        return leftWinch.getMotorTemperature();
    }
}
