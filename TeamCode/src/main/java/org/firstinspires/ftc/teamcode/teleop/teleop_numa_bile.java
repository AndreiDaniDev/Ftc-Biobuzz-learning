package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.systems.DriveTrain;

@TeleOp(name = "TeleOP - Numai Bile", group = "Linear OpMode")
public class teleop_numa_bile extends LinearOpMode {

    DriveTrain driveTrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("status : ", "initialized :)");
        telemetry.update(); /// status update to driver hub ///

        driveTrain = new DriveTrain(hardwareMap);

        waitForStart();

        if(isStopRequested()){ return; }

        for(; opModeIsActive(); ) {
            driveTrain.updateDriveTrain(gamepad1);
        }

        return; /// i like it with this :)
    }
}
