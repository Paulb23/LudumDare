package net.net23.paulb.ludumdare.states;

import net.net23.paulb.ludumdare.entities.Player;
import net.net23.paulb.ludumdare.maps.Level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {

	private int state;
	private Player player;
	private Level level;
	
	private int xOffset;
	private int yOffset;
	
	private int mapWidth;
	private int mapHeight;
	
	public Game(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		this.level = new Level(12368216);
		
		this.player = new Player( this.level.getPlayerSpawnX(), this.level.getPlayerSpawnY(), 16, 16, 0.1, 0.1, "res/textures/sprites/player.png");
		
		this.mapWidth = level.getMapWidth();
		this.mapHeight = level.getMapHeight();
		
		this.xOffset = 0;
		this.yOffset = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame gs, Graphics g) throws SlickException {
		g.scale(4, 4);
		
		
		xOffset = -(this.player.getX() - 90);
		yOffset = -(this.player.getY() - 50);
		
		if (xOffset > 0) { xOffset = 0; }
		if (yOffset > 0) { yOffset = 0; }
		
		if (-xOffset > mapWidth) { xOffset = -mapWidth; }
		if (-yOffset > mapHeight) { yOffset = -mapHeight; }
		
		g.translate(xOffset, yOffset );
		
		this.level.render(g);
		this.player.render(g, 0, 0);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) throws SlickException {
		Input input = gc.getInput();
		
		player.checkCollision(this.level);
		player.update(input, delta);
		
		if (this.player.getX() < 0 ) { this.player.setX(0); }
		if (this.player.getY() < 0 ) { this.player.setY(0); }
		
		if (this.player.getX() > this.level.getMapWidth() ) { this.player.setX(this.level.getMapWidth()); }
		if (this.player.getY() > this.level.getMapHeight()) { this.player.setY(this.level.getMapHeight());}
	}

	@Override
	public int getID() {
		return this.state;
	}

}
