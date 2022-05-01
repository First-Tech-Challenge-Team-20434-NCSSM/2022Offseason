package org.firstinspires.ftc.teamcode.CommandFramework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Robot;

public abstract class BaseTeleop extends LinearOpMode {

    protected Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, Robot.OpMode.Auto, gamepad1, gamepad2);
        waitForStart();

        robot.getScheduler().forceCommand(setupTeleop(robot.getScheduler()));

        while(opModeIsActive())
            robot.update();
    }

    public abstract Command setupTeleop(CommandScheduler scheduler);
}
