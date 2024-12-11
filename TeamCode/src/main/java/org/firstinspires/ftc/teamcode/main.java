package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;


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
    DcMotor armString;
    TouchSensor upTouchSensor;
    TouchSensor downTouchSensor;
    boolean armIsDown = true;
    DcMotor hexMotor;
    double ticks = 288;
    double Target;

    private boolean leftBumperPressed = false;
    private boolean rightBumperPressed = false;

    BNO055IMU imu;
    double imuAngle;

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
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        servo1 = hardwareMap.get(Servo.class,"servo1");
        servo2 = hardwareMap.get(Servo.class,"servo2");

        armString = hardwareMap.get(DcMotor.class, "armString");
        armString.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        upTouchSensor = hardwareMap.get(TouchSensor.class, "upTouchSensor");
        downTouchSensor = hardwareMap.get(TouchSensor.class, "downTouchSensor");

        hexMotor = hardwareMap.get(DcMotor.class, "hexMotor");
        hexMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hexMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
        imuParameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(imuParameters);

    }

    @Override
    public void loop() {
        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double rotation = gamepad1.right_stick_x;

        double maxSpeed = 0.7;

        imuAngle = imu.getAngularOrientation().firstAngle;

        double tempX = x * Math.cos(imuAngle) - y * Math.sin(imuAngle);
        double tempY = x * Math.sin(imuAngle) + y * Math.cos(imuAngle);

        double powerFrontLeft = tempY + tempX + rotation;
        double powerFrontRight = tempY - tempX - rotation;
        double powerBackLeft = tempY - tempX + rotation;
        double powerBackRight = tempY + tempX - rotation;

        double maxPower = Math.max(Math.abs(powerFrontLeft),
                            Math.max(Math.abs(powerFrontRight),
                            Math.max(Math.abs(powerBackLeft), Math.abs(powerBackRight))));

        if (maxPower > maxSpeed) {
            powerFrontLeft *= maxSpeed / maxPower;
            powerFrontRight *= maxSpeed / maxPower;
            powerBackLeft *= maxSpeed / maxPower;
            powerBackRight *= maxSpeed / maxPower;
        }

        frontLeft.setPower(powerFrontLeft);
        frontRight.setPower(powerFrontRight);
        backLeft.setPower(powerBackLeft);
        backRight.setPower(powerBackRight);


        telemetry.addData("Front Right Power", frontRight.getPower());
        telemetry.addData("Front Left Power", frontLeft.getPower());
        telemetry.addData("Back Right Power", backRight.getPower());
        telemetry.addData("Back Left Power", backLeft.getPower());

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

        if (gamepad1.left_bumper && !leftBumperPressed) {
            servo2Stage = (servo2Stage + 1) % 3;  // Cycle through 3 stages
            switch (servo2Stage) {
                case 0:
                    servo2.setPosition(0);
                    break;
                case 1:
                    servo2.setPosition(0.6);
                    break;
                case 2:
                    servo2.setPosition(0.9);
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
        if (gamepad1.left_trigger > 0 && armIsDown) {
            armString.setPower(1); // 上昇

        }
        // 下方向の動作
        else if (gamepad1.left_trigger > 0 && !armIsDown) {

            armString.setPower(-1); // 下降

        } else {
            armString.setPower(0); // トリガーが押されていない場合、モーター停止
        }
        if (upTouchSensor.isPressed() && !downTouchSensor.isPressed()){
            armIsDown = false;
        } else if (downTouchSensor.isPressed() && !upTouchSensor.isPressed()){
            armIsDown = true;
        }


        // テレメトリでデバッグ情報を表示
        telemetry.addData("Arm Position", armIsDown ? "Down" : "Up");
        telemetry.addData("Up Sensor Pressed", upTouchSensor.isPressed());
        telemetry.addData("Down Sensor Pressed", downTouchSensor.isPressed());
        if(gamepad1.a){
            encoder(6.6);
        }
        if(gamepad1.b){
            returnToZero();
        }

    }
    public void encoder(double turnage){
        Target = ticks/turnage;
        hexMotor.setTargetPosition((int) Target);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(-0.7);
        while (hexMotor.isBusy()) {
            telemetry.addData("Current Position", hexMotor.getCurrentPosition());
            telemetry.update();
        }
// モーターが目標位置に到達したら停止
        hexMotor.setPower(0);
        hexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void returnToZero() {
        // エンコーダーのリセット（必要に応じて）
        hexMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hexMotor.setTargetPosition(0);

        // RUN_TO_POSITION モードに設定
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(0.7);

        // モーターが目標位置に到達するまで待機
        while (hexMotor.isBusy()) {
            telemetry.addData("Current Position", hexMotor.getCurrentPosition());
            telemetry.update();
        }

        // モーターを停止して保持力を残す
        hexMotor.setPower(0.2); // 必要であれば少しのトルクを残す (例: hexMotor.setPower(0.1);)
        // モードを変更しない
    }



}


