package org.firstinspires.ftc.teamcode.systems;

import android.provider.Settings;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GlobalStates;
import org.firstinspires.ftc.teamcode.tricks.Point2D;

import dev.frozenmilk.dairy.cachinghardware.CachingServo;
import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;

/// make a settings inside class ///
@Configurable
public class Shooter {
    public final CachingServo servoGate, servoHood;

    /// servo positions ///
    public static double min_hood_angle = 0.8;
    public static double max_hood_angle = 0.8;

    public static double gate_open_angle = 0.8;
    public static double gate_closed_angle = 0.54;

    /// shooter state for simpler handling ///
    public enum ShooterState{ RUNNING, SHOOTING, STOPPED };
    public ShooterState shooterState, previousState;

    public ElapsedTime shootTimer;
    public double last_timestamp = 0;

    /// ---> separate flywheel class <--- ///
    public static FlywheelMotor flywheel;
    public static int max_cycle_time_ms = 800;

    public static double tuning_hood_angle = 0;
    public static double tuning_flywheel_velocity = 0;

    /// ---> class constructor <--- ///
    public Shooter(HardwareMap hardwareMap){
        flywheel = new FlywheelMotor(hardwareMap, "MSHT");
        servoGate = new CachingServo(hardwareMap.get(Servo.class, "Gate"));
        servoHood = new CachingServo(hardwareMap.get(Servo.class, "Reglaj"));

        /// init simpler variables ///
        shootTimer = new ElapsedTime();
        shooterState = ShooterState.SHOOTING;
        previousState = ShooterState.SHOOTING;
    }

    /// ---> shooting methods :D ///
    public void setGateOpened(){ servoGate.setPosition(gate_open_angle); }
    public void setGateClosed(){ servoGate.setPosition(gate_closed_angle); }

    public void update_shooter(double x, double y){
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
            flywheel_target_speed = tuning_flywheel_velocity;
            hood_target_speed = tuning_hood_angle;
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

                if(flywheel.isReady()){
                    setGateOpened();
                    if(shootTimer.milliseconds() - last_timestamp > max_cycle_time_ms){
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
