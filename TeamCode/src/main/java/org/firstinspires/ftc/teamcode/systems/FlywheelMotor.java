package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.systems.helpers.MathTricks;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;

public class FlywheelMotor {
    private final CachingDcMotorEx motorShooter;

    class Settings{
        public String motor_name;

        public double min_shooter_rpm = 1380f;
        public double max_shooter_rpm = 1530f;

        public int max_delta_speed = 100;
        public double nominalVoltage = 12.0f;

        public double kP = 0.005, kV = 0.00036, kS = 0.12;
    }

    private Settings settings;

    private VoltageSensor volageSensor;
    private double target_velocity;

    public FlywheelMotor(HardwareMap hardwareMap, Settings settings){
        this.settings = settings;

        motorShooter = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, this.settings.motor_name));
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
        double voltageScaling = this.settings.nominalVoltage / volageSensor.getVoltage();

        double proportional = (this.settings.kP * error);
        double feedforward = (this.settings.kV * target_velocity) + this.settings.kS;

        double pow = (proportional + feedforward) * voltageScaling;
        MathTricks.clamp(pow, -1f, +1f);

        motorShooter.setPower(pow);
    }

    public boolean isReady(){
        return Math.abs(target_velocity - motorShooter.getVelocity()) <= this.settings.max_delta_speed;
    }
}
