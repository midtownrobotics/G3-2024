package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{

    private final CANSparkMax runExternal;
    private final CANSparkMax runInternal; 
    private final DoubleSolenoid externalPivot;
    private final DigitalInput noteSensor;
    
    public Intake(CANSparkMax runExternal, CANSparkMax runInternal, DoubleSolenoid externalpivot, DigitalInput noteSensor){
        this.runExternal = runExternal;
        this.runInternal = runInternal;
        this.externalPivot = externalpivot;
        this.noteSensor = noteSensor;
        runExternal.restoreFactoryDefaults();
        runInternal.restoreFactoryDefaults();
        runInternal.setInverted(true);
        externalPivot.set(Value.kForward);
    }

    public void run(double power){
        runExternal.set(power/2);
        runInternal.set(power);
    }

    public void pivot(Value value) {
        externalPivot.set(value);
    }

    public void togglePivot() {
        externalPivot.toggle();
    }

    public boolean getNoteSensor() {
        return noteSensor.get();
    }

    public double getInternalMotorTemp() {
        return runInternal.getMotorTemperature();
    }

}
