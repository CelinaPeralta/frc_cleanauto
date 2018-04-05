package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchAuto extends CommandGroup {

	private double delay = 0.5;

	public SwitchAuto(int robot_position, int switch_position) {
		if (robot_position == 1) {
			//lord have mercy
			addSequential(new TurnToSetpoint(switch_position == 0 ? -45 : 45));
			addSequential(new Wait(delay));
			addSequential(new DriveAndRaiseElevator(0.6, 3.0, 0.5, 4.0));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(switch_position == 0 ? 45 : -45));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.4, 0.4, 3.5));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
			addSequential(new DriveToTimeout(-0.25, -0.25, 1.0));
		} else if (robot_position == switch_position) {
			addSequential(new DriveAndRaiseElevator(0.8, 3.0, 0.5, 4.0));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(switch_position == 0 ? 90 : -90));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.4, 0.4, 3.0));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
			addSequential(new DriveToTimeout(-0.25, -0.25, 1.0));
		} else {
			addSequential(new BaseLineAuto());
		}
	}
}
