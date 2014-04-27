package net.net23.paulb.ludumdare.entities;

public abstract class Mob extends Entity {

	private double xSpeed, ySpeed, health;
	
	
	public Mob(int x, int y, int w, int h, double xSpeed, double ySpeed, double health) {
		super(x, y, w, h);
		
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.health = health;
	}
	
	public void setXSpeed( double x ) { this.xSpeed = x;}
	public void setYSpeed( double y) {  this.ySpeed = y;}
	public void takeDamage( double damage) { this.health -= damage; }
	public void setHealth( double health) { this.health = health; }
	
	public double getXSpeed() { return this.xSpeed;}
	public double getYSpeed() { return this.ySpeed;}
	public double getHealth() { return this.health;}

}
