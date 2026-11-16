package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.configurables.annotations.Configurable;
import org.firstinspires.ftc.teamcode.tricks.Point2D;

import org.firstinspires.ftc.teamcode.GlobalStates;

@Configurable
public class GoalManager {
    public static Point2D RED_GOAL_SUP = new Point2D(56, 92);
    public static Point2D RED_GOAL_INF = new Point2D(62, 52);
    public static Point2D BLUE_GOAL_SUP = new Point2D(84, 82);
    public static Point2D BLUE_GOAL_INF = new Point2D(84, 58);

    public static double midline = 70.7;

    public static Point2D getGoalPose(double x, double y){
        if(GlobalStates.alliance == GlobalStates.Alliance.RED){
            return (y <= midline) ? RED_GOAL_INF : RED_GOAL_SUP;
        }
        return (y <= midline) ? BLUE_GOAL_INF : BLUE_GOAL_SUP;
    }

    public static double getHiveOffsetX(double x, double y, Point2D goal_pose){
        return 0f; /// nothing implemented for now
    }
}
