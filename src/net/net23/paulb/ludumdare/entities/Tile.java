package net.net23.paulb.ludumdare.entities;

public class Tile extends Entity {

	private int hardness;
	private boolean collision;
	
	public Tile(int x, int y, int w, int h, int hardness, boolean collision) {
		super(x, y, w, h);
		
		this.hardness = hardness;
		this.collision = collision;
	}

}
