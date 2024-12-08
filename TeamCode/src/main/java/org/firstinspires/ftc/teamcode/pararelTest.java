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

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "motor1");
        backRight = hardwareMap.get(DcMotor.class, "motor2");
        frontLeft = hardwareMap.get(DcMotor.class, "motor3");
        backLeft = hardwareMap.get(DcMotor.class, "motor4");

        FLDSensor = hardwareMap.get(DistanceSensor.class, "FLDSensor");
        BLDSensor = hardwareMap.get(DistanceSensor.class, "BLDSensor");
    }

    @Override
    public void loop() {
        double FLDistance = FLDSensor.getDistance(DistanceUnit.CM);
        double BLDistance = BLDSensor.getDistance(DistanceUnit.CM);

        // 適切なスレッショルドを設定
        if (FLDistance - BLDistance > 5) {
            // Forward
            setMotorPower(0.2, 0.2, 0.2, 0.2);
        } else if (BLDistance - FLDistance > 5) {
            // Backward
            setMotorPower(-0.2, -0.2, 0.2, 0.2);
        } else {
            // Rotate
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
