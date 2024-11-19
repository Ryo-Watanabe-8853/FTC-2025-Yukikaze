package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
@TeleOp
public class touchsensortest extends OpMode {
    DcMotor motor;
    TouchSensor UpTouchSensor;
    TouchSensor DownTouchSensor;
    boolean armIsDown = false;
    int a = 0;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class,"motor");
        UpTouchSensor = hardwareMap.get(TouchSensor.class,"touchsensor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        if(gamepad1.left_trigger>0 && armIsDown){
            if(!UpTouchSensor.isPressed() && a%2==0) {
                motor.setPower(0.2);
            } else {
                motor.setPower(0);
                armIsDown = false;
                a += 1;
            }
        }

        else if(gamepad1.left_trigger>0 && !armIsDown){
            if(!UpTouchSensor.isPressed() && a%2==1) {
                motor.setPower(-0.2);
            } else{
                motor.setPower(0);
                armIsDown = true;
                a += 1;
            }
        } else {
            motor.setPower(0);
        }
    }
}
