package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class servoText extends OpMode {
    public Servo servo1;
    private int servo1Stage = 0;
    private boolean rightBumperPressed = false;
    @Override
    public void init() {
        servo1 = hardwareMap.get(Servo.class,"servo1");
    }

    @Override
    public void loop() {
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
    }
}
