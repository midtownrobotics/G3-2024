package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;


public class IntervalAdjustSpeed extends Command{
    private final Outtake outtake;
    private final boolean up;

    public IntervalAdjustSpeed(Outtake outtake, boolean up){
        this.outtake = outtake;
        this.up = up;
    }

    @Override
    public void initialize() {
        if (up){
            outtake.setSpeed(outtake.getSpeed() + 100);
        } else {
            outtake.setSpeed(outtake.getSpeed() - 100);
        }
    }
}
