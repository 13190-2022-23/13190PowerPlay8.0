package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImpl;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.*;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

@TeleOp(name = "SurendraRobotCentricMecanumTeleOp", group = "MecanumBot")
public class Surendra_Mecanum_Robot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("lf");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("lb");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("rf");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("rb");

        //Declar motors for Arm
        DcMotor liftMotor = hardwareMap.dcMotor.get("arm_motor");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode( DcMotor.RunMode.RUN_TO_POSITION );

        telemetry.addData("Lift motor", "initialized");
        telemetry.update();

        ServoImpl armServo = (ServoImpl) hardwareMap.servo.get("arm_servo");
        ServoImpl clawServo = (ServoImpl) hardwareMap.servo.get("claw_servo");

        telemetry.addData("Arm servos", "initialized");
        telemetry.update();

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //TODO check if the encoder stuff works
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        //TODO Decide on whether or not to use robot centric or field centric
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            //Had to recreate ArmClass here
            int liftRange = 2849;

            if (gamepad1.dpad_up){
                liftMotor.setTargetPosition(liftRange);
            }

            if (gamepad1.dpad_down) {

                liftMotor.setTargetPosition(0);

            }

//            if (gamepad1.left_bumper) {
//
//                turnArm();
//
//            }
//
//            if (gamepad1.right_bumper) {
//
//                runClaw();
//
//            }
        }
    }
}