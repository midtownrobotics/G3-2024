package frc.robot.commands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;
import edu.wpi.first.wpilibj2.command.Command;

public class RunIntake extends Command {
    private final Intake intake;
    private final Outtake outtake;
    private final double power;
 
    public RunIntake(Intake intake, Outtake outtake, double power) {
        this.intake = intake;
        this.outtake = outtake;
        this.power = power;
 
        addRequirements(intake);
    }
 
    @Override
    public void execute() {
        intake.run(power);
        outtake.roller(0.1);
    }

    @Override
    public void end(boolean interrupted) {
        intake.run(0);
        outtake.roller(0);
    }

    @Override
    public boolean isFinished() {
        boolean intakeStatus = intake.getNoteSensor();
        return intakeStatus;
    }
}