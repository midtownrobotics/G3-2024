package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class PivotIntake extends Command{
    private final Intake intake;
    private final Value value;

    public PivotIntake(Intake intake, Value value) {
        this.intake = intake;
        this.value = value;
    }

    @Override
    public void initialize() {
        intake.pivot(value);
    }

}