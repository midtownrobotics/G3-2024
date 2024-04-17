package frc.robot.commands.opcheck;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class RunLeftOuttake extends Command{
    Outtake outtake;
    boolean reversed;

    public RunLeftOuttake(Outtake outtake, boolean reversed){
        this.outtake = outtake;
        this.reversed = reversed;
    }

    @Override
    public void execute() {
        if (reversed) {
            outtake.leftFlyWheel(-1);
        }else {
            outtake.leftFlyWheel(1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        outtake.leftFlyWheel(0);
    }
}
