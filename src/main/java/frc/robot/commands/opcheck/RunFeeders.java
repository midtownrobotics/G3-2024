package frc.robot.commands.opcheck;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class RunFeeders extends Command{
    Outtake outtake;
    double speed;
    public RunFeeders(Outtake outtake, double speed) {
        this.outtake = outtake;
    }

    @Override
    public void execute() {
        outtake.runFeeders(speed);
    }

    @Override
    public void end(boolean interrupted) {
        outtake.runFeeders(0);
    }
}
