package frc.robot.commands;


import edu.wpi.first.hal.simulation.RoboRioDataJNI;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Outtake;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.utils.ShooterUtils;

public class SmartShoot extends Command{
    private final Outtake outtake;
    private final double speed;
    private final Limelight limelight;
    private final SwerveDrivetrain drivetrain;
    private PIDController rotationPID = new PIDController(OuttakeConstants.SMART_ROTATE_P, 
                                                          OuttakeConstants.SMART_ROTATE_I, 
                                                          OuttakeConstants.SMART_ROTATE_D);

    public SmartShoot(Outtake outtake, double speed, Limelight limelight, SwerveDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.outtake = outtake;
        this.speed = speed;
        this.limelight = limelight;
        addRequirements(outtake);
        addRequirements(limelight);
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        outtake.changeStop(false);
        outtake.setMode("speaker");

        rotationPID.setP(SmartDashboard.getNumber("Smart Shoot P", 0));
    }

    @Override
    public void execute() {
        double distance = limelight.getDistance();
        outtake.setSpeed(speed, ShooterUtils.instance.getAngleFromDistance(distance));
        outtake.pidWheel();
        outtake.setPivot(); 

        drivetrain.drive(0, 0, rotationPID.calculate(limelight.getAngleOffset()), false);
    }

}