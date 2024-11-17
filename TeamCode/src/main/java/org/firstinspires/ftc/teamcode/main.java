package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class main extends OpMode {
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;
    public Servo servo1;
    public Servo servo2;
    private int servo1Stage = 0;
    private int servo2Stage = 0;

    private boolean leftBumperPressed = false;
    private boolean rightBumperPressed = false;

    @Override

    public void init() {
        frontRight = hardwareMap.get(DcMotor.class,"motor1");
        backRight = hardwareMap.get(DcMotor.class,"motor2");
        frontLeft = hardwareMap.get(DcMotor.class,"motor3");
        backLeft = hardwareMap.get(DcMotor.class,"motor4");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo1 = hardwareMap.get(Servo.class,"servo1");
        servo2 = hardwareMap.get(Servo.class,"servo2");
        colorSensor1 = hardwareMap.get(ColorSensor.class, "colorSensor1");
        telemetry.addData("Motors are","Initialized");
        telemetry.addData("Color Sensors are", "Initialized");

    }

    @Override
    public void loop() {
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float x2 = gamepad1.right_stick_x;

        if (gamepad1.left_stick_y > 0.2){
            frontRight.setPower(-y);
            backRight.setPower(-y);
            frontLeft.setPower(y);
            backLeft.setPower(y);

        } else if (gamepad1.left_stick_x > 0.2) {
            frontRight.setPower(x);
            backRight.setPower(-x);
            frontLeft.setPower(-x);
            backLeft.setPower(x);

        } else if (gamepad1.left_stick_y < -0.2) {
            frontRight.setPower(-y);
            backRight.setPower(-y);
            frontLeft.setPower(y);
            backLeft.setPower(y);

        } else if (gamepad1.left_stick_x < -0.2) {
            frontRight.setPower(x);
            backRight.setPower(-x);
            frontLeft.setPower(-x);
            backLeft.setPower(x);

        } else if (gamepad1.right_stick_x < 0) {
            frontRight.setPower(x2*0.7);
            backRight.setPower(x2*0.7);
            frontLeft.setPower(x2);
            backLeft.setPower(x2);
        } else if (gamepad1.right_stick_x > 0) {
            frontRight.setPower(x2);
            backRight.setPower(x2);
            frontLeft.setPower(x2);
            backLeft.setPower(x2);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);


        float radian1 = (float) (servo1.getPosition() * 300);
        float radian2 = (float) (servo2.getPosition() * 180);
        telemetry.addData("servo1 position", radian1 + "°");
        telemetry.addData("servo2 position", radian2 + "°");

        if (gamepad1.x) {
            servo1.setPosition(0);
            servo2.setPosition(0);
            servo1Stage = 0;
            servo2Stage = 0;
        }

        // arm (servo2) control
        if (gamepad1.left_bumper && !leftBumperPressed) {
            servo2Stage = (servo2Stage + 1) % 3;  // Cycle through 3 stages
            switch (servo2Stage) {
                case 0:
                    servo2.setPosition(0);
                    break;
                case 1:
                    servo2.setPosition(0.417);
                    break;
                case 2:
                    servo2.setPosition(0.5);
                    break;
            }
            leftBumperPressed = true;
        } else if (!gamepad1.left_bumper) {
            leftBumperPressed = false;
        }

        // hand (servo1) control
        if (gamepad1.right_bumper && !rightBumperPressed) {
            servo1Stage = (servo1Stage + 1) % 2;  // Cycle through 2 stages
            switch (servo1Stage) {
                case 0:
                    servo1.setPosition(0);
                    break;
                case 1:
                    servo1.setPosition(0.333);
                    break;
            }
            rightBumperPressed = true;
        } else if (!gamepad1.right_bumper) {
            rightBumperPressed = false;
        }
        telemetry.addData("Color Sensor","results");// カラーセンサーの値を読み取って表示
        int red1 = colorSensor1.red();
        int green1 = colorSensor1.green();
        int blue1 = colorSensor1.blue();
        telemetry.addData("RED",red1);
        telemetry.addData("GREEN",green1);
        telemetry.addData("BLUE",blue1);

        if (100 < red1 && red1 < 250 && 80 < green1 && green1 < 150 && blue1 < 100){
            telemetry.addData("ColorSensor1 is Watching","RED");
        } else if (100 < red1 && red1 < 200 && green1 > 130 && 50 < blue1 && blue1 < 100){
            telemetry.addData("ColorSensor1 is Watching","YELLOW");
        } else if (20 < red1 && red1 < 80 && 80 < green1 && green1 < 200 && 90 < blue1 && blue1 < 500){
            telemetry.addData("Color Sensor1 is Watching","BLUE");
        } else {
            telemetry.addData("Color Sensor is Watching","Something Far");
        }
        telemetry.update();


    }
}
