package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Ports;

public class IntakeBeamBreak {
    XboxController driver;

    public IntakeBeamBreak() {
        this.driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);;
    }

    public void onTrue() {
        XboxController driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 1);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
    }

    public void stop() {
        XboxController driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }
}
