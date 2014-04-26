package net.net23.paulb.ludumdare.maps;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

	public static int TILESIZE = 8;
	
	private TiledMap map;
	
	public Level(int seed) {
		try {
			this.map = new TiledMap("res/maps/map.tmx");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	public void render(Graphics g) {
		this.map.render(0, 0);
	}


	public int getMapWidth() {
		return this.map.getWidth() * TILESIZE;
	}
	
	public int getMapHeight() {
		return this.map.getHeight() * TILESIZE;
	}
}
