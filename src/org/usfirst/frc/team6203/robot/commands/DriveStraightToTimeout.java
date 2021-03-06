package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightToTimeout extends Command {
	
	double speed;
	double kP = 0.03;

    public DriveStraightToTimeout(double a, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    	setTimeout(timeout);
    	speed = a;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.imu.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double theta = -Robot.imu.getAngleZ() * kP;
    	Robot.chassis.drive.arcadeDrive(speed, theta);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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
