/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Intake;

/**
 * Aqui se declara los componentes del Intake
 */
public class IntakeBalls extends SubsystemBase {
  private WPI_VictorSPX intake = new WPI_VictorSPX(Intake.m1); //Intake
  private WPI_VictorSPX motorBandaA = new WPI_VictorSPX(Intake.m2); //Banda A (Bufer)
  private WPI_TalonSRX motorBandaB = new WPI_TalonSRX(Intake.m3); //Banda B

  private Solenoid pistonA = new Solenoid(Intake.solenoids[0]);

  private DigitalInput sensor1 = new DigitalInput(Intake.sensors[0]), //Sensor Bufer
                       sensor2 = new DigitalInput(Intake.sensors[1]),
                       sensor3 = new DigitalInput(Intake.sensors[2]),
                       sensor4 = new DigitalInput(Intake.sensors[3]); //Sensor de salida

                       
  //Velocidad de los motores
  private double intake_Velocity = 0.4
  ,
  //regular la velocidad para procurar tener siempre el mismo disparo de salida y no aumentar la potencia con todo el recorrido
  bandaA_Velocity = 0.3,
  bandaB_Velocity = 0.3;
                       
  private boolean s1; //= !sensor1.get();
  private boolean s2; //= !sensor2.get();
  private boolean s3; //= !sensor3.get();
  private boolean s4; //= !sensor4.get();

  private boolean ready = false; 
  
  
  //TOMAR 80% AL TIRADOR!!! NO 100% 
  //Update: Tomar 10v para la torreta
  //LA VELOCIDAD DEL TIRADOR PODRÍA VARIADA POR EL VALOR Y DADO POR LA LIMELIGHT

  public IntakeBalls() {
    //Configurar como defecto los controladores
    intake.configFactoryDefault();
    motorBandaA.configFactoryDefault();
    motorBandaB.configFactoryDefault();
    
    //Asignar si es invertido o no
    intake.setInverted(true);
    motorBandaA.setInverted(true);
    motorBandaB.setInverted(false);

  }

  public void take(){
    intake.set(ControlMode.PercentOutput, intake_Velocity);
    motorBandaA.set(ControlMode.PercentOutput, bandaA_Velocity);
    motorBandaB.set(ControlMode.PercentOutput, bandaB_Velocity);

  }

  public void neutral(){
    intake.set(ControlMode.PercentOutput, 0);
    motorBandaA.set(ControlMode.PercentOutput, 0);
    motorBandaB.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Dada una lógica sobre cuántas power cells tenemos en posesión asegura que mientras se ejecuta este método no existan espacios muertos
   * y pasen poco a poco sin llegar a tocar el disparador.
   */
  public void takeBalls(){

    /**
     * Para la toma de decisiones de esta logica se tomo como referencia una tabla de verdad para 
     * los casos posibles y redundantes.
     * 
     * Modo Simplificado
     */
    if (s1 == true && s4 == true){ //1--1
      //Se detienen las bandas
      //Prender Led de control (Listo para tirar)
      intake.set(ControlMode.PercentOutput, intake_Velocity);
      motorBandaA.set(ControlMode.PercentOutput, 0);
      motorBandaB.set(ControlMode.PercentOutput, 0);

      ready = true;
    } 

    else if (s1 == false && s4 == true || 
             s1 == false && s2 == false && s3 == false && s4 == false ||
             s1 == false && s2 == true && s4 == false){ // 0--1
      intake.set(ControlMode.PercentOutput, intake_Velocity);
      motorBandaA.set(ControlMode.PercentOutput, bandaA_Velocity);
      motorBandaB.set(ControlMode.PercentOutput, 0);

      ready = false;
    }
/*
    else if (s1 == false && s2 == false && s3 == false && s4 == false){//0000
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }*/

    else if(s1 == true && s2 == false && s3 == false && s4 == false ||
            s1 == true && s2 == false && s3 == true && s4 == false ||
            s1 == true && s2 == true){//1000
      intake.set(ControlMode.PercentOutput, intake_Velocity);
      motorBandaA.set(ControlMode.PercentOutput, bandaA_Velocity);
      motorBandaB.set(ControlMode.PercentOutput, bandaB_Velocity);

      ready = false;
    }
    /*
    else if(s1 == false && s2 == true && s4 == false){//} s3 == false && s4 == false){
      //0100
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }*/
    /*
    else if(s1 == false && s2 == false && s3 == true && s4 == false){//0010
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(0);
    }*/
    /*
    else if(s1 == true && s2 == false && s3 == true && s4 == false){//1010
      intake.set(1);
      motorBandaA.set(1);
      motorBandaB.set(1);
    }*/
    /*
    else if (s1 == true && s2 == true){//&& s3 == false && s4 == false){//1100
      intake.set(ControlMode.PercentOutput, 1);
      motorBandaA.set(ControlMode.PercentOutput, 1);
      motorBandaB.set(ControlMode.PercentOutput, 1);
    }*/
    else {
      intake.set(ControlMode.PercentOutput, 0);
      motorBandaA.set(ControlMode.PercentOutput, 0);
      motorBandaB.set(ControlMode.PercentOutput, 0);

      ready = false;
    }

  }
  
  /**
   * Expulsa las power cells hacia el disparador.
   */
  public void ejectBallstoShooter(){
    /*if (s1 == false && s2 == false && s3 == false && s4 == false){
      intake.set(ControlMode.PercentOutput, intake_Velocity);
      motorBandaA.set(ControlMode.PercentOutput, 0);
      motorBandaB.set(ControlMode.PercentOutput, 0);
    }
    else {
      intake.set(ControlMode.PercentOutput, intake_Velocity);
      motorBandaA.set(ControlMode.PercentOutput, bandaA_Velocity);
      motorBandaB.set(ControlMode.PercentOutput, bandaB_Velocity);
    }*/
    
    intake.set(ControlMode.PercentOutput, intake_Velocity);
    motorBandaA.set(ControlMode.PercentOutput, bandaA_Velocity);
    motorBandaB.set(ControlMode.PercentOutput, bandaB_Velocity);
  }


  /**
   * Expulsa las power cells hacía fuera del robot.
   */
  public void ejectBallstoOut(){
    intake.set(ControlMode.PercentOutput, -intake_Velocity);
      motorBandaA.set(ControlMode.PercentOutput, -bandaA_Velocity);
      motorBandaB.set(ControlMode.PercentOutput, -bandaB_Velocity);

  }

  /**
   * Extiende el intake.
   */
  public void toExtendIntake(){
    pistonA.set(true);
  }

  /**
   * Guarda el intake.
   */
  public void saveIntake(){
    pistonA.set(false);
  }

  @Override
  public void periodic() {
    s1 = !sensor1.get();
    s2 = !sensor2.get();
    s3 = !sensor3.get();
    s4 = !sensor4.get();

    SmartDashboard.putBoolean("1", s1);
    SmartDashboard.putBoolean("2", s2);
    SmartDashboard.putBoolean("3", s3);
    SmartDashboard.putBoolean("4", s4);
    SmartDashboard.putBoolean("READY?", ready);
    
  }
}