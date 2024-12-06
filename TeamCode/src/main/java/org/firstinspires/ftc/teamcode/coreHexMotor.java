package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class coreHexMotor extends OpMode {
    DcMotor hexMotor;
    double ticks = 288;
    double Target1;
    double Target2;
    double Target3;

    @Override
    public void init() {
        hexMotor = hardwareMap.get(DcMotor.class,"hexMotor");
        hexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hexMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    @Override
    public void loop() {
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
        Target3 = ticks/8;
        hexMotor.setTargetPosition((int)Target3);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(0.6);
        hexMotor.setTargetPosition(0);
        hexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hexMotor.setPower(0.2);
    }
}
