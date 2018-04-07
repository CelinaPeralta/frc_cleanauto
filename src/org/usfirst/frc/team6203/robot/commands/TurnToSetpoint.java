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

	public TurnToSetpoint(double angle) {
		requires(Robot.chassis);
		this.target = angle;
		rotationSpeed = initialRotationSpeed;
	}

	protected void initialize() {
		Robot.imu.reset();
		setTimeout(3);
	}

	protected void execute() {
		if (target < 0)
			Robot.chassis.tankDrive(rotationSpeed, -rotationSpeed);
		else
			Robot.chassis.tankDrive(-rotationSpeed, rotationSpeed);

		SmartDashboard.putNumber("AngleZ", Robot.imu.getAngleZ());
		SmartDashboard.putNumber("Rotation Speed", rotationSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(Robot.imu.getAngleZ() - target) < 1.5 || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.chassis.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
