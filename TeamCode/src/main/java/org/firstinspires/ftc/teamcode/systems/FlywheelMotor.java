package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.tricks.MathTricks;
import org.opencv.core.Mat;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;

@Configurable
public class FlywheelMotor {
    public CachingDcMotorEx motorShooter;
    public static double min_shooter_rpm = 1380f;
    public static double max_shooter_rpm = 1530f;

    public static int max_delta_speed = 100;
    public static double nominalVoltage = 12.0f;

    public static double kP = 0.005, kV = 0.00036, kS = 0.12;

    private VoltageSensor volageSensor;
    private double target_velocity;

    public FlywheelMotor(HardwareMap hardwareMap, String motorName){
        motorShooter = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, motorName));
        motorShooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorShooter.setDirection(DcMotorSimple.Direction.FORWARD);
        motorShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorShooter.setCachingTolerance(0.001);

        volageSensor = hardwareMap.voltageSensor.iterator().next();
    }

    /// let the flywheel float ///
    public void stop(){ motorShooter.setPower(0); }

    /// pid calculations ///
    public void setPower(double param_target_velocity){
        double active_velocity = motorShooter.getVelocity();
        target_velocity = param_target_velocity;

        double error = target_velocity - active_velocity;
        double voltageScaling = nominalVoltage / volageSensor.getVoltage();

        double proportional = (kP * error);
        double feedforward = (kV * target_velocity) + kS;

        double pow = (proportional + feedforward) * voltageScaling;
        MathTricks.clamp(pow, -1f, +1f);

        motorShooter.setPower(pow);
    }

    public boolean isReady(){
        return Math.abs(target_velocity - motorShooter.getVelocity()) <= max_delta_speed;
    }
}
