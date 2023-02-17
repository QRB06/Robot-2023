package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    public final XboxController driver = new XboxController(0);
    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kBack.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton aButton = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton bButton = new JoystickButton(driver, XboxController.Button.kB.value);
    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    public VerticalElevator verticalElevator = new VerticalElevator();
    public HorizontalElevator horizontalElevator = new HorizontalElevator();
    public Wrist wrist = new Wrist();
    public Intake intake = new Intake();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
        
        

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));                
        aButton.onTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, 0.95, 1));
        aButton.onFalse(new HorizontalFirstVerticalCommand(verticalElevator, horizontalElevator, 0.02, 0.01));
        bButton.onTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, 0.53, .5));
        bButton.onFalse(new HorizontalFirstVerticalCommand(verticalElevator, horizontalElevator, 0.05, 0.05));
    }

    public void teleopPeriodic() {
        System.out.println("RobotContainer teleopPeriodic");

        // Vertical Elevator

        this.verticalElevator.driveTowardsPid();
        
        // Horizontal Elevator

         this.horizontalElevator.driveTowardsPid();

         // Intake

// Vertical Elevator PID
    //this.verticalElevator.driveTowardsPid();
// Horizontal Elevator PID
    //this.horizontalElevator.driveTowardsPid();
     // Intake
     if (driver.getYButtonPressed()){
        intake.intake_on(0.8);
     }
     if (driver.getYButtonReleased()){
        intake.intake_on(0.0);
     }
     // Out-take
     if (driver.getXButtonPressed()){
        intake.intake_on(-.7);
     }
     if (driver.getXButtonReleased()){
        intake.intake_on(0.0);
     }
    // Wrist
     if (driver.getRightBumperPressed()){
        wrist.setSetpoint(0.7);
     }
     if (driver.getLeftBumperPressed()){
        wrist.setSetpoint(1);
     }
     this.wrist.driveTowardsPid();

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new TestAuto(s_Swerve);
        

    }
}
