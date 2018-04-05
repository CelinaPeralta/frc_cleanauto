package org.usfirst.frc.team6203.robot;

import org.usfirst.frc.team6203.robot.commands.BaseLineAuto;
import org.usfirst.frc.team6203.robot.commands.RobotDrive;
import org.usfirst.frc.team6203.robot.commands.ScaleAuto;
import org.usfirst.frc.team6203.robot.commands.SwitchAuto;
import org.usfirst.frc.team6203.robot.commands.TestAuto;
import org.usfirst.frc.team6203.robot.commands.TimedAutoRoutine;
import org.usfirst.frc.team6203.robot.subsystems.Chassis;
import org.usfirst.frc.team6203.robot.subsystems.Elevator;
import org.usfirst.frc.team6203.robot.subsystems.Intake;
import org.usfirst.frc.team6203.robot.subsystems.LED;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

	public static CameraServer usbCam;

	public static Chassis chassis;
	public static Intake intake;
	public static Elevator elevator;
	public static RobotDrive robotDrive;

	public static LED led;

	int robot_position;
	int switch_position;
	int scale_position;
	boolean fdisable = false;

	// PowerDistributionPanel pdp;

	Command autonomousCommand;
	SendableChooser<Integer> chooser;
	SendableChooser<Command> auto_chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		// Instantiate subsystems
		oi = new OI();
		chassis = new Chassis();
		intake = new Intake();
		elevator = new Elevator();
		robotDrive = new RobotDrive();

		led = new LED();

		usbCam = CameraServer.getInstance();
		usbCam.startAutomaticCapture();

		// pdp = new PowerDistributionPanel();

		chassis.imu.calibrate();

		chooser = new SendableChooser<Integer>();
		chooser.addObject("Left", 0);
		chooser.addDefault("Middle", 1);
		chooser.addObject("Right", 2);

		// Get game data
		double start = System.currentTimeMillis();

		String gameData;
		do
			gameData = DriverStation.getInstance().getGameSpecificMessage();
		while (gameData.length() == 0 && System.currentTimeMillis() - start < 2000);
		if (gameData.length() == 0)
			gameData = "L";

		// robot_position = 3 - DriverStation.getInstance().getLocation();
		robot_position = chooser.getSelected();

		switch_position = gameData.charAt(0) == 'L' ? 0 : 2;
		scale_position = gameData.charAt(1) == 'L' ? 0 : 2;

		auto_chooser = new SendableChooser<Command>();
		auto_chooser.addDefault("Baseline", new BaseLineAuto());
		auto_chooser.addObject("Switch", new SwitchAuto(robot_position, switch_position));
		auto_chooser.addObject("Scale", new ScaleAuto(robot_position, scale_position));
		auto_chooser.addObject("Timed", new TimedAutoRoutine(robot_position, switch_position));
		auto_chooser.addObject("Test", new TestAuto());

		SmartDashboard.putData("Robot Position", chooser);
		SmartDashboard.putData("Autonomous Command", auto_chooser);
		SmartDashboard.putString("Game Data", DriverStation.getInstance().getGameSpecificMessage());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */

	public void disabledInit() {
		chassis.imu.calibrate();
		chassis.imu.reset();
	}

	@Override
	public void disabledPeriodic() {
		led.pong_or_gg();

		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */

	public void autonomousInit() {
		autonomousCommand = auto_chooser.getSelected();
		autonomousCommand.start();
	}

	public void autonomousPeriodic() {
		led.glhf();

		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		robotDrive.start();
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		led.emote();

		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}