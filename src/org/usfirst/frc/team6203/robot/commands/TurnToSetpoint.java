package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToSetpoint extends Command {

	double target;

	double rotationSpeed;
	final double initialRotationSpeed = 0.6;
	final double kP = 0.03;

	public TurnToSetpoint(double angle) {
		requires(Robot.chassis);
		this.target = angle;
		rotationSpeed = initialRotationSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.chassis.imu.reset();
		setTimeout(4.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (target < 0)
			Robot.chassis.tankDrive(-rotationSpeed, rotationSpeed);
		else
			Robot.chassis.tankDrive(rotationSpeed, -rotationSpeed);
	}

	protected void getPIDOutput() {
		rotationSpeed -= Math.abs(Robot.chassis.imu.getAngleZ() - target) * kP;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(Robot.chassis.imu.getAngleZ() - target) < 2.0 || isTimedOut();
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
