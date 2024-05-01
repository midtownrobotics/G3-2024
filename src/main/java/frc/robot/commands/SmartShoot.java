package frc.robot.commands;

import edu.wpi.first.hal.simulation.RoboRioDataJNI;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Outtake;
import frc.utils.ShooterUtils;

public class SmartShoot extends Command{
    private final Outtake outtake;
    private final double speed;
    private final Limelight limelight;

    public SmartShoot(Outtake outtake, double speed, Limelight limelight) {
        this.outtake = outtake;
        this.speed = speed;
        this.limelight = limelight;
        addRequirements(outtake);
        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        outtake.changeStop(false);
        outtake.setMode("speaker");
    }

    @Override
    public void execute() {
        double distance = limelight.getDistance();
        outtake.setSpeed(speed, ShooterUtils.instance.getAngleFromDistance(distance));
    }

}
