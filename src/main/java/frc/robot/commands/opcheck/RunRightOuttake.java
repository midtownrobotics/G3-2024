package frc.robot.commands.opcheck;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class RunRightOuttake extends Command{
    Outtake outtake;
    boolean reversed;

    public RunRightOuttake(Outtake outtake, boolean reversed){
        this.outtake = outtake;
        this.reversed = reversed;
    }

    @Override
    public void execute() {
        if (reversed) {
            outtake.rightFlyWheel(-1);
        }else{
            outtake.rightFlyWheel(1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        outtake.rightFlyWheel(0);
    }
}
