package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.NeoMotorConstants;

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
        runExternal.setInverted(true);

        runExternal.setSmartCurrentLimit(NeoMotorConstants.INTAKE_CURRENT_LIMIT_EXTERNAL);
        runInternal.setSmartCurrentLimit(NeoMotorConstants.INTAKE_CURRENT_LIMIT_INTERNAL);

        runInternal.setInverted(false);

        SmartDashboard.putBoolean("external inverted?", runExternal.getInverted());

        runExternal.burnFlash();
        runInternal.burnFlash();

        externalPivot.set(Value.kForward);  
    }

    public void run(double power){
        runExternal.set(power);
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
