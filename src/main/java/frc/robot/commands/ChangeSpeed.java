package frc.robot.commands;

import edu.wpi.first.hal.simulation.RoboRioDataJNI;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.subsystems.Outtake;

public class ChangeSpeed extends Command{
    private final Outtake outtake;
    private final double speed;
    private final String mode;
    private double angle = 0.9;

    public ChangeSpeed(Outtake outtake, double speed, String newMode) {
        this.outtake = outtake;
        this.speed = speed;
        this.mode = newMode;
        addRequirements(outtake);
    }

    @Override
    public void initialize() {
        switch (mode) {
            case "amp":
                outtake.changeStop(false);
                angle = OuttakeConstants.AMP_ANGLE;
                outtake.setSpeed(speed, angle);
                outtake.setMode("amp");
                break;
            case "bottom":
                outtake.changeStop(false);
                angle = 0.962;
                outtake.setSpeed(speed, angle);
                outtake.setMode("speaker");
                break;
            case "speaker":
                outtake.changeStop(false);
                angle = OuttakeConstants.SPEAKER_ANGLE;
                outtake.setSpeed(speed, angle);
                outtake.setMode("speaker");
                break;
            case "stop":
                outtake.changeStop(true);
                outtake.setMode("stop");
                break;
        }
    }

}
