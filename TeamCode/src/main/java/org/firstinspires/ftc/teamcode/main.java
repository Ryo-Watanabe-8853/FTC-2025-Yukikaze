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
    public Servo servo1;
    public Servo servo2;
    private int servo1Stage = 0;
    private int servo2Stage = 0;

    private boolean leftBumperPressed = false;
    private boolean rightBumperPressed = false;

    @Override

    public void init() {
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backRight = hardwareMap.get(DcMotor.class,"backRight");
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo1 = hardwareMap.get(Servo.class,"servo1");
        servo2 = hardwareMap.get(Servo.class,"servo2");

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

        servo2.setPosition((double)8/9);

        // hand (servo1) control
        if (gamepad1.right_bumper && !rightBumperPressed) {
            servo1Stage = (servo1Stage + 1) % 2;  // Cycle through 2 stages
            switch (servo1Stage) {
                case 0:
                    servo1.setPosition(0);
                    break;
                case 1:
                    servo1.setPosition(0.37);
                    break;
            }
            rightBumperPressed = true;
        } else if (!gamepad1.right_bumper) {
            rightBumperPressed = false;
        }
    }
}
