package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.systems.DriveTrainPedro;
import org.firstinspires.ftc.teamcode.systems.IntakeTransfer;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Turret;

@Configurable
public class LittleDash {
    public DriveTrainPedro driveTrain;
    public IntakeTransfer intakeTransfer;
    public Shooter shooter;
    public Turret turret;

    public static Turret.Settings turret_settings;
    public static Shooter.Settings shooter_settings;

    static { /// init parameters for turret ///
        turret_settings.servo_leftt_name = "Turela_left";
        turret_settings.servo_rightt_name = "Turela_right";
    };

    static { /// init parameters for shooter ///
        shooter_settings.servo_gate_name = "Gate";
        shooter_settings.servo_hood_name = "Reglaj";
        shooter_settings.flywheel_settings.motor_name = "MSHT";
    };

    public static Pose starting_pose = new Pose(9, 9, Math.toRadians(90));

    public LittleDash(HardwareMap hardwareMap){
        driveTrain = new DriveTrainPedro(hardwareMap);
        intakeTransfer = new IntakeTransfer(hardwareMap);
        turret = new Turret(hardwareMap, turret_settings);
        shooter = new Shooter(hardwareMap, shooter_settings);
    }

    public void setPose(Pose pose){ driveTrain.follower.setPose(pose); }
}
