package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class climb extends OpMode {
    DcMotor climbMotor;
    @Override
    public void init() {
        climbMotor = hardwareMap.get(DcMotor.class,"climbMotor");
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            climbMotor.setPower(1);
        } else if (gamepad1.b){
            climbMotor.setPower(-1);
        } else {
            climbMotor.setPower(0);
        }

    }
}
