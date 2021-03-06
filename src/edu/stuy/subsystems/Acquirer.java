package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Acquirer {

    private static Acquirer instance;
    private Solenoid pistonExtend;
    private Solenoid pistonRetract;
    private Talon roller;
    public Compressor compressor;

    public Acquirer() {
        pistonExtend = new Solenoid(Constants.PISTON_EXTEND_CHANNEL);
        pistonRetract = new Solenoid(Constants.PISTON_RETRACT_CHANNEL);
        roller = new Talon(Constants.ACQUIRER_ROLLER_CHANNEL);
        compressor = new Compressor(Constants.PRESSURE_SWITCH_CHANNEL, Constants.COMPRESSOR_RELAY_CHANNEL);
        compressor.start();
    }

    public static Acquirer getInstance() {
        if (instance == null) {
            instance = new Acquirer();
        }
        return instance;
    }

    public void rotateDown() {
        pistonExtend.set(true);
        pistonRetract.set(false);
    }

    public void rotateUp() {
        pistonExtend.set(false);
        pistonRetract.set(true);
    }

    public void intakeBall() {
        roller.set(-1);
    }

    public void intakeHalfSpeed() {
        roller.set(-0.5);
    }

    public void ejectBall() {
        roller.set(1);
    }

    public void stopRoller() {
        roller.set(0);
    }

    public void reset() {
        stopRoller();
    }

    public void manualGamepadControl(Gamepad gamepad) {
        // Acquirer frame controls
        if (gamepad.getLeftBumper()) {
            SmartDashboard.putString("acquirer state", "ejecting");
            ejectBall();
        } else if (gamepad.getLeftTrigger()) {
            SmartDashboard.putString("acquirer state", "intaking");
            intakeBall();
        } else {
            SmartDashboard.putString("acquirer state", "stopping");
            stopRoller();
        }

        // Acquirer roller controls
        if (gamepad.getRightBumper()) {
            rotateDown();
        } else if (gamepad.getRightTrigger()) {
            rotateUp();
        }
    }
}
