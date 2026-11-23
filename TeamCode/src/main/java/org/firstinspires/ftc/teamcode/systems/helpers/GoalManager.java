package org.firstinspires.ftc.teamcode.systems.helpers;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class GoalManager {
    public static Point2D GOAL_SUP = new Point2D(56, 92);
    public static Point2D GOAL_INF = new Point2D(62, 52);

    public static double midline = 70.7;

    public static Point2D getGoalPose(double x, double y){
        return (y <= midline) ? GOAL_INF : GOAL_SUP;
    }

    public static double getHiveOffsetX(double x, double y, Point2D goal_pose){
        return 0f; /// nothing implemented for now
    }
}
