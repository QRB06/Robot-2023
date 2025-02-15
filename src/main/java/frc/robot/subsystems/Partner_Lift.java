// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Partner_Lift extends SubsystemBase {
  /** Creates a new Partner_Lift. */
  TalonFX partner_lift = new TalonFX(Constants.PartnerLiftID);
  public Partner_Lift() {
    this.partner_lift.setNeutralMode(NeutralMode.Brake);
  }

  public void Partner_Lift_On(double power){
    partner_lift.set(TalonFXControlMode.PercentOutput, power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
