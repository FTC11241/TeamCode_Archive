package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


/**
 * Created by team 6934 on 5/5/2017.
 */

public class Vuforia_Class_2 {

    // Constructor
    public Vuforia_Class_2() {
    }

    VuforiaTrackables targets;
    VuforiaTrackableDefaultListener listener;
    VuforiaLocalizer vuforia_localizer;
    float Tx,Tz,Ty,Deg;
    float Rx,Ry;
    String target_name;
    private float offset=0;
    private float mm_to_Inch = 25.4f;

    //Vuforia Set Up
    public void Init_Vuforia(){

        // Vuforia LicenseKey Link .. https://developer.vuforia.com/user/register
        // Basic vuforia set up perams....
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "Ac4ziN3/////AAAAGTsD0H73x05nq6wcRsez7GYrwsmg+WsZaODHq/Y/nc/AvI0Qbpyj6/QX6QAQe4ZLcNfZOrQ55apfBH5IPV6oPTdjULoRFX/JnTm5EDbQVT9Tpcoa5AuFR0CvBZ4ZRxVrPtxJ5G8L2dUTq5guuIsgxoiX2rQqjx+HIpZTRsKgf8sFhOzFie6R5u9w4RH+t2IAiX1BRvmvflRsfLLA+JnQVgAM/yIPOY20cdTN/l4hOqJme5wd9TCzHvVjvHXRHPTQAzDmO2E4y2gAeLuD9qSxEznk0KSIUqi+p2cV99eqEfmJCETePfokHX3/bhpWUIVWF+j83l4IHcW6pPZiSrrYP80GG/W4eZn7axZ3jD5l6lkT";
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //FRONT / BACK
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        vuforia_localizer = ClassFactory.createVuforiaLocalizer(params);

        //load tragets and assign them names....
        targets = vuforia_localizer.loadTrackablesFromAsset("FTC_2016-17");
        targets.get(0).setName("wheels");
        targets.get(1).setName("tools");
        targets.get(2).setName("legos");
        targets.get(3).setName("gears");

        // set up targets to there field positions....
        // target_field_position( target # , field X position , field Y position , target Y rotation )
        // Rotation is in a counter clockwise rotation
        // all coords start from bottom left of field if viewed from top down .

        /*
        Field Example of Target locations starting with target 0 to 3
        All values are in inches.
        Ez. X value inches , Y value inches , rotation

        *  *  *  2  *  *  *  3  *  *  *  *  *
        *    118,48,0    118,96,0           *
        *                                   *
        1  0,108,90                         *
        *                                   *
        *                                   *
        *                 C                 *
        0  0,60,90                          *
        *                                   *
        *                                   *
        *                                   *
        *          Robot                    *
        *  *  *  *  *  *  *  *  *  *  *  *  *
        /\
        start counting values from here ( bottom , left )= 0,0,0


        */


        target_field_position( 0 , 0 , 60 , 90 );//wheels
        target_field_position( 1 , 0 , 108 , 90 );//tools
        target_field_position( 2 , 48 , 118 , 0 );//legos
        target_field_position( 3 , 96 , 118 , 0 );//gears

        //Setting Phone position is not needed to work but can be used for setting phones offset to robots center .
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(0,0,0) // values should be in mm .( left/right , up/down , forward/backward ).
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY,AngleUnit.DEGREES, -90, 0, 0));
        ((VuforiaTrackableDefaultListener)targets.get(0).getListener()).setPhoneInformation(phoneLocationOnRobot, params.cameraDirection);
        ((VuforiaTrackableDefaultListener)targets.get(1).getListener()).setPhoneInformation(phoneLocationOnRobot, params.cameraDirection);
        ((VuforiaTrackableDefaultListener)targets.get(2).getListener()).setPhoneInformation(phoneLocationOnRobot, params.cameraDirection);
        ((VuforiaTrackableDefaultListener)targets.get(3).getListener()).setPhoneInformation(phoneLocationOnRobot, params.cameraDirection);

        targets.activate();

    }

    //Vuforia Track Target
    public void Track_Target(){

        for (VuforiaTrackable targ : targets ){

            //Target position to robot
            listener = (VuforiaTrackableDefaultListener) targ.getListener();
            OpenGLMatrix pose = listener.getPose();

            if( pose != null) {

                VectorF Tdata = pose.getTranslation();
                Tx = Tdata.get(0);
                Ty = Tdata.get(1);
                Tz = Tdata.get(2);

                Tx=Tx+offset;

                Orientation orientation = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                Deg = orientation.secondAngle;

                target_name = targ.getName();

            }

            //Robot position on field
            OpenGLMatrix robotLoc = listener.getUpdatedRobotLocation();
            if (robotLoc != null) {

                VectorF Rdata = robotLoc.getTranslation();
                Rx = Rdata.get(0) / mm_to_Inch;
                Ry = Rdata.get(1) / mm_to_Inch;
            }


        }

       // for(int i=0 ; i < targets.size() ; i++){
         //   VuforiaTrackable targ = targets.get(i);
       // }

    }

    // this is a helper function to set targets field positions.
    private void target_field_position( int target_num , float tx_position , float ty_position , int ty_angle ){
        tx_position = (tx_position * mm_to_Inch);
        ty_position = (ty_position * mm_to_Inch);
        OpenGLMatrix TargetLocationOnField = OpenGLMatrix
                .translation(tx_position , ty_position , 0 )
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX,AngleUnit.DEGREES, 90 , ty_angle , 0));
        targets.get(target_num).setLocation(TargetLocationOnField);
    }


}
