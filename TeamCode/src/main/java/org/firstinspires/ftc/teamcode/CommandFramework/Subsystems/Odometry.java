package org.firstinspires.ftc.teamcode.CommandFramework.Subsystems;

import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Utils.ExtraUtils;

import static org.firstinspires.ftc.teamcode.Utils.ExtraUtils.drawRobot;

public class Odometry extends Subsystem {

	private BNO055IMU imu;
	public DcMotorEx FrontLeft;
	public DcMotorEx FrontRight;
	private double leftPrev = 0;
	private double rightPrev = 0;
	double trackWidth = 35.70453809697589;
	double velocityX = 0;
	double velocityΘ = 0;

	Vector position = new Vector(3);

	ElapsedTime timer = new ElapsedTime();


	@Override
	public void initAuto(HardwareMap hwMap) {
		imu = hwMap.get(BNO055IMU.class, "imu");
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
		parameters.mode = BNO055IMU.SensorMode.IMU;
		parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
		imu.initialize(parameters);

		FrontLeft = hwMap.get(DcMotorEx.class, "FrontLeft");
		FrontRight = hwMap.get(DcMotorEx.class, "FrontRight");

	}

	@Override
	public void periodic() {
		double left = encoderTicksToInches(FrontLeft.getCurrentPosition());
		double right = encoderTicksToInches(FrontRight.getCurrentPosition());
		double leftVelocity = encoderTicksToInches(FrontLeft.getVelocity());
		double rightVelocity = encoderTicksToInches(FrontRight.getVelocity());
		velocityX = (leftVelocity + rightVelocity) / 2;

		double leftDelta = left - leftPrev;
		double rightDelta = right - rightPrev;
		leftPrev = left;
		rightPrev = right;
		double xDelta = (leftDelta + rightDelta) / 2;
		double yDelta = 0;
		double thetaDelta = (rightDelta - leftDelta) / (trackWidth);
		velocityΘ = thetaDelta / timer.seconds();
		timer.reset();

		double imuAngle = imu.getAngularOrientation().firstAngle;

		Vector nu = new Vector(new double[] {
				xDelta,
				yDelta,
				thetaDelta
		});

		ExtraUtils.rotate(nu, imuAngle);

		try {
			position = position.add(nu);
		} catch (Exception e) {
			e.printStackTrace();
		}

		position.set(imuAngle,2);

		drawRobot(position, Dashboard.packet);

	}

	@Override
	public void shutdown() {

	}

	public static double encoderTicksToInches(double ticks) {
		double WHEEL_RADIUS = 3.77953 / 2;
		double ticksPerRevolution = 28.0 * 13.7;
		return WHEEL_RADIUS * 2 * Math.PI * 1 * ticks / ticksPerRevolution;
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return new Vector(new double[] {
				velocityX,
				0,
				velocityΘ
		});
	}
}
