package org.firstinspires.ftc.teamcode;

public class MathTricks {
    public static double angleWrap(double angle) {
        while (angle < -Math.PI) angle += 2 * Math.PI;
        while (angle >= Math.PI) angle -= 2 * Math.PI;
        return angle;
    }
}
