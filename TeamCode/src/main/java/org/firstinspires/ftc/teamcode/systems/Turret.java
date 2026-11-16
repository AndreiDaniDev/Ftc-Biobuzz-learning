package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.tricks.MathTricks;
import org.firstinspires.ftc.teamcode.tricks.Point2D;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;

@Configurable
public class Turret {
    public static CachingServo servoLeftt;
    public static CachingServo servoRightt;

    /// robot variables about positions ///
    public static double shooter_offset = -1.811;

    public static int min_turret_angle = -180;
    public static int max_turret_angle = +180;

    public static double min_servo_pos = 0.045;
    public static double max_servo_pos = 0.960;

    public static double turret_angle_offset = 0f;

    /// make the turret be [-180 - over_limit_angle, +180 + over_limit_angle] ///
    public static int over_limit_angle = +10;

    public Turret(HardwareMap hardwareMap){
        servoLeftt = new CachingServo(hardwareMap.get(Servo.class, "Turela_left"));
        servoRightt = new CachingServo(hardwareMap.get(Servo.class, "Turela_right"));
    }

    public void setAngle(double angle){
        servoLeftt.setPosition(angle);
        servoRightt.setPosition(angle);
    }

    public void update_turret(Pose follower_pose){
        double x = follower_pose.x(), y = follower_pose.y();
        double heading = MathTricks.angleWrap(follower_pose.heading());

        /// get 2d points for shooter center and hive pose :D ///
        Point2D shooter_world = new Point2D(
            x + (shooter_offset * Math.cos(heading)),
            y + (shooter_offset * Math.sin(heading))
        );

        Point2D goal = GoalManager.getGoalPose(x, y);

        /// angle computing ///
        double hive_offset = GoalManager.getHiveOffsetX(x, y, goal);
        double angle = Math.atan2(goal.y - shooter_world.y, goal.x - shooter_world.x + hive_offset);

        double relative_angle = MathTricks.angleWrap(angle - heading + Math.toRadians(turret_angle_offset));

        double target_servo_angle = MathTricks.clamp(
            relative_angle,
            Math.toRadians(min_turret_angle),
            Math.toRadians(max_turret_angle)
        );

        double target_servo_pos = Range.scale(
            target_servo_angle,
            Math.toRadians(min_turret_angle), Math.toRadians(max_turret_angle),
            min_servo_pos, max_servo_pos
        );

        setAngle(target_servo_pos);
    }
}
