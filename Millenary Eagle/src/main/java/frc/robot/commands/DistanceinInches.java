/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.DistancePIDController;
import frc.robot.Robot;

public class DistanceinInches extends Command {
  double ref;
  double distance;
  double angle;
  int refAngle;

  int tol = 1;

  double kProportional = 0.02;
  double kProportionalturn = 0.03;

  static DistancePIDController pid;

  public DistanceinInches(double inches, int angle) {
    requires(Robot.powertrain);
    ref = inches;
    refAngle = angle;

    pid = new DistancePIDController(0.015, 0, 0, -1, 1);
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.powertrain.resetEncoder();
    Robot.powertrain.resetGyro();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double xSpeed = ((ref - distance)*kProportional);
    double zSpeed = ((refAngle - angle)*kProportionalturn);
    
    Robot.powertrain.arcadeDrive(velocity(xSpeed, 0.7, 0.4), velocity(zSpeed, 0.7, 0));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    angle = Robot.powertrain.gyroFinal();
    distance = Robot.powertrain.distanceInInches();
    return Math.abs(distance) >= (Math.abs(ref) - tol);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.powertrain.arcadeDrive(-0.1, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  double velocity(double speed, double maxSpeed, double minSpeed){
    if (speed >= maxSpeed) speed = maxSpeed;
    else if (speed <= -maxSpeed) speed = -maxSpeed;
    else if(speed <= minSpeed && speed > 0) speed = minSpeed;
    else if(speed >= -minSpeed && speed < 0) speed = -minSpeed;
    return speed;
  }
}
