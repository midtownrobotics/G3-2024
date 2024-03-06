package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Outtake extends SubsystemBase {

    private final CANSparkMax outtakeSparkMax;
    private final CANSparkMax pivotOuttake;
    private final CANSparkMax roller;

    public Outtake(CANSparkMax outtakeSparkMax, CANSparkMax pivotOuttake, CANSparkMax roller){
        this.outtakeSparkMax = outtakeSparkMax;
        this.pivotOuttake = pivotOuttake;
        this.roller = roller;
        outtakeSparkMax.restoreFactoryDefaults();
        pivotOuttake.restoreFactoryDefaults();
        outtakeSparkMax.setIdleMode(IdleMode.kCoast);
        outtakeSparkMax.setInverted(true);
    }

    public void run(double power){
        outtakeSparkMax.set(power);
    }

    public void pivot(double power){
        pivotOuttake.set(power);
    }

    public void roller(double power){
        roller.set(power);
    }
}
