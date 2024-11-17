package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class servoAndMotor extends OpMode {
    DcMotor motor;
    public Servo servo1;
    public Servo servo2;
    private int servo1Stage = 0;
    private int servo2Stage = 0;

    private boolean leftBumperPressed = false;
    private boolean rightBumperPressed = false;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class,"motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo1 = hardwareMap.get(Servo.class,"servo1");
        servo2 = hardwareMap.get(Servo.class,"servo2");
    }

    @Override
    public void loop() {
        if(gamepad1.left_trigger>0){
            motor.setPower(0.6);
        } else if (gamepad1.x) {
            motor.setPower(-0.6);
        }
        float radian1 = (float) (servo1.getPosition() * 300);
        float radian2 = (float) (servo2.getPosition() * 180);
        telemetry.addData("servo1 position", radian1 + "°");
        telemetry.addData("servo2 position", radian2 + "°");



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
                    servo1.setPosition(0.37);
                    break;
            }
            rightBumperPressed = true;
        } else if (!gamepad1.right_bumper) {
            rightBumperPressed = false;
        }
    }
}