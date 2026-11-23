package org.firstinspires.ftc.teamcode.systems;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.systems.helpers.GoalManager;
import org.firstinspires.ftc.teamcode.systems.helpers.MathTricks;
import org.firstinspires.ftc.teamcode.systems.helpers.Point2D;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;

public class Turret {
    private final CachingServo servoLeftt;
    private final CachingServo servoRightt;

    public class Settings { /// turret variables ///
        public String servo_leftt_name;
        public String servo_rightt_name;

        public double shooter_offset = -1.811;

        public int min_turret_angle = -180;
        public int max_turret_angle = +180;

        public double min_servo_pos = 0.045;
        public double max_servo_pos = 0.960;

        public double turret_angle_offset = 0f;

        /// make the turret be [-180-over_limit_angle, +180 + over_limit_angle] ///
        public int over_limit_angle = 0;
    }

    private Settings settings;

    public Turret(HardwareMap hardwareMap, Settings settings){
        this.settings = settings;

        servoLeftt = new CachingServo(hardwareMap.get(Servo.class, this.settings.servo_leftt_name));
        servoRightt = new CachingServo(hardwareMap.get(Servo.class, this.settings.servo_rightt_name));
    }

    private void setAngle(double angle){
        servoLeftt.setPosition(angle);
        servoRightt.setPosition(angle);
    }

    public void update_turret(Pose follower_pose){
        double x = follower_pose.x(), y = follower_pose.y();
        double heading = MathTricks.angleWrap(follower_pose.heading());

        /// get 2d points for shooter center and hive pose :D ///
        Point2D shooter_world = new Point2D(
            x + (this.settings.shooter_offset * Math.cos(heading)),
            y + (this.settings.shooter_offset * Math.sin(heading))
        );

        Point2D goal = GoalManager.getGoalPose(x, y);

        /// angle computing ///
        double hive_offset = GoalManager.getHiveOffsetX(x, y, goal);
        double angle = Math.atan2(goal.y - shooter_world.y, goal.x - shooter_world.x + hive_offset);

        double relative_angle = MathTricks.angleWrap(angle - heading + Math.toRadians(this.settings.turret_angle_offset));

        double target_servo_angle = MathTricks.clamp(
            relative_angle,
            Math.toRadians(this.settings.min_turret_angle),
            Math.toRadians(this.settings.max_turret_angle)
        );

        double target_servo_pos = Range.scale(
            target_servo_angle,
            Math.toRadians(this.settings.min_turret_angle), Math.toRadians(this.settings.max_turret_angle),
            this.settings.min_servo_pos, this.settings.max_servo_pos
        );

        setAngle(target_servo_pos);
    }

    public boolean isReady(){
        return true; /// needs to be updated ///
    }
}
