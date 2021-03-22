package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.servo_block;
import org.firstinspires.ftc.teamcode.hardware.servo_wobble;

@TeleOp
public class pistonMotorTest extends LinearOpMode {

    protected Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();

    int pozFinala = 0;

    double outtakePower = 0;
    double pistonPower = 0;

    Boolean cheie = Boolean.FALSE;
    Boolean cheiePiston = Boolean.FALSE;
    Boolean isPistonOk = Boolean.FALSE;
    Boolean isRunning = Boolean.FALSE;
    Boolean cheieOutake = Boolean.FALSE;
    Boolean isMax = Boolean.FALSE;

    servo_block servoBlock = new servo_block();


    @Override
    public void runOpMode()
    {
        robot.initHardware(hardwareMap);
        servoBlock.initBlock(hardwareMap);
        servoBlock.open();
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Outake", outtakePower);
        telemetry.addData("Pozitie finala piston", pozFinala);
        telemetry.update();

        robot.wobbleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.pistonMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        runtime.reset();

        while(opModeIsActive())
        {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Outtake", outtakePower);
            telemetry.addData("Pozitie finala piston", pozFinala);
            telemetry.update();

            reglajPozitie();

            outtakeController();
            pistonController();

            robot.outtakeMotor.setPower(outtakePower);
            robot.pistonMotor.setPower(pistonPower);
        }
    }

    void pistonController()
    {
        if(gamepad2.left_bumper && !cheiePiston)
        {
            isPistonOk = !isPistonOk;
            cheiePiston = !cheiePiston;
        }
        if(!gamepad2.left_bumper)
            cheiePiston = false;
        if(isPistonOk){
            pistonPower = 0.2;
            robot.pistonMotor.setTargetPosition(pozFinala);
            robot.pistonMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else
        {
            robot.pistonMotor.setTargetPosition(0);
            robot.pistonMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            pistonPower = 0.2;
        }
    }


    void outtakeController()
    {
        if(gamepad2.y && !cheieOutake)
        {
            isMax = !isMax;
            cheieOutake = !cheieOutake;
        }
        if(!gamepad2.y)
            cheieOutake = false;
        if(isMax)
        {
            outtakePower = 1.0;
        }
        else
        {
            if(gamepad2.left_trigger!=0)
            {
                outtakePower = gamepad2.left_trigger;
            }
            else
                outtakePower = 0.0;
        }
    }

    void reglajPozitie()
    {
        if(gamepad2.dpad_up)
        {
            pozFinala+=10;
        }
        else if(gamepad2.dpad_down)
        {
            pozFinala-=10;
        }
    }
}