package game.Enemies;

import java.awt.image.BufferedImage;

import game.Bomb;
import game.Enemy;
import game.Projectile;

public class SpriteGod extends Enemy {

	private double wanderTheta;
	private double attackStage;

	public SpriteGod(double X, double Y, BufferedImage img) {
		super(23, X, Y, img);
		wanderTheta = 0;
		attackStage = 0;
	}

	// @Overrides Enemy Class
	public void moveBehavior(double xIn, double yIn) {

		double speed = stats.getSpeed();
		//double playerDist = (Math.sqrt((eX - xIn) * (eX - xIn) + (eY - yIn) * (eY - yIn)));
		// keeps a distance of 5 tiles from the player

		double changeTheta = Math.PI;
		wanderTheta += (Math.random() * changeTheta - changeTheta / 2);
		xVel = speed * Math.cos(wanderTheta);
		yVel = speed * Math.sin(wanderTheta);

		eX += xVel;
		eY += yVel;
	}

	public void attackBehavior(double xIn, double yIn) {
		attackStage++;
		// Typically shoot out 5 spread of projectiles
		// System.out.println("Attacking the player...");
		if (attackStage >= 3) // Every three times the spread is fired, throw a bomb
		{
			attackStage = 0;
			// Bomb
			double projSpeed = .1;
			projectiles.add(new Projectile(25, eX, eY, thetaPredict(xIn, yIn, xVel, yVel, projSpeed), projSpeed, stats.getAttack2()));
		}


		for (double cT = Math.PI / 24; cT <= 3 * Math.PI / 24; cT += Math.PI / 12) {
			projectiles.add(new Projectile(26, eX, eY, theta + cT, .1, stats.getAttack()));
			projectiles.add(new Projectile(26, eX, eY, theta - cT, .1, stats.getAttack()));
		}
	}

}
