// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  private static final double P_PID = 0.0002;
  private static final double I_PID = 0.;
  private static final double D_PID = 0.;
  private static final double I_PID_ZONE = 0.;
  private static final double RIGHT_FEED_FORWARD_PID = 0.000175;

  private final CANSparkMax m_indexMotor;

  private final CANSparkMax m_rightShootMotor;

  private final RelativeEncoder m_rightShootMotorEncoder;

  private final SparkMaxPIDController m_rightShootMotorPIDController;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    this.m_indexMotor = new CANSparkMax(14, MotorType.kBrushless);

    this.m_rightShootMotor = new CANSparkMax(17, MotorType.kBrushless);
    this.m_rightShootMotorEncoder = this.m_rightShootMotor.getEncoder();

    this.m_rightShootMotorPIDController = this.m_rightShootMotor.getPIDController();

    initializePID();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void initializePID() {

    m_rightShootMotorPIDController.setP(P_PID);
    m_rightShootMotorPIDController.setI(I_PID);
    m_rightShootMotorPIDController.setD(D_PID);
    m_rightShootMotorPIDController.setIZone(I_PID_ZONE);
    m_rightShootMotorPIDController.setFF(RIGHT_FEED_FORWARD_PID);
    m_rightShootMotorPIDController.setOutputRange(0, 1);

    m_rightShootMotor.burnFlash();
  }

  public double getCurrentShooterSpeed() {
    return m_rightShootMotorEncoder.getVelocity();
  }

  public void setShooterSpeed(double speed) {
    if (speed == 0) {
      m_rightShootMotor.set(0);
      return;
    } else if (speed < 0) {
      m_rightShootMotor.set(speed);
      return;
    }
    speed += 200; // FIXME Corrects for PID-mistuning
    m_rightShootMotorPIDController.setReference(speed, ControlType.kVelocity);
  }

  public void setIndexerSpeed(double speed) {
    m_indexMotor.set(speed);
  }

}
