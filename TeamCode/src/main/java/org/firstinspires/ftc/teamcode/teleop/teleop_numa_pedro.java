package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.systems.DriveTrain_pedro;

@TeleOp(name = "TeleOP - Pedro - Field Centric", group = "Linear OpMode")
public class teleop_numa_pedro extends LinearOpMode {

    DriveTrain_pedro driveTrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("status : ", "initialized :)");
        telemetry.update(); /// status update to driver hub ///

        driveTrain = new DriveTrain_pedro(hardwareMap);

        waitForStart();

        if(isStopRequested()){ return; }

        for(; opModeIsActive(); ) {
            driveTrain.updateFieldCentric(gamepad1);
        }

        return; /// i like it with this :)
    }
}
