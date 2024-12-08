package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class colorline extends OpMode {
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

    public void loop() {

        int red1 = rightColor.red();
        int green1 = rightColor.green();
        int blue1 = rightColor.blue();
        telemetry.addData("RED",red1);
        telemetry.addData("GREEN",green1);
        telemetry.addData("BLUE",blue1);
        int red2 = leftColor.red();
        int green2 = leftColor.green();
        int blue2 = leftColor.blue();
        telemetry.addData("RED",red2);
        telemetry.addData("GREEN",green2);
        telemetry.addData("BLUE", blue2);
        //アライアンスカラーが青の場合
        //((20 < red1 && red1 < 80 && 80 < green1 && green1 < 200 && 90 < blue1 && blue1 < 500) && (500 < red2) && (red2 < 700) && (1100 < green2) && (green2 < 1250) && (900 < blue2) && (blue2 < 1050))
        if (((350 < red1) && (red1 < 450) && (130 < green1) && (green1 < 170) && (130 < blue1) &&(blue1 < 200)) && ((500 < red2) && (red2 < 650) && (1000 < green2) && (green2 < 1100) && (800 < blue2) && (blue2 < 900))){
            telemetry.addData("玄関開けたらお風呂","カラーセンサー１が赤、カラーセンサー２が床");
            frontRight.setPower(0.2);
            backRight.setPower(-0.2);
            frontLeft.setPower(-0.2);
            backLeft.setPower(0.2);
        }  else if (((400 < red2) && (red2 < 500) && (300 < green2) && (green2 < 400) && (250 < blue2) &&(blue2 < 350)) && ((400 < red1) && (red1 < 500) && (400 < green1) && (green1 < 500) && (250 < blue1) && (blue1 < 350))) {
            telemetry.addData("玄関開けたら佐藤のごはん","カラーセンサー2が赤、カラーセンサー1が床");
            frontRight.setPower(0.2);
            backRight.setPower(-0.2);
            frontLeft.setPower(-0.2);
            backLeft.setPower(0.2);
        } else {
            telemetry.addData("Color Sensor is Watching","Something Far");
            frontRight.setPower(0.2);
            backRight.setPower(-0.2);
            frontLeft.setPower(-0.2);
            backLeft.setPower(0.2);
        }


    }
}
