package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToSetpoint extends Command {

	double target;

	double rotationSpeed;
	final double initialRotationSpeed = 0.8;
	final double kP = 0.00003;
	final double kI = 0.00003;
	final double kD = 0.00003;

	private double previous_error = 0;
	private double error = 0;
	private double error_sum = 0;
	private double current = 0.0;

	public TurnToSetpoint(double angle) {
		requires(Robot.chassis);
//		if (angle < 0)
//			this.target = 360 + angle;
//		else
//			this.target = angle;
		
		this.target = angle;
		rotationSpeed = initialRotationSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.imu.reset();
		//setTimeout(10);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		current = Robot.imu.getAngleZ();
		error = Math.abs(target - current); // maybe not abs

		// look at this jank ass code
//		if (target > 180)
//			Robot.chassis.tankDrive(-rotationSpeed, rotationSpeed);
//		else
//			Robot.chassis.tankDrive(rotationSpeed, -rotationSpeed);
		
		if (target < 0)
			Robot.chassis.tankDrive(rotationSpeed, -rotationSpeed);
		else
			Robot.chassis.tankDrive(-rotationSpeed, rotationSpeed);

		SmartDashboard.putNumber("AngleZ", Robot.imu.getAngleZ());
		SmartDashboard.putNumber("Rotation Speed", rotationSpeed);

		previous_error = error;

		// getPIDOutput();
	}

	protected void getPIDOutput() {

		double P = error * kP;
		error_sum += error * kI;
		double D = (error - previous_error) / kD;

		rotationSpeed = P + error_sum + D;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(Robot.imu.getAngleZ() - target) < 1.5 || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.chassis.tankDrive(0, 0);
		Robot.imu.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
