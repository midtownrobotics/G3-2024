package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;

public class RunFlywheel extends Command{
    private final Intake intake;
    private final Outtake outtake;
    private final double power;

    public RunFlywheel(Intake intake, Outtake outtake, double power) {
        this.intake = intake;
        this.outtake = outtake;
        this.power = power;
    }


    @Override
    public void end(boolean interrupted) {
        outtake.flywheel(0);
    }

    @Override
    public void initialize() {
        outtake.flywheel(power);
    }
    
}
