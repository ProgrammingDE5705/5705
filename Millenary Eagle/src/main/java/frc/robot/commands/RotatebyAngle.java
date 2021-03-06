/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.GyroPIDController;
import frc.robot.Robot;

public class RotatebyAngle extends Command {
  int desiredAngle;
  double angle;
  double kProportional = 0.03;
  double tol = 2;
  static GyroPIDController pidGyro;

  public RotatebyAngle(int angle) {
    requires(Robot.powertrain);
    desiredAngle = angle;
    
    pidGyro = new GyroPIDController(0.01, 0, 0, -0.6, 0.6); 
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.powertrain.resetGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //double zSpeed = ((ref - angle)* kProportional);
    //Robot.powertrain.arcadeDrive(0, velocity(zSpeed, 0.7, 0.4));
    Robot.powertrain.arcadeDrive(0, velocity(pidGyro.getPIDValue(desiredAngle), 0.4) );
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    angle = Robot.powertrain.gyroFinal();
    return Math.abs(angle) >= (Math.abs(desiredAngle) - tol);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.powertrain.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  double velocity(double speed, double minSpeed){
    if(speed <= minSpeed && speed > 0) speed = minSpeed;
    else if(speed >= -minSpeed && speed < 0) speed = -minSpeed;
    return speed;
  }
}
