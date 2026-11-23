package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.systems.helpers.GoalManager;
import org.firstinspires.ftc.teamcode.systems.helpers.GlobalStates;
import org.firstinspires.ftc.teamcode.systems.helpers.Point2D;

import dev.frozenmilk.dairy.cachinghardware.CachingServo;

/// make a settings inside class ///
public class Shooter {
    private final CachingServo servoGate, servoHood;

    public class Settings{
        public String servo_gate_name;
        public String servo_hood_name;

        /// servo positions ///
        public double min_hood_angle = 0.8;
        public double max_hood_angle = 0.8;

        public double gate_open_angle = 0.8;
        public double gate_closed_angle = 0.54;

        /// ---> separate flywheel class <--- ///
        public int max_cycle_time_ms = 800;

        public double tuning_hood_angle = 0;
        public double tuning_flywheel_velocity = 0;

        public FlywheelMotor.Settings flywheel_settings;
    };

    /// shooter states and timer ///
    private enum ShooterState{ RUNNING, SHOOTING, STOPPED };
    private ShooterState shooterState, previousState;

    private ElapsedTime shootTimer;
    private double last_timestamp = 0;

    private Settings settings;
    private FlywheelMotor flywheel;

    /// ---> class constructor <--- ///
    public Shooter(HardwareMap hardwareMap, Settings settings){
        this.settings = settings;

        flywheel = new FlywheelMotor(hardwareMap, this.settings.flywheel_settings);
        servoGate = new CachingServo(hardwareMap.get(Servo.class, this.settings.servo_gate_name));
        servoHood = new CachingServo(hardwareMap.get(Servo.class, this.settings.servo_hood_name));

        /// init simpler variables ///
        shootTimer = new ElapsedTime();
        shooterState = ShooterState.STOPPED;
        previousState = ShooterState.STOPPED;
    }

    /// ---> shooting methods :D ///
    public void setGateOpened(){ servoGate.setPosition(this.settings.gate_open_angle); }
    public void setGateClosed(){ servoGate.setPosition(this.settings.gate_closed_angle); }

    public void update_shooter(double x, double y, boolean is_turret_ready){
        Point2D goal = GoalManager.getGoalPose(x, y);
        double hive_offset = GoalManager.getHiveOffsetX(x, y, goal);

        double distance_to_hive = Math.hypot(
            x - goal.x + hive_offset, y - goal.y
        );

        /// take a timestamp / reset timer ///
        if(shooterState == ShooterState.RUNNING && previousState != ShooterState.RUNNING){
            last_timestamp = shootTimer.milliseconds();
        }

        /// do this :) ///
        double flywheel_target_speed, hood_target_speed;

        if(GlobalStates.runningState != GlobalStates.RunningState.TUNING){
            flywheel_target_speed = compute_flywheel_velocity(distance_to_hive);
            hood_target_speed = compute_hood_angle(distance_to_hive);
        }else{ /// we are in tuning mode ///
            flywheel_target_speed = this.settings.tuning_flywheel_velocity;
            hood_target_speed = this.settings.tuning_hood_angle;
        }

        switch(shooterState){
            case RUNNING:
                setGateClosed();
                flywheel.setPower(flywheel_target_speed);
                servoHood.setPosition(hood_target_speed);

                break;
            case SHOOTING:
                flywheel.setPower(flywheel_target_speed);
                servoHood.setPosition(hood_target_speed);

                if(flywheel.isReady() && is_turret_ready){
                    setGateOpened();
                    if(shootTimer.milliseconds() - last_timestamp > this.settings.max_cycle_time_ms){
                        shooterState = ShooterState.RUNNING;
                    }
                }
                break;
            case STOPPED:
                setGateClosed();
                flywheel.stop();
                break;
        }
    }

    public double compute_flywheel_velocity(double dist){
        return 0; /// need to put
    }

    public double compute_hood_angle(double dist){
        return 0.8f; /// need to put
    }
}
