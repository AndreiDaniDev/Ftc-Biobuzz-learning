package org.firstinspires.ftc.teamcode.systems.helpers;

public class MathTricks {
    public static double angleWrap(double angle) {
        while (angle < -Math.PI) angle += 2 * Math.PI;
        while (angle >= Math.PI) angle -= 2 * Math.PI;
        return angle;
    }

    public static double clamp(double val, double val_min, double val_max){
        return Math.max(val_min, Math.min(val, val_max));
    }
}