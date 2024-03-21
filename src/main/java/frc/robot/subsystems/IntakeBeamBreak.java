package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Ports;

public class IntakeBeamBreak {
    private final XboxController driver;
    private final XboxController operator;

    public IntakeBeamBreak() {
        this.driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);
        this.operator = new XboxController(Ports.USB.OPERATOR_CONTROLLER);
    }

    public void onTrue() {
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 1);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
        operator.setRumble(GenericHID.RumbleType.kRightRumble, 1);
        operator.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
    }

    public void stop() {
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        operator.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        operator.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }
}
