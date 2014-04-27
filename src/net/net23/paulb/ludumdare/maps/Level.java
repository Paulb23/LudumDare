package net.net23.paulb.ludumdare.maps;

import net.net23.paulb.ludumdare.entities.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

	public int TILESIZE = 16;
	
	private TiledMap map;
	
	private boolean blocked[][];
	private Tile tiles[][];
	
	private boolean aiSpawn[][];
	
	private int playerSpawnX;
	private int playerSpawnY;
	
	public Level(int seed) {
		try {
			this.map = new TiledMap("res/maps/map.tmx");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		blocked = new boolean[this.map.getWidth()][this.map.getHeight()];
		tiles = new Tile[this.map.getWidth()][this.map.getHeight()];
		aiSpawn = new boolean[this.map.getWidth()][this.map.getHeight()];
		
		for (int i = 0; i < this.map.getWidth(); i++) {
			for (int j = 0; j < this.map.getHeight(); j++) {
				int tileID = this.map.getTileId(i, j, 1);				
				
				String solid = this.map.getTileProperty(tileID, "Solid", "false");
				
				if (tileID == 2) {
					playerSpawnX = i * TILESIZE;
					playerSpawnY = j * TILESIZE;
				}
				
				if (tileID == 13) {
					aiSpawn[i][j] = true;
				} else {
					aiSpawn[i][j] = false;
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
	
	public boolean collision(int x, int y) {
		return blocked[x][y];
	}
	
	public void render(Graphics g) {
		this.map.render(0, 0);
	}
	
	public boolean getAiSpawn(int x, int y) {return this.aiSpawn[x][y];}

	public int getPlayerSpawnX() { return playerSpawnX; }
	public int getPlayerSpawnY() { return playerSpawnY; }

	public int getMapWidth() {
		return this.map.getWidth() * TILESIZE;
	}
	
	public int getMapHeight() {
		return this.map.getHeight() * TILESIZE;
	}
}
