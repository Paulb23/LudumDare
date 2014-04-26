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
	
	
	public Game(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		this.player = new Player( 32, 32, 16, 16, 0.5, 0.5, "res/textures/sprites/player.png");
		this.level= new Level(12368216);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame gs, Graphics g) throws SlickException {
		this.level.render(g);
		this.player.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) throws SlickException {
		Input input = gc.getInput();
		
		player.update(input, delta);
	}

	@Override
	public int getID() {
		return this.state;
	}

}
