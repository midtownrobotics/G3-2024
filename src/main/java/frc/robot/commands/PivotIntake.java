package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class PivotIntake extends Command{
    private final Intake intake;
    private final double power;

    public PivotIntake(Intake intake, double power) {
        this.intake = intake;
        this.power = power;
    }

    @Override
    public void initialize() {
        intake.pivot(power);
    }

    @Override
    public void end(boolean interrupted) {
        intake.pivot(0);
    }
}