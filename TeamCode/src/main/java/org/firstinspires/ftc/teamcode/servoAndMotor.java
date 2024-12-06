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
    DcMotor hexMotor;
    double ticks = 288;
    double Target1;
    double Target2;
    double Target3;
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    private boolean leftBumperPressed = false;
    private boolean rightBumperPressed = false;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class,"motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo1 = hardwareMap.get(Servo.class,"servo1");
        servo2 = hardwareMap.get(Servo.class,"servo2");
        hexMotor = hardwareMap.get(DcMotor.class,"hexMotor");
        hexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hexMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1 = hardwareMap.get(DcMotor.class,"motor1");
        motor2 = hardwareMap.get(DcMotor.class,"motor2");
        motor3 = hardwareMap.get(DcMotor.class,"motor3");
        motor4 = hardwareMap.get(DcMotor.class,"motor4");

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor4.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float x2 = gamepad1.right_stick_x;


        if (gamepad1.left_stick_y > 0.2){
            motor1.setPower(-y);
            motor2.setPower(-y);
            motor3.setPower(y);
            motor4.setPower(y);

        } else if (gamepad1.left_stick_x > 0.2) {
            motor1.setPower(x);
            motor2.setPower(-x);
            motor3.setPower(x);
            motor4.setPower(-x);

        } else if (gamepad1.left_stick_y < -0.2) {
            motor1.setPower(-y);
            motor2.setPower(-y);
            motor3.setPower(y);
            motor4.setPower(y);

        } else if (gamepad1.left_stick_x < -0.2) {
            motor1.setPower(x);
            motor2.setPower(-x);
            motor3.setPower(x);
            motor4.setPower(-x);

        } else if (gamepad1.right_stick_x < 0) {
            motor1.setPower(x2);
            motor2.setPower(x2);
            motor3.setPower(x2*0.7);
            motor4.setPower(x2*0.7);
        } else if (gamepad1.right_stick_x > 0) {
            motor1.setPower(x2*0.7);
            motor2.setPower(x2*0.7);
            motor3.setPower(x2);
            motor4.setPower(x2);
        } else {
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }
        if(gamepad1.left_trigger>0){
            motor.setPower(0.6);
        } else if (gamepad1.x) {
            motor.setPower(-0.6);
        } else if (gamepad1.y){
            motor.setPower(0);
        }
        float radian1 = (float) (servo1.getPosition() * 300);
        float radian2 = (float) (servo2.getPosition() * 180);
        telemetry.addData("servo1 position", radian1 + "°");
        telemetry.addData("servo2 position", radian2 + "°");



        // arm (servo2) control
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
        if(gamepad1.a){
            encoder(4);
        }
        if(gamepad1.b){
            returnToZero();
        }

    }
    public void encoder(double turnage){
        Target1 = ticks/turnage;
        Target2 = ticks/(turnage*2);
        hexMotor.setTargetPosition((int)Target2);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(-0.6);
        hexMotor.setTargetPosition((int)Target1);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(-0.2);
    }
    public void returnToZero() {
        Target3 = ticks/9;
        hexMotor.setTargetPosition((int)Target3);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(0.6);
        hexMotor.setTargetPosition(0);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(0.2);
    }

}
