package net.net23.paulb.ludumdare.maps;

import net.net23.paulb.ludumdare.entities.Entity;
import net.net23.paulb.ludumdare.entities.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

	public int TILESIZE = 16;
	
	private TiledMap map;
	
	private boolean blocked[][];
	private Tile tiles[][];
	
	private boolean hole[][];
	
	private int playerSpawnX;
	private int playerSpawnY;
	
	public Level(int seed) {
		try {
			this.map = new TiledMap("res/maps/map.tmx");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		blocked = new boolean[this.map.getWidth()][this.map.getHeight()];
		hole = new boolean[this.map.getWidth()][this.map.getHeight()];
		tiles = new Tile[this.map.getWidth()][this.map.getHeight()];
		
		for (int i = 0; i < this.map.getWidth(); i++) {
			for (int j = 0; j < this.map.getHeight(); j++) {
				int tileID = this.map.getTileId(i, j, 1);
				
				
				int tileIDground = this.map.getTileId(i, j, 0);
				
				if (tileIDground > 1) {
					hole[i][j] = false;
				} else {
					hole[i][j] = true;
				}
				
				
				String solid = this.map.getTileProperty(tileID, "Solid", "false");
				
				if (tileID == 2) {
					playerSpawnX = i * TILESIZE;
					playerSpawnY = j * TILESIZE;
				}
				
				if (solid.equals("true")) {
					blocked[i][j] = true;
					tiles[i][j] = new Tile(i * TILESIZE, j * TILESIZE, 16, 16, 1, true);
				} else {
					tiles[i][j] = new Tile(i * TILESIZE, j * TILESIZE, 16, 16, 1, false);
				}
			}
			
		}
	}
	
	public boolean collision(int x, int y, Entity e) {
		return blocked[x][y];
	}
	
	public boolean checkHole(int x, int y) {
		return hole[x][y];
	}
	
	public void render(Graphics g) {
		this.map.render(0, 0);
	}

	public int getPlayerSpawnX() { return playerSpawnX; }
	public int getPlayerSpawnY() { return playerSpawnY; }

	public int getMapWidth() {
		return this.map.getWidth() * TILESIZE;
	}
	
	public int getMapHeight() {
		return this.map.getHeight() * TILESIZE;
	}
}
