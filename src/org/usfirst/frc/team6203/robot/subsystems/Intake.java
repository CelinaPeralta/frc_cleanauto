package org.usfirst.frc.team6203.robot.subsystems;

import org.usfirst.frc.team6203.robot.Constants;
import org.usfirst.frc.team6203.robot.OI;
import org.usfirst.frc.team6203.robot.RobotMap;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	public static Spark m_intakeDropperMotor;
	public static Victor m_intakeMotorM, m_intakeMotorS;

	boolean drop = false;

	public Intake() {
		m_intakeMotorM = new Victor(RobotMap.intakeMotorM);
		m_intakeMotorS = new Victor(RobotMap.intakeMotorS);
		m_intakeDropperMotor = new Spark(RobotMap.intakeDropperMotor);

		m_intakeMotorM.setInverted(true);
	}

	public void initDefaultCommand() {

	}

	public void setIntakeSpeed(double speed) {
		m_intakeMotorM.set(speed);
		m_intakeMotorS.set(speed);
	}

	public void drive() {
		boolean right = OI.elevatorStick.getRawButton(1);
		
		if (right) {
			m_intakeDropperMotor.set(-0.8);
		} else
			m_intakeDropperMotor.set(0);

		drop = drop || right;

		if (drop)
			setIntakeSpeed(0);
		else if (OI.driverStick.getRawButton(4))
			setIntakeSpeed(Constants.m_IntakeMaxIntakeSpeed);
		else if (OI.driverStick.getRawButton(6))
			setIntakeSpeed(Constants.m_IntakeSlowEjectSpeed);
		else if (OI.driverStick.getRawButton(5))
			setIntakeSpeed(Constants.m_IntakeFastEjectSpeed);
		else
			setIntakeSpeed(0);
	}
}
