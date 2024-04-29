package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Outtake;
import frc.utils.ShooterUtils;

public class VariableAngleShooter extends Command{
    private Limelight limelight;
    private Outtake outtake;
    
    public VariableAngleShooter(Limelight limelight, Outtake outtake) {
        this.limelight = limelight;
        this.outtake = outtake;
        addRequirements(outtake);
    }

    @Override
    public void execute() {
        // if (limelight.getTagId() == Constants.OuttakeConstants.SPEAKER_TAG_ID) {
        //     double Z = limelight.getZ();
        //     double angle = ShooterUtils.instance.getAngleFromDistance(Z);
        //     outtake.setPivot(angle);
        // }

        outtake.setPivot(SmartDashboard.getNumber("Set Shooter Angle", 0));
    }

}
