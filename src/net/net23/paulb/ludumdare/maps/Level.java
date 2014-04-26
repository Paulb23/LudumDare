package net.net23.paulb.ludumdare.maps;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

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
}
