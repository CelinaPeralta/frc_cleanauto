package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAndRaiseElevator extends CommandGroup {

    public DriveAndRaiseElevator(double drive_speed, double drive_time, double elevator_speed, double elevator_time) {
       addParallel(new DriveToTimeout(drive_speed, drive_speed, drive_time));
       addParallel(new SetElevator(elevator_speed, elevator_time));
    }
}
