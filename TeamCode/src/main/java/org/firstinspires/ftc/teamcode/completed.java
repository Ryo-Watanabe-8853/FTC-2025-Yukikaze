package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class completed extends OpMode {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    public Servo servo1;
    public Servo servo2;
    private int servo1Stage = 0;
    private int servo2Stage = 0;

    private boolean leftBumperPressed = false;
    private boolean rightBumperPressed = false;

    @Override

    public void init() {
        motor1 = hardwareMap.get(DcMotor.class,"motor1");
        motor2 = hardwareMap.get(DcMotor.class,"motor2");
        motor3 = hardwareMap.get(DcMotor.class,"motor3");
        motor4 = hardwareMap.get(DcMotor.class,"motor4");
        servo1 = hardwareMap.get(Servo.class,"servo1");
        servo2 = hardwareMap.get(Servo.class,"servo2");

        telemetry.addData("Hardware","Initialized");

    }

    @Override
    public void loop() {
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;

        if (gamepad1.left_stick_y > 0.2){
            motor1.setPower(-y);
            motor2.setPower(-y);
            motor3.setPower(y);
            motor4.setPower(y);

        } else if (gamepad1.left_stick_x > 0.2) {
            motor1.setPower(x);
            motor2.setPower(-x);
            motor3.setPower(-x);
            motor4.setPower(x);

        } else if (gamepad1.left_stick_y < -0.2) {
            motor1.setPower(-y);
            motor2.setPower(-y);
            motor3.setPower(y);
            motor4.setPower(y);

        } else if (gamepad1.left_stick_x < -0.2) {
            motor1.setPower(x);
            motor2.setPower(-x);
            motor3.setPower(-x);
            motor4.setPower(x);

        } else if (gamepad1.right_stick_x < 0) {
            motor1.setPower(0.7);
            motor2.setPower(0.7);
            motor3.setPower(1);
            motor4.setPower(1);
        } else if (gamepad1.right_stick_x > 0) {
            motor1.setPower(-1);
            motor2.setPower(-1);
            motor3.setPower(-0.7);
            motor4.setPower(-0.7);
        }
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        motor4.setPower(0);


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

    }
}
