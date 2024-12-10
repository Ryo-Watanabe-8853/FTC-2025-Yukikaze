package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class colorTest extends OpMode {
    ColorSensor rightColor;
    ColorSensor leftColor;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;
    @Override
    public void init() {
        rightColor = hardwareMap.get(ColorSensor.class, "rightColor");
        leftColor = hardwareMap.get(ColorSensor.class, "leftColor");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backRight = hardwareMap.get(DcMotor.class,"backRight");
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        int red1 = rightColor.red();
        int green1 = rightColor.green();
        int blue1 = rightColor.blue();
        telemetry.addData("Sensor1 - RED", red1);
        telemetry.addData("Sensor1 - GREEN", green1);
        telemetry.addData("Sensor1 - BLUE", blue1);

        int red2 = leftColor.red();
        int green2 = leftColor.green();
        int blue2 = leftColor.blue();
        telemetry.addData("Sensor2 - RED", red2);
        telemetry.addData("Sensor2 - GREEN", green2);
        telemetry.addData("Sensor2 - BLUE", blue2);

        // カラーセンサー1が赤、カラーセンサー2が床
        if ((320 < red1 && red1 < 400 && 80 < green1 && green1 < 170 && 80 < blue1 && blue1 < 170) &&
                !(500 < red2 && red2 < 600 && 350 < green2 && green2 < 400 && 250 < blue2 && blue2 < 350)) {
            telemetry.addData("状態", "カラーセンサー1が赤、カラーセンサー2が床");
            stopMotors(); // モーターを完全停止
        }
        // カラーセンサー2が赤、カラーセンサー1が床
        else if (!(320 < red1 && red1 < 400 && 80 < green1 && green1 < 170 && 80 < blue1 && blue1 < 170) &&
                (500 < red2 && red2 < 600 && 350 < green2 && green2 < 400 && 250 < blue2 && blue2 < 350)) {
            telemetry.addData("状態", "カラーセンサー2が赤、カラーセンサー1が床");
            stopMotors(); // モーターを完全停止
        }
        // 条件に一致しない場合はゆっくり前進
        else {
            telemetry.addData("状態", "条件外。移動中...");
            setMotorPower(0.2, -0.2, 0.2, -0.2);
        }
        telemetry.update();
    }

    // モーターを停止させるメソッド
    private void stopMotors() {
        setMotorPower(0, 0, 0, 0);
    }

    // モーターのパワーを設定する共通メソッド
    private void setMotorPower(double fr, double br, double fl, double bl) {
        frontRight.setPower(fr);
        backRight.setPower(br);
        frontLeft.setPower(fl);
        backLeft.setPower(bl);
    }

}
//((320 < red1 && red1 < 400 && 80 < green1 && green1 < 170 && 80 < blue1 && blue1 < 170) && !(500 < red2 && red2 < 600 && 350 < green2 && green2 < 400 && 250 < blue2 && blue2 < 350))
