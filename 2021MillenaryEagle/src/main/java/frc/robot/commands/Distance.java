/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PID;
import frc.robot.subsystems.Powertrain;

public class Distance extends CommandBase {
  private final Powertrain powertrain;
  private static PID pidDistance;
  private float angle;

  private double distance;

  private double tolerance = 0.05; // 5 cm

  double kP = 0,
         kI = 0, 
         kD = 0, 
         bias = 0.3;

  /**
   * 7v7
   * 
   * @param powertrain       Subsistema motriz
   * @param distanceInMeters Distancia deseada en metros
   * @param kP               Proporcional
   * @param kI               Integral
   * @param kD               Derivativo
   */
  public Distance(Powertrain powertrain, double distanceInMeters, double kP, double kI, double kD, double bias) {
    this.powertrain = powertrain;
    this.distance = distanceInMeters;
    pidDistance = new PID(kP, kI, kD, distanceInMeters, bias, false);

    addRequirements(powertrain);
  }

  /**
   * Comando para acanzar la distancia deseada en metros. Realiza el cálculo de PID y terminará cuando la distancia
   * sea mayor o igual tomando en cuenta la tolerancia dada. los valores de PID en este apartado ya están declarados.
   * 
   * @param powertrain
   * @param distanceInMeters
   */
  public Distance(Powertrain powertrain, double distanceInMeters) {
    this.powertrain = powertrain;
    this.distance = distanceInMeters;

    pidDistance = new PID(kP, kI, kD, distanceInMeters, bias, false);

    addRequirements(powertrain);
  }

  @Override
  public void initialize() {
    powertrain.resetEncoders();

    angle = (float) powertrain.angleNormalized(); //Guardamos el grado actual del robot para realizar su recorrido recto.
  }

  @Override
  public void execute() {
    pidDistance.runPID(powertrain.getDistanceRight());

    double turn = (angle - powertrain.navAngle()) * 0.1;

    powertrain.arcadeDrive(pidDistance.valuePID(), turn);

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return (powertrain.getDistanceRight() >= (distance - tolerance));
  }
}
