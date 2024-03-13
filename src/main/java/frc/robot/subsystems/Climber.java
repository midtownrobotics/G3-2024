package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
    
    private final CANSparkMax leftWinch;
    private final CANSparkMax rightWinch;
    private final DigitalInput leftSensor;
    private final DigitalInput rightSensor;

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

    public boolean getLeftSensor() {
        return leftSensor.get();
    }

    public boolean getRightSensor() {
        return rightSensor.get();
    }
}
