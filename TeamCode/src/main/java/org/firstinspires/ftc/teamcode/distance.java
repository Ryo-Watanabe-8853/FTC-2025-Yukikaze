package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Distance Sensor Test", group = "Sensor")
public class distance extends OpMode {
    DistanceSensor FLDSensor;
    DistanceSensor BLDSensor;
    DistanceSensor FRDSensor;


    @Override
    public void init() {
        // Initialize the Distance Sensor hardware
        FLDSensor = hardwareMap.get(DistanceSensor.class, "FLDSensor");
        BLDSensor = hardwareMap.get(DistanceSensor.class, "BLDSensor");
        FRDSensor = hardwareMap.get(DistanceSensor.class,"FRDSensor");

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Get and display the distance measurement
        double distance1 = FLDSensor.getDistance(DistanceUnit.CM);
        double distance2 = BLDSensor.getDistance(DistanceUnit.CM);
        double FRDistance = FRDSensor.getDistance(DistanceUnit.CM);

        telemetry.addData("Distance (cm)", distance1);
        telemetry.addData("Distance (cm)", distance2);
        telemetry.addData("Distance(cm)", FRDistance);
        if (distance1 - distance2 < 2.0 && distance1 - distance2 > -2.0 ){
            telemetry.addData("robot is","parallel to the wall");
        } else {
            telemetry.addData("robot is ","not parallel to the wall");
        }
        telemetry.update();
    }
}
