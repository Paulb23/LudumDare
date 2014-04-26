package net.net23.paulb.ludumdare.entities;

public abstract class Mob extends Entity {

	private double xSpeed, ySpeed;
	
	public Mob(int x, int y, int w, int h, double xSpeed, double ySpeed) {
		super(x, y, w, h);
		
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void setXSpeed( double x ) { this.xSpeed = x;}
	public void setYSpeed( double y) {  this.ySpeed = y;}
	
	public double getXSpeed() { return this.xSpeed;}
	public double getYSpeed() { return this.ySpeed;}

}
