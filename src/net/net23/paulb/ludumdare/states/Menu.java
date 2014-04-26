package net.net23.paulb.ludumdare.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
	
	private int state;
	
	public Menu(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame gs, Graphics g) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) throws SlickException {
	}

	@Override
	public int getID() {
		return this.state;
	}

}
