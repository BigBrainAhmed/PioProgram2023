package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.motorcontrol.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import java.nio.file.attribute.AclEntryPermission;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;



public class Robot extends TimedRobot {

private static final int rightMotor0ID = 1;
private static final int rightMotor1ID = 2;
private static final int leftMotor0ID = 3;
private static final int leftMotor1ID = 4;
private static final int armRetracterID = 6;
private static final int armRaiserID = 7;

private CANSparkMax leftMotor0;
private CANSparkMax leftMotor1;
private CANSparkMax rightMotor0;
private CANSparkMax rightMotor1;
private CANSparkMax armRetracter;
private CANSparkMax armRaiser;

private DoubleSolenoid solenoid = new DoubleSolenoid(10, PneumaticsModuleType.REVPH, 7, 5);


double percentPower = 0.8;


private Timer elapsedTime = new Timer();


private final Joystick m_stick = new Joystick(0);
private DifferentialDrive m_robotDrive;

UsbCamera camera1;
UsbCamera camera2;

NetworkTableEntry cameraSelection;

VideoSink server;

boolean cameraSwitch = false;
boolean inEncoderLimit = true;

boolean disableEncoder;



@Override
public void robotInit(){

  camera1 = CameraServer.startAutomaticCapture(0);
  camera2 = CameraServer.startAutomaticCapture(1);
  server = CameraServer.getServer();

  camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
  camera2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

  
 leftMotor1 = new CANSparkMax(leftMotor1ID, MotorType.kBrushless);
 leftMotor0 = new CANSparkMax(leftMotor0ID, MotorType.kBrushless);
 rightMotor0 = new CANSparkMax(rightMotor0ID, MotorType.kBrushless);
 rightMotor1 = new CANSparkMax(rightMotor1ID, MotorType.kBrushless);
 armRetracter = new CANSparkMax(armRetracterID, MotorType.kBrushless);
 armRaiser = new CANSparkMax(armRaiserID, MotorType.kBrushless);


 MotorControllerGroup leftMotors = new MotorControllerGroup(leftMotor0, leftMotor1);
 MotorControllerGroup rightMotors = new MotorControllerGroup(rightMotor0, rightMotor1);

 leftMotors.setInverted(true);
 m_robotDrive = new DifferentialDrive(leftMotors, rightMotors);

 solenoid.set(DoubleSolenoid.Value.kForward);

 armRaiser.getEncoder().setPosition(0);
 armRetracter.getEncoder().setPosition(0);
 disableEncoder = false;
 
}




@Override
public void teleopPeriodic() {

  if (m_stick.getRawButtonPressed(2)) {
    if(cameraSwitch){
      server.setSource(camera2);
      cameraSwitch = false;
    } else {
      server.setSource(camera1);
      cameraSwitch = true;
    }
  }


 if(m_stick.getRawButton(8)){
   percentPower = 1;
 } else if (m_stick.getRawButton(10)){
   percentPower = 0.8;
 } else if(m_stick.getRawButton(12)){
  percentPower += 0.1;
 } else if(m_stick.getRawButtonPressed(11)){
   percentPower -= 0.1;
 }


if (m_stick.getRawButton(4)){
   armRetracter.set(0.8);
} else if(m_stick.getRawButton(6) && inEncoderLimit){
  armRetracter.set(-0.8);
} else {
   armRetracter.set(0);
}

if(m_stick.getRawButton(7)){
  disableEncoder = true;
  inEncoderLimit = true;
}

if(!disableEncoder){
  if(armRetracter.getEncoder().getPosition() < 5){
    inEncoderLimit = false;
  } else {
    inEncoderLimit = true;
  }
}

 if(m_stick.getRawButton(5)){
   armRaiser.set(0.8);
 } else if (m_stick.getRawButton(3)){
   armRaiser.set(-0.8);
 } else {
   armRaiser.set(0);
 }

 if(m_stick.getRawButton(9)){
  armRetracter.getEncoder().setPosition(0);
  armRaiser.getEncoder().setPosition(0);
 }


 if(m_stick.getTriggerPressed()){
   if(solenoid.get() == DoubleSolenoid.Value.kForward){
     solenoid.set(DoubleSolenoid.Value.kReverse);
   } else if (solenoid.get() == DoubleSolenoid.Value.kReverse){
     solenoid.set(DoubleSolenoid.Value.kForward);
   }
 }

 m_robotDrive.arcadeDrive( -m_stick.getY() * percentPower, m_stick.getTwist() * percentPower);

 SmartDashboard.putNumber("%Power", percentPower);
 SmartDashboard.putString("Value", solenoid.get().toString());
 SmartDashboard.putNumber("Raiser Rot", armRaiser.getEncoder().getPosition());
 SmartDashboard.putNumber("Puller Rot", armRetracter.getEncoder().getPosition());
 SmartDashboard.putBoolean("inEncoderLimit", inEncoderLimit);
 SmartDashboard.putBoolean("disableEncoder", disableEncoder);
}


public void autonomousInit(){
 elapsedTime.reset();
 elapsedTime.start();

 armRaiser.getEncoder().setPosition(0);
 armRetracter.getEncoder().setPosition(0);

 solenoid.set(DoubleSolenoid.Value.kForward);
}




public void autonomousPeriodic(){

  if(armRaiser.getEncoder().getPosition() < 20 && elapsedTime.get() < 2 && elapsedTime.get() > 1){
    armRaiser.set(0.8);
    armRetracter.set(0);
  } else if (armRaiser.getEncoder().getPosition() < 160 && elapsedTime.get() > 4 && elapsedTime.get() < 6){
    armRaiser.set(0.8);
  } else {
    armRaiser.set(0);
  }
  
  
  if (armRetracter.getEncoder().getPosition() < 18 && elapsedTime.get() > 2 && elapsedTime.get() < 4){
    armRetracter.set(0.8);
    armRaiser.set(0);
  } else if (armRetracter.getEncoder().getPosition() < 38 && elapsedTime.get() > 6 && elapsedTime.get() < 8){
    armRetracter.set(0.8);
  } else {
    armRetracter.set(0);
  }


  if (elapsedTime.get() > 8 && elapsedTime.get() < 8.5){
    solenoid.set(DoubleSolenoid.Value.kReverse);
  }

  if(elapsedTime.get() < 12.5 && elapsedTime.get() > 8.5){
    solenoid.set(DoubleSolenoid.Value.kForward);
    if(armRetracter.getEncoder().getPosition() > 10){
      armRetracter.set(-0.5);
    }
    m_robotDrive.arcadeDrive(-0.5, 0);
  }
}
 
}

