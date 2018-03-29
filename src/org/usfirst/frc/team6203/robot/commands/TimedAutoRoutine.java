package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimedAutoRoutine extends Command {

	// Subsystems
	final double elevator_speed = 0.7;
	final double eject_speed = -0.8;
	final double intake_speed_i = 0.5;

	// Drive constants, same side
	final double s_drive_straight = 0.8;
	final double s_drive_slow = 0.4;

	// Timing phases, same side
	final double t_drive_straight = 4.0;
	final double t_drive_slow = 6.0;
	final double t_deposit_cube = 8.0;

	final double t_run_intake = 4.0;
	final double t_run_elevator = 3.0;

	// Drive constants, opposite side
	final double s2_drive_straight = 0.3;
	final double s2_turn = 0.8;
	final double s2_cross_field = 0.8;
	final double s2_turn_2 = 0.8;
	final double s2_align_bot = 0.4;

	// Timing phases, opposite side
	final double t2_drive_straight = 1.0;
	final double t2_turn = 3.0; // problematic as hell lmfao
	final double t2_turn_delay = 3.5;
	final double t2_cross_field = 7.0;
	final double t2_turn_2 = 10.0;
	final double t2_turn_2_delay = 10.5;
	final double t2_align_bot = 12.0;
	final double t2_deposit_cube = 13.0;

	int robot_position;
	int switch_position;
	int scale_position;

	boolean first = true;
	boolean TRY_MIDDLE_SWITCH = false;

	Timer autoTimer;

	public TimedAutoRoutine(int r, int s) {
		robot_position = r;
		switch_position = s;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		autoTimer = new Timer();
	}

	protected void execute() {
		// Baseline Auto
		if (robot_position == 3 || robot_position != switch_position) {
			if (autoTimer.get() < t_drive_straight)
				Robot.chassis.tankDrive(s_drive_straight, s_drive_straight);
			else
				Robot.chassis.tankDrive(0, 0);
		} else if (robot_position == 1) {
			if (autoTimer.get() < 1.0) {
				if (switch_position == 2)
					Robot.chassis.tankDrive(s_drive_straight + 0.25, s_drive_straight);
				else
					Robot.chassis.tankDrive(s_drive_straight, s_drive_straight);
				Robot.elevator.setElevator(elevator_speed);
			} else if (autoTimer.get() < 5.0) {
				if (switch_position == 2)
					Robot.chassis.tankDrive(s_drive_straight - 0.25, s_drive_straight);
				else
					Robot.chassis.tankDrive(s_drive_straight, s_drive_straight - 0.25);
			} else if (autoTimer.get() < 5.6) {
				Robot.chassis.tankDrive(0, 0);
				Robot.elevator.setElevator(0);
			} else if (autoTimer.get() < 7.5) {
				Robot.chassis.tankDrive(0, 0);
				if (robot_position == switch_position)
					Robot.intake.setIntakeSpeed(eject_speed);
				else // HOLD ON TO THE CUBE FOR DEAR LIFE
					Robot.intake.setIntakeSpeed(-eject_speed);
			} else {
				Robot.intake.setIntakeSpeed(0);
			}
		} else {
			if (switch_position == robot_position) {
				// Drive straight, slow, deposit.
				if (autoTimer.get() < 1.0) {
					if (TRY_MIDDLE_SWITCH) {
						if (switch_position == 2)
							Robot.chassis.tankDrive(s_drive_straight + 0.25, s_drive_straight);
						else
							Robot.chassis.tankDrive(s_drive_straight, s_drive_straight);
					} else {
						Robot.chassis.tankDrive(s_drive_straight, s_drive_straight + 0.25);
					}
					Robot.elevator.setElevator(elevator_speed);
				} else if (autoTimer.get() < 5.0) {
					if (TRY_MIDDLE_SWITCH) {
						if (switch_position == 2)
							Robot.chassis.tankDrive(s_drive_straight - 0.25, s_drive_straight);
						else
							Robot.chassis.tankDrive(s_drive_straight, s_drive_straight - 0.25);
					} else {
						Robot.chassis.tankDrive(s_drive_straight, s_drive_straight + 0.25);
					}
				} else if (autoTimer.get() < 5.6) {
					Robot.chassis.tankDrive(0, 0);
					Robot.elevator.setElevator(0);
				} else if (autoTimer.get() < 7.5) {
					Robot.chassis.tankDrive(0, 0);
					if (robot_position == switch_position)
						Robot.intake.setIntakeSpeed(eject_speed);
					else
						Robot.intake.setIntakeSpeed(-eject_speed);
				} else {
					Robot.intake.setIntakeSpeed(0);
				}
			} else {
				if (autoTimer.get() < 0.5) {
					Robot.chassis.tankDrive(s2_drive_straight, s2_drive_straight);
				} else if (autoTimer.get() < 1.0) {
					Robot.chassis.tankDrive(0, 0);
				} else if (autoTimer.get() < 3.0) {
					if (robot_position == 0)
						Robot.chassis.tankDrive(s2_turn, -s2_turn);
					else
						Robot.chassis.tankDrive(-s2_turn, s2_turn);
				} else if (autoTimer.get() < 4.0) {
					Robot.elevator.setElevator(elevator_speed);
					Robot.chassis.tankDrive(0, 0);
				} else if (autoTimer.get() < 6.5) {
					Robot.elevator.setElevator(0);
					Robot.chassis.tankDrive(s2_cross_field, s2_cross_field);
				} else if (autoTimer.get() < 7.5) {
					if (robot_position == 0)
						Robot.chassis.tankDrive(-s2_turn, s2_turn);
					else
						Robot.chassis.tankDrive(s2_turn, -s2_turn);
				} else if (autoTimer.get() < 8.5) {
					Robot.chassis.tankDrive(s2_align_bot, s2_align_bot);
				} else if (autoTimer.get() < 9.0) {
					Robot.chassis.tankDrive(0, 0);
					Robot.intake.setIntakeSpeed(eject_speed);
				} else {
					Robot.intake.setIntakeSpeed(0);
				}
			}
		}
	}

	protected void runIntake(double time) {
		if (autoTimer.get() < time) {
			Robot.intake.setIntakeSpeed(intake_speed_i);
		} else {
			Robot.intake.setIntakeSpeed(0);
		}
	}

	protected void runElevator(double time) {
		if (autoTimer.get() < time)
			Robot.elevator.elevatorMotor.set(elevator_speed);
		else
			Robot.elevator.elevatorMotor.set(0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
