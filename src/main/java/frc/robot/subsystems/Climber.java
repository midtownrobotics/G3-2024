package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.NeoMotorConstants;

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
        
        leftWinch.setSmartCurrentLimit(NeoMotorConstants.STANDARD_NEO_CURRENT_LIMIT);
        rightWinch.setSmartCurrentLimit(NeoMotorConstants.STANDARD_NEO_CURRENT_LIMIT);
    }

    public void winch(double left, double right){
        leftWinch.set(left);
        rightWinch.set(right);
    }

    public boolean getLeftSensor() {
        return leftSensor.get();
    }

    public boolean getRightSensor() {
        return rightSensor.get();
    }
}
