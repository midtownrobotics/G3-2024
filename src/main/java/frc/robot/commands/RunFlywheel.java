package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;

public class RunFlywheel extends Command{
    private final Outtake outtake;

    public RunFlywheel(Outtake outtake) {
        this.outtake = outtake;
        addRequirements(outtake);
    }

    @Override
    public void execute() {
        outtake.flywheel();
    }
    
}
