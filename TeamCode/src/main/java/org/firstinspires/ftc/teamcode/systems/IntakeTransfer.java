package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;
import kotlin.collections.UCollectionsKt;

@Configurable
public class IntakeTransfer {
    public static CachingDcMotorEx motorIntake, motorTransfer;
    public static CachingServo servoRampLeftt, servoRampRightt;

    /// angles for ramp ///
    public static double ramp_angle_low = 0.07;
    public static double ramp_angle_high = 0.95;

    /// collect pollen constants ///
    public static double intake_collect_power = 0.8;
    public static double transfer_collect_power = 0.5;

    /// shoot pollen constants ///
    public static double intake_shoot_power = 0.8;
    public static double transfer_shoot_power = 0.5;

    public static double global_reverse_power = -1;

    private boolean collecting_flower = false;

    /// ---> init class and get motors <--- ///
    public IntakeTransfer(HardwareMap hardwareMap){
        motorIntake = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, "MINT"));
        motorTransfer = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, "MTR"));

        servoRampLeftt = new CachingServo(hardwareMap.get(Servo.class, "RampaLeft"));
        servoRampRightt = new CachingServo(hardwareMap.get(Servo.class, "RampaRight"));

        motorIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        motorTransfer.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /// intake and transfer methods ///
    public void feedCollect(){
        motorIntake.setPower(intake_collect_power);
        motorTransfer.setPower(transfer_collect_power);
    }

    public void feedShoot(){
        motorIntake.setPower(intake_shoot_power);
        motorTransfer.setPower(transfer_shoot_power);
    }

    public void feedReverse(){
        motorIntake.setPower(global_reverse_power);
        motorTransfer.setPower(global_reverse_power);
    }

    public void feedOff(){
        motorIntake.setPower(0);
        motorTransfer.setPower(0);
    }

    /// flower ramp methods ///
    public void rampLow(){
        servoRampLeftt.setPosition(ramp_angle_low);
        servoRampRightt.setPosition(ramp_angle_low);
        collecting_flower = true;
    }

    public void rampHigh(){
        servoRampLeftt.setPosition(ramp_angle_high);
        servoRampRightt.setPosition(ramp_angle_high);
        collecting_flower = false;
    }

    public boolean getStateRamp(){
        return collecting_flower;
    }
}
