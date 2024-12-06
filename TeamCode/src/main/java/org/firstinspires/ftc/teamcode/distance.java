package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Distance Sensor Test", group = "Sensor")
public class distance extends OpMode {
    DistanceSensor Dsensor1;
    DistanceSensor Dsensor2;


    @Override
    public void init() {
        // Initialize the Distance Sensor hardware
        Dsensor1 = hardwareMap.get(DistanceSensor.class, "Dsensor1");
        Dsensor2 = hardwareMap.get(DistanceSensor.class, "Dsensor2");
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Get and display the distance measurement
        double distance1 = Dsensor1.getDistance(DistanceUnit.CM);
        double distance2 = Dsensor2.getDistance(DistanceUnit.CM);

        telemetry.addData("Distance (cm)", distance1);
        telemetry.addData("Distance (cm)", distance2);
        if (distance1 - distance2 < 2.0 && distance1 - distance2 > -2.0 ){
            telemetry.addData("robot is","parallel to the wall");
        } else {
            telemetry.addData("robot is ","not parallel to the wall");
        }
        telemetry.update();
    }
}
