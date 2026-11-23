package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
public class DriveTrainPedro {
    public Follower follower;

    public double kp_align = 0.6;
    public double turn_multiplier = 1.1;

    /// set positions and heading ///
    public DriveTrainPedro(HardwareMap hardwareMap) {
        follower = Constants.create(hardwareMap);
    }

    public void updateFieldCentric(Gamepad gamepad) {
        double turn_value = turn_multiplier * (-gamepad.left_trigger + gamepad.right_trigger);

        /// super simple drive :) ///
        DrivePowers powers = ManualDrive.fieldCentric(
            -gamepad.left_stick_y,
            -gamepad.left_stick_x,
            -turn_value,
            follower.pose().heading()
        );

        follower.manual(powers);
        follower.update();
    }
}
