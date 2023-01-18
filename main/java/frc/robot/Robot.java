package frc.robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
 
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


import java.lang.Math;
 
import javax.sound.sampled.SourceDataLine;
public class Robot extends TimedRobot {
 
 private static final int rightMotor0ID = 1;
 private static final int rightMotor1ID = 2;
 private static final int leftMotor0ID = 3;
 private static final int leftMotor1ID = 4;
 
 
private CANSparkMax leftMotor0;
private CANSparkMax leftMotor1;
 
private CANSparkMax rightMotor0;
private CANSparkMax rightMotor1;
 
public static final ADIS16470_IMU imu = new ADIS16470_IMU();
private DifferentialDrive m_robotDrive;
private final Joystick m_stick = new Joystick(0);
//private final XboxController x_stick = new XboxController(1);
 
 
@Override
public void robotInit() {
 leftMotor0 = new CANSparkMax(leftMotor0ID, MotorType.kBrushless);
 leftMotor1 = new CANSparkMax(leftMotor1ID, MotorType.kBrushless);
 
 rightMotor0 = new CANSparkMax(rightMotor0ID, MotorType.kBrushless);
 rightMotor1 = new CANSparkMax(rightMotor1ID, MotorType.kBrushless);
 
  MotorControllerGroup rightMotors = new MotorControllerGroup(rightMotor0, rightMotor1);
 MotorControllerGroup leftMotors = new MotorControllerGroup(leftMotor0, leftMotor1);
 
 leftMotor0.setInverted(true);
 
 m_robotDrive = new DifferentialDrive(rightMotors, leftMotors);
}
@Override
public void teleopPeriodic() {
  m_robotDrive.arcadeDrive( -m_stick.getY(),m_stick.getX() );
 
}
}

