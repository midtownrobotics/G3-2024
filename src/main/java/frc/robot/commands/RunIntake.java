package frc.robot.commands;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;

public class RunIntake extends Command {
    private final Intake intake;
    private final double power;
 
    public RunIntake(Intake intake, double power) {
        this.intake = intake;
        this.power = power;
 
        addRequirements(intake);
    }
 
    @Override
    public void execute() {
        intake.run(power);
    }
 
    @Override
    public void end(boolean interrupted) {
        intake.run(0);
    }
}