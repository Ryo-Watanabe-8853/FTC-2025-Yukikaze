package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@TeleOp
public class pararelTest extends OpMode {
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;
    DistanceSensor FLDSensor;
    DistanceSensor BLDSensor;
    DistanceSensor FRDSensor;
    DistanceSensor BRDSensor;

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        FLDSensor = hardwareMap.get(DistanceSensor.class, "FLDSensor");
        BLDSensor = hardwareMap.get(DistanceSensor.class, "BLDSensor");
        FRDSensor = hardwareMap.get(DistanceSensor.class,"FRDSensor");
    }

    @Override
    public void loop() {
        telemetry.addData("Front Right Power", frontRight.getPower());
        telemetry.addData("Front Left Power", frontLeft.getPower());
        telemetry.addData("Back Right Power", backRight.getPower());
        telemetry.addData("Back Left Power", backLeft.getPower());
        double FLDistance = FLDSensor.getDistance(DistanceUnit.CM);
        double BLDistance = BLDSensor.getDistance(DistanceUnit.CM);
        double FRDistance = FRDSensor.getDistance(DistanceUnit.CM);
        telemetry.addData("Distance (cm)", FLDistance);
        telemetry.addData("Distance (cm)", BLDistance);
        telemetry.addData("Distance(cm)", FRDistance);
        if (FRDistance <= 50) {
            setMotorPower(0.2,0.2,0.2,0.2);
        }else if (FLDistance > 40 && BLDistance > 40) {
            setMotorPower(-0.2,0.2,-0.2,0.);
        } else if (BLDistance - FLDistance >= 10) {
            setMotorPower(0.2, 0.2, 0, 0);
        } else if (FLDistance - BLDistance >= 10) {

            setMotorPower(-0, -0, -0.2, -0.2);
        } else if (-10 < BLDistance - FLDistance && BLDistance - FLDistance< 10) {

            setMotorPower(0.2, 0.2, -0.2, -0.2);
        }
    }

    // モーターのパワーを設定するヘルパーメソッド
    private void setMotorPower(double fr, double br, double fl, double bl) {
        frontRight.setPower(fr);
        backRight.setPower(br);
        frontLeft.setPower(fl);
        backLeft.setPower(bl);
    }
}
