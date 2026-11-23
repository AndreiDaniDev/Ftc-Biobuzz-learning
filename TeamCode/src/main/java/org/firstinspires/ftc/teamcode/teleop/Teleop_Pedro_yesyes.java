package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LittleDash;
import org.firstinspires.ftc.teamcode.systems.helpers.GlobalStates;
import org.firstinspires.ftc.teamcode.systems.DriveTrainPedro;
import org.firstinspires.ftc.teamcode.systems.IntakeTransfer;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Turret;

@TeleOp(name = "TeleOP - Pedro - Field Centric", group = "Linear OpMode")
public class Teleop_Pedro_yesyes extends LinearOpMode {

    private LittleDash lilDash;

    /// starting pose for red alliance ///
    private Pose starting_pose = new Pose(9, 9, Math.toRadians(90));

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("status : ", "initialized :)");
        telemetry.update(); /// status update to driver hub ///

        GlobalStates.runningState = GlobalStates.RunningState.TELEOP;

        waitForStart();

        if(isStopRequested()){ return; }

        for(; opModeIsActive(); ) {
            telemetry.addData("pose x : ", lilDash.driveTrain.follower.pose().x());
            telemetry.addData("pose y : ", lilDash.driveTrain.follower.pose().y());
            telemetry.addData("heading : ", Math.toDegrees(lilDash.driveTrain.follower.pose().heading()));
            telemetry.update();

            if(gamepad1.dpad_down){ lilDash.setPose(starting_pose); }
            lilDash.driveTrain.updateFieldCentric(gamepad1);

            /// ---> system updates <--- ///
            Pose robot_pose = lilDash.driveTrain.follower.pose();
            lilDash.turret.update_turret(robot_pose);
            lilDash.shooter.update_shooter(robot_pose.x(), robot_pose.y(), lilDash.turret.isReady());
        }

        return; /// i like it with this :)
    }
}
