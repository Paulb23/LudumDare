package net.net23.paulb.ludumdare.entities;

import org.newdawn.slick.Graphics;

public abstract class Entity {

	private int x, y, w, h;
	
	public Entity(int x, int y, int  w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setX(int x) { this.x = x; } 
	public void setY(int y) { this.y = y; } 
	public void setW(int w) { this.w = w; } 
	public void setH(int h) { this.h = h; } 
	public int getX() { return this.x; } 
	public int getY() { return this.y; } 
	public int getW() { return this.w; } 
	public int getH() { return this.h; } 
	
	
	public abstract void render(Graphics g);

	public abstract double getHealth();
}
