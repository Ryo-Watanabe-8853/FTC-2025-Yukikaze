package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Touch Sensor Test", group = "Sensor")
public class touchsensortest extends OpMode {
    DcMotor motor;
    TouchSensor upTouchSensor;
    TouchSensor downTouchSensor;
    boolean armIsDown = true;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        upTouchSensor = hardwareMap.get(TouchSensor.class, "touchsensor1");
        downTouchSensor = hardwareMap.get(TouchSensor.class, "touchsensor2");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // 上方向の動作
        if (gamepad1.left_trigger > 0 && armIsDown) {
                motor.setPower(0.8); // 上昇

        }
        // 下方向の動作
        else if (gamepad1.left_trigger > 0 && !armIsDown) {

                motor.setPower(-0.8); // 下降

        } else {
            motor.setPower(0); // トリガーが押されていない場合、モーター停止
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
        telemetry.update();
    }
}
