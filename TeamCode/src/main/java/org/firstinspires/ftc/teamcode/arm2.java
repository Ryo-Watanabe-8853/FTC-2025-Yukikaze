package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class arm2 extends OpMode {
    DcMotor armString;
    @Override
    public void init() {
        armString = hardwareMap.get(DcMotor.class,"armString");
        armString.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            armString.setPower(0.6);
        }
        if(gamepad1.b){
            armString.setPower(0);
        }
        if(gamepad1.x){
            armString.setPower(-0.6);
        }
    }
}
