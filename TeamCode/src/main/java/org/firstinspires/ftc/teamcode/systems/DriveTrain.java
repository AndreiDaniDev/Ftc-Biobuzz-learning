package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class DriveTrain {
    DcMotor frontLeftMotor, frontRightMotor;
    DcMotor backLeftMotor, backRightMotor;
    public static double rpm =0.0;

    ///  init function :) ///
    public DriveTrain(HardwareMap hardwareMap){
        /// init motors :) ///
        frontLeftMotor = hardwareMap.dcMotor.get("FL");
        frontRightMotor = hardwareMap.dcMotor.get("FR");

        backRightMotor = hardwareMap.dcMotor.get("BR");
        backLeftMotor = hardwareMap.dcMotor.get("BL");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    };

    public void updateDriveTrain(Gamepad gamepad1){
        double xx = +gamepad1.left_stick_x * 1.1;
        double yy = -gamepad1.left_stick_y;

        double rotatexx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(xx) + Math.abs(yy) + Math.abs(rotatexx), 1);
        double frontLeftPower = (yy + xx + rotatexx) / denominator;
        double backLeftPower = (yy - xx + rotatexx) / denominator;
        double frontRightPower = (yy - xx - rotatexx) / denominator;
        double backRightPower = (yy + xx - rotatexx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        return;
    }
}
