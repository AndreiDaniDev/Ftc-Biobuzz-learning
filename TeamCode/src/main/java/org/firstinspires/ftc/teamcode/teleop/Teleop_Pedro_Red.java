package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GlobalStates;
import org.firstinspires.ftc.teamcode.systems.DriveTrainPedro;
import org.firstinspires.ftc.teamcode.systems.IntakeTransfer;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Turret;

@TeleOp(name = "TeleOP - Pedro - Field Centric", group = "Linear OpMode")
public class Teleop_Pedro_Red extends LinearOpMode {

    private DriveTrainPedro driveTrain;
    private Turret turret;
    private Shooter shooter;
    private IntakeTransfer intakeTransfer;

    /// starting pose for red alliance ///
    private Pose starting_pose = new Pose(9, 9, Math.toRadians(90));

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("status : ", "initialized :)");
        telemetry.update(); /// status update to driver hub ///

        driveTrain = new DriveTrainPedro(hardwareMap);
        intakeTransfer = new IntakeTransfer(hardwareMap);
        turret = new Turret(hardwareMap);
        shooter = new Shooter(hardwareMap);

        GlobalStates.alliance = GlobalStates.Alliance.RED;
        GlobalStates.runningState = GlobalStates.RunningState.TELEOP;

        waitForStart();

        if(isStopRequested()){ return; }

        for(; opModeIsActive(); ) {
            telemetry.addData("pose x : ", driveTrain.follower.pose().x());
            telemetry.addData("pose y : ", driveTrain.follower.pose().y());
            telemetry.addData("heading : ", Math.toDegrees(driveTrain.follower.pose().heading()));
            telemetry.update();

            if(gamepad1.dpad_down){ driveTrain.resetPose(starting_pose); }
            driveTrain.updateFieldCentric(gamepad1);

            if(gamepad1.circle){ intakeTransfer.feedCollect(); }else{ intakeTransfer.feedReverse(); }

            /// ---> system updates <--- ///
            Pose robot_pose = driveTrain.follower.pose();
            turret.update_turret(robot_pose);
            shooter.update_shooter(robot_pose.x(), robot_pose.y());
        }

        return; /// i like it with this :)
    }
}
