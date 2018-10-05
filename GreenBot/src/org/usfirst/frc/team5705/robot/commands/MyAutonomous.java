package org.usfirst.frc.team5705.robot.commands;

import org.usfirst.frc.team5705.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MyAutonomous extends CommandGroup {

	public MyAutonomous() {
		requires(Robot.drivetrain);
		requires(Robot.balls);
	}
		
	protected void initialize() {
		
	}
	protected void execte() {
		addSequential(new Rotate90(), 2);
		addParallel(new Shooter(), 3);
		addParallel(new MoveRobot(), 3);
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted() {
		end();
	}
	

		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// Un grupo de comando requerira todos los
		// subsistemas que cada miembro requeriria,
		// por ejemplo. si Command1 requiere un chasis,
		// y Command2 requiere un brazo, un CommandGroup
		// que los contenga requeriria tanto el chasis como el brazo.
	
}