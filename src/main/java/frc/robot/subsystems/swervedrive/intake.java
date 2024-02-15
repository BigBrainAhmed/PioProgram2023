package frc.robot.subsystems.swervedrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class intake 
{
    private final CANSparkMax intakeMotor;
    private long seconds = 0;
    public intake(int driveMotorChannelA)
    {
        intakeMotor = new CANSparkMax(driveMotorChannelA, MotorType.kBrushless);
    }

    public void grab()
    {
        long startTime = System.currentTimeMillis();
        while(seconds <=1)
        {
            intakeMotor.set(1);
            long endTime = System.currentTimeMillis();
            seconds = endTime - startTime;
        }
        intakeMotor.set(0); 
    }
}
