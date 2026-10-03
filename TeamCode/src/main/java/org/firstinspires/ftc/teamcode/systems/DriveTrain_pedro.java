package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
public class DriveTrain_pedro {
    public Follower follower;

    public DriveTrain_pedro(HardwareMap hardwareMap) {
        follower = Constants.create(hardwareMap);
    }

    public void updateFieldCentric(Gamepad gamepad) {
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad.left_stick_y,
                gamepad.left_stick_x,
                gamepad.right_stick_x,
                follower.pose().heading()
        );

        follower.manual(powers);
        follower.update();
    }
    public void updateRobotCentric(Gamepad gamepad) {
        follower.manual(
            -gamepad.left_stick_y,
            gamepad.left_stick_x,
            gamepad.right_stick_x
        );

        follower.update();
    }
}
