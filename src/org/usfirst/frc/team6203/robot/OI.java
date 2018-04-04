package org.usfirst.frc.team6203.robot;


import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick driverStick;
	public static Joystick elevatorStick;
	public static Joystick emojiStick;

	public OI() {
		driverStick = new Joystick(RobotMap.controller);
		elevatorStick = new Joystick(RobotMap.controller2);
		emojiStick = new Joystick(RobotMap.controller3);
	}
	
	
	
	

}
