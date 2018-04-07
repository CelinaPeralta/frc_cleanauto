package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitchAuto extends CommandGroup {

	private double delay = 0.4;

	public CenterSwitchAuto() {
			addSequential(new DriveToTimeout(0.8, 0.8, .2));
			addSequential(new Wait(.4));
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? -45 : 45));
			addSequential(new Wait(1));
			addSequential(new DriveToTimeout(0.8, 0.8,Robot.switch_position == 0 ? .7 : .55));
			addSequential(new Wait(1));
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? 45 : -45));
			addSequential(new Wait(.5));
			addSequential(new SetElevator(0.5, 3.5));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
	}
}
