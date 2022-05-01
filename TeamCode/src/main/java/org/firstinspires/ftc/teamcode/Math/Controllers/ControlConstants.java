package org.firstinspires.ftc.teamcode.Math.Controllers;

import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

import org.firstinspires.ftc.teamcode.Math.AsymmetricProfile.MotionConstraint;
import org.firstinspires.ftc.teamcode.Math.Controllers.Coefficient.SqrtCoefficients;

public class ControlConstants {
	public static PIDCoefficients distanceControl = new PIDCoefficients(0.1,0,0);
	public static SqrtCoefficients angleControl = new SqrtCoefficients(0.45, 0.05,0);
	public static MotionConstraint driveConstraintsNew = new MotionConstraint(90,
			-45,90);
	public static MotionConstraint turnConstraintsNew =
			new MotionConstraint(Math.toRadians(300),
					Math.toRadians(300),
					Math.toRadians(300));

}
