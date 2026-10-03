package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig driveConfig = new MecanumConfig(
        c -> {
            c.frontLeftName.set("FLM");
            c.frontRightName.set("FRM");
            c.backRightName.set("BRM");
            c.backLeftName.set("BLM");

            c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
            c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
            c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
            c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);

            c.manualBrakeMode.set(true); /// set zero power behaviour to brake ///
        }
    );

    /// gun to my head if i understand anything about this ///
    // public static PinpointConfig localizerConfig = new PinpointConfig(
    //     c -> {
    //         c.name.set("pinpoint");
    //         c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
    //         c.xPodOffset.set(7.184293251337968);
    //         c.yPodOffset.set(0.45273972308541843);
    //         c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    //         c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    //         c.globalDistanceUnit.set(DistanceUnit.INCH);
    //         c.offsetUnits.set(DistanceUnit.INCH);
    //     }
    // );

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(7.014559047428642);
        c.yPodOffset.set(2.639961843415508);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    // public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
    //     c.name.set("pinpoint");
    //     c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
    //     c.xPodOffset.set(7.605369147353285);
    //     c.yPodOffset.set(1.4645558454858978);
    //     c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    //     c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    //     c.globalDistanceUnit.set(DistanceUnit.INCH);
    //     c.offsetUnits.set(DistanceUnit.INCH);
    // });

    // public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
    //     c.name.set("pinpoint");
    //     c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
    //     c.xPodOffset.set(7.096382201187254);
    //     c.yPodOffset.set(2.8296433095856917);
    //     c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    //     c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    //     c.globalDistanceUnit.set(DistanceUnit.INCH);
    //     c.offsetUnits.set(DistanceUnit.INCH);
    // });

    // public static ForesightConfig foresightConfig = new ForesightConfig(
    //     c -> {
    //         Controller primaryTranslationalForward = Controller.proportional(0.37456851359327037);
    //         Controller secondaryTranslationalForward = Controller.proportional(0.13839305711769814);
    //         Controller primaryTranslationalLateral = Controller.proportional(0.6572100611810343);
    //         Controller secondaryTranslationalLateral = Controller.proportional(0.24282155663013227);
    //
    //         c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
    //         c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));
    //
    //         c.coast.set(Controller.proportionalFeedforward(0.010864024608218087));
    //         c.brake.set(Controller.proportionalFeedforward(0.009234420916985373));
    //
    //         c.headingFeedback.set(Controller.proportional(6.841690979859027));
    //         c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05618481940060934, 0.009979346535831315));
    //
    //         c.linearBrakeCoefficients.set(Matrix.diag(0.15170201453998028, 0.0594474208115488));
    //         c.quadraticBrakeCoefficients.set(Matrix.diag(0.0019129349131026922, 0.002714744369435937));
    //
    //         c.maxAchievableForwardVelocity.set(82.06755695895417);
    //         c.maxAchievableStrafeVelocity.set(68.40576786198412);
    //         c.naturalForwardDeceleration.set(27.04362677982182);
    //         c.naturalStrafeDeceleration.set(61.30078700278792);
    //     }
    // );

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.40615812808549134);
                Controller secondaryTranslationalForward = Controller.proportional(0.15006457558252875);
                Controller primaryTranslationalLateral = Controller.proportional(0.7055623974686387);
                Controller secondaryTranslationalLateral = Controller.proportional(0.26068645289018144);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.010323122936498659));
                c.brake.set(Controller.proportionalFeedforward(0.008774654496023859));

                c.headingFeedback.set(Controller.proportional(7.491503206968922));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.07438765650292697, 0.00815372441130985));

                c.linearBrakeCoefficients.set(Matrix.diag(0.2580742941175198, 0.09445835524143824));
                c.quadraticBrakeCoefficients.set(Matrix.diag(3.7015649536997124E-4, 0.0021060706110898686));

                c.maxAchievableForwardVelocity.set(86.44822570468862);
                c.maxAchievableStrafeVelocity.set(68.09834359316461);
                c.naturalForwardDeceleration.set(24.132922765754618);
                c.naturalStrafeDeceleration.set(59.093466697381984);
            }
    );

    public static Follower create(HardwareMap hardwareMap) {
        return new Follower(
            new PinpointLocalizer(hardwareMap, localizerConfig),
            new Mecanum(hardwareMap, driveConfig),
            new Foresight(foresightConfig)
        );
    }
}