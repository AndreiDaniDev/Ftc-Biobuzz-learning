package org.firstinspires.ftc.teamcode.systems.achived;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class LimelightTracking {
    Limelight3A limelight;

    LimelightTracking(HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); /// pipeline for red ball tracking ///
    }

    public void doSomething(){
        LLResult result = limelight.getLatestResult();

        if(result != null){
            if(!result.isValid()){ return; }

        }
    }
}
