package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class arm extends OpMode {
    ColorSensor colorSensor;
    DcMotor motor;

    private boolean isMotorRunning = false;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor3");
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // RGB値を取得
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();

        // Aボタンが押されたらモーターを動かす
        if (gamepad1.a && !isMotorRunning) {
            motor.setPower(-0.3);
            isMotorRunning = true; // モーターが動作中であることを記録
        }
        if (isMotorRunning && (800 < red && red < 1300 && 1350 < green && green < 1800 && 480 < blue && blue < 600)){
            motor.setPower(-0.1);
        }
        // 条件に一致する色を検出したらモーターを停止
        if (isMotorRunning && (430 < red && red < 1000 && 350 < green && green < 600 && 200 < blue && blue < 400)) {
            motor.setPower(0);
            isMotorRunning = false; // モーターの動作状態をリセット
        }
        if(gamepad1.b){
            motor.setPower(0);
        }

        // Telemetryで情報を表示
        telemetry.addData("RED", red);
        telemetry.addData("GREEN", green);
        telemetry.addData("BLUE", blue);
        telemetry.addData("Motor Power", motor.getPower());
        telemetry.update();
    }
}
