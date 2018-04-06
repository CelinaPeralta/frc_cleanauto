package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {

	private double delay = 0.5;

	public TestAuto() {
		if (Robot.robot_position == 1) {
			//lord have mercy
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? -45 : 45));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.8, 0.8, .4));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? 45 : -45));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
		} else if (Robot.robot_position == Robot.switch_position) {
			addSequential(new DriveToTimeout(0.8, 0.8, 1));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? 90 : -90));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
		} else {
			addSequential(new BaseLineAuto());
		}
	}
}
