package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Motortest extends OpMode {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotor.class,"motor1");
        motor2 = hardwareMap.get(DcMotor.class,"motor2");
        motor3 = hardwareMap.get(DcMotor.class,"motor3");
        motor4 = hardwareMap.get(DcMotor.class,"motor4");

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor4.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
    }
}
