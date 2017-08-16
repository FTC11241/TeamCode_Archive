package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by rmorgan on 8/11/2017 mod. 8/16/2017 practicing JAVA/Vuforia using
 * youtube https://www.youtube.com/watch?v=2z-o9Ts8XoE
 * GitHub https://www.youtube.com/watch?v=Ldmc757EXaE&feature=youtu.be&t=1m5s
 */

public class Vuforia_Op extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params= new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //FRONT
        params.vuforiaLicenseKey = "Ac4ziN3/////AAAAGTsD0H73x05nq6wcRsez7GYrwsmg+WsZaODHq/Y/nc/AvI0Qbpyj6/QX6QAQe4ZLcNfZOrQ55apfBH5IPV6oPTdjULoRFX/JnTm5EDbQVT9Tpcoa5AuFR0CvBZ4ZRxVrPtxJ5G8L2dUTq5guuIsgxoiX2rQqjx+HIpZTRsKgf8sFhOzFie6R5u9w4RH+t2IAiX1BRvmvflRsfLLA+JnQVgAM/yIPOY20cdTN/l4hOqJme5wd9TCzHvVjvHXRHPTQAzDmO2E4y2gAeLuD9qSxEznk0KSIUqi+p2cV99eqEfmJCETePfokHX3/bhpWUIVWF+j83l4IHcW6pPZiSrrYP80GG/W4eZn7axZ3jD5l6lkT";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia_localizer = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables targets = vuforia_localizer.loadTrackablesFromAsset("FTC_2016-17");
        targets.get(0).setName("wheels");
        targets.get(1).setName("tools");
        targets.get(2).setName("legos");
        targets.get(3).setName("gears");

        waitForStart();

        targets.activate();
    }
}