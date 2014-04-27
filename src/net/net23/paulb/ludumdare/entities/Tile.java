package net.net23.paulb.ludumdare.entities;

import org.newdawn.slick.Graphics;

public class Tile extends Entity {

	private int hardness;
	private boolean collision;
	
	public Tile(int x, int y, int w, int h, int hardness, boolean collision) {
		super(x, y, w, h);
		
		this.hardness = hardness;
		this.collision = collision;
	}

	public boolean isSolid() {
		return this.collision;
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public double getHealth() {
		return hardness;
	}
}
