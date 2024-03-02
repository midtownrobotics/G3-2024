package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{

    private final CANSparkMax pivotExternal;
    private final CANSparkMax runExternal;
    private final CANSparkMax runInternal; 
    
    public Intake(CANSparkMax pivotExternal, CANSparkMax runExternal, CANSparkMax runInternal){
        this.pivotExternal = pivotExternal;
        this.runExternal = runExternal;
        this.runInternal = runInternal;
        pivotExternal.restoreFactoryDefaults();
        runExternal.restoreFactoryDefaults();
        runInternal.restoreFactoryDefaults();
    }

    public void run(double power){
        runExternal.set(power);
        runInternal.set(power);
    }

    public void pivot(double power){
        pivotExternal.set(power);
    }

}
