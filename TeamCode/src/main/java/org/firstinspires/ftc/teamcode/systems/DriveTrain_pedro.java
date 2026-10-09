package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MathTricks;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
public class DriveTrain_pedro {
    public Follower follower;

    /// follow target angle to shoot :) ///
    private boolean toggle_follow_target;
    private boolean last_state_toggle;

    /// need to modify this the actual positions ///
    private Pose target_hive_left = new Pose(0.0, 0.0);
    private Pose target_hive_right = new Pose(0.0, 0.0);
    private static Pose target_hive;
    public static int half_pointx = 0;

    public double kp_align = 0.6;

    public DriveTrain_pedro(HardwareMap hardwareMap) {
        follower = Constants.create(hardwareMap);
        toggle_follow_target = false;

        /// set positions and heading ///
        follower.setX(0.0); follower.setY(0.0);
        follower.setHeading(0.0);
    }

    /// i don't know if this works, also i need to go get the target hive left and right ///
    public void updateTargetCentric(Gamepad gamepad){
        /// set the target hive :>> ///
        target_hive = ((follower.pose().x() < half_pointx) ? target_hive_left : target_hive_right);

        double angle = Math.atan2(
            target_hive.y() - follower.pose().y(),
            target_hive.x() - follower.pose().x()
        );

        double heading_error = MathTricks.angleWrap(angle - follower.pose().heading());
        double turn_rate = kp_align * heading_error;

        DrivePowers powers = ManualDrive.fieldCentric(
            -gamepad.left_stick_y,
            -gamepad.left_stick_x,
            turn_rate,
            follower.pose().heading()
        );
        follower.manual(powers);
        follower.update();
    }

    public void updateFieldCentric(Gamepad gamepad) {
        if(gamepad.a && !last_state_toggle){
            toggle_follow_target = !toggle_follow_target;
            last_state_toggle = true;
        } else if(!gamepad.a){
            last_state_toggle = false;
        }

        if(toggle_follow_target){ updateTargetCentric(gamepad); return; }

        /// super simple drive :) ///
        DrivePowers powers = ManualDrive.fieldCentric(
            -gamepad.left_stick_y,
            -gamepad.left_stick_x,
            -gamepad.right_stick_x,
            follower.pose().heading()
        );

        follower.manual(powers);
        follower.update();
    }
}
