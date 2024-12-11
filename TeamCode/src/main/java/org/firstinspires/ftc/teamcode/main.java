package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;


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

    // AprilTag Variables
    private static final boolean USE_WEBCAM = true;
    private Position cameraPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES, 0, -90, 0, 0);
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    BNO055IMU imu;
    double imuAngle;

    private PIDController distancePID = new PIDController(0.005, 0.0, 0.001); // 距離制御
    private PIDController anglePID = new PIDController(0.02, 0.0, 0.005);      // 角度制御

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

        initAprilTag();
    }

    @Override
    public void loop() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == 13) { // タグIDが13の場合
                telemetry.addData("Target Detected", "AprilTag ID: 13");

                // 目標位置と角度
                double targetDistance = 300; // 目標距離 (mm)
                double targetAngle = Math.toRadians(45); // 目標角度 (ラジアン)

                // 現在の位置と角度を取得
                double currentX = detection.robotPose.getPosition().x; // 現在のX座標 (mm)
                double currentY = detection.robotPose.getPosition().y; // 現在のY座標 (mm)
                double currentAngle = imu.getAngularOrientation().firstAngle; // 現在の角度 (ラジアン)

                // 現在の距離を計算
                double currentDistance = Math.sqrt(currentX * currentX + currentY * currentY);

                // PID制御でエラーを計算
                double forwardPower = distancePID.calculate(targetDistance, currentDistance);
                double rotationPower = anglePID.calculate(targetAngle, currentAngle);

                // モーター出力計算
                double powerFrontLeft = forwardPower + rotationPower;
                double powerFrontRight = forwardPower - rotationPower;
                double powerBackLeft = forwardPower + rotationPower;
                double powerBackRight = forwardPower - rotationPower;

                // モーター出力を適用
                frontLeft.setPower(powerFrontLeft);
                frontRight.setPower(powerFrontRight);
                backLeft.setPower(powerBackLeft);
                backRight.setPower(powerBackRight);

                // テレメトリでデバッグ情報を表示
                telemetry.addData("Distance Error", targetDistance - currentDistance);
                telemetry.addData("Angle Error", Math.toDegrees(targetAngle - currentAngle));
                telemetry.addData("Forward Power", forwardPower);
                telemetry.addData("Rotation Power", rotationPower);
                telemetry.update();

                // 条件を満たしたら停止
                if (Math.abs(targetDistance - currentDistance) < 10 && Math.abs(Math.toDegrees(targetAngle - currentAngle)) < 5) {
                    frontLeft.setPower(0);
                    frontRight.setPower(0);
                    backLeft.setPower(0);
                    backRight.setPower(0);
                    distancePID.reset();
                    anglePID.reset();
                    telemetry.addData("Status", "Target Reached");
                    telemetry.update();
                }
                return;
            }
        }

        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double rotation = gamepad1.right_stick_x;

        imuAngle = imu.getAngularOrientation().firstAngle;

        double tempX = x * Math.cos(imuAngle) - y * Math.sin(imuAngle);
        double tempY = x * Math.sin(imuAngle) + y * Math.cos(imuAngle);

        double maxSpeed = 0.7;

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
            servo2Stage = (servo2Stage + 1) % 2;  // Cycle through 3 stages
            switch (servo2Stage) {
                case 0:
                    servo2.setPosition(0);
                    break;
                case 1:
                    servo2.setPosition(0.7);
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
        telemetryAprilTag();

        // Example: Stream control
        if (gamepad1.dpad_down) {
            visionPortal.stopStreaming();
        } else if (gamepad1.dpad_up) {
            visionPortal.resumeStreaming();
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
        hexMotor.setTargetPosition(0);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(0.7);

        while (hexMotor.isBusy()) {
            telemetry.addData("Current Position", hexMotor.getCurrentPosition());
            telemetry.update();
        }
// モーターが目標位置に到達したら停止
        hexMotor.setPower(0);
        hexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }

    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f (millimeter)",
                        detection.robotPose.getPosition().x,
                        detection.robotPose.getPosition().y,
                        detection.robotPose.getPosition().z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (deg)",
                        detection.robotPose.getOrientation().getPitch(AngleUnit.DEGREES),
                        detection.robotPose.getOrientation().getRoll(AngleUnit.DEGREES),
                        detection.robotPose.getOrientation().getYaw(AngleUnit.DEGREES)));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
            }
        }
    }
}
// PIDコントローラークラス
class PIDController {
    private double kp, ki, kd;
    private double integral, previousError;

    public PIDController(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.integral = 0;
        this.previousError = 0;
    }

    public double calculate(double target, double current) {
        double error = target - current;
        integral += error;
        double derivative = error - previousError;
        previousError = error;
        return (kp * error) + (ki * integral) + (kd * derivative);
    }

    public void reset() {
        integral = 0;
        previousError = 0;
    }
}



