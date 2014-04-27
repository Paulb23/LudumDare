package net.net23.paulb.ludumdare.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.SelectTransition;

public class Controls extends BasicGameState {
	
	private Image title;
	private int state;
	
	public Controls(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		this.title = new Image("res/ui/Controls.png");
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame gs, Graphics g) throws SlickException {	
		this.title.draw(0,0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gs.enterState(0, new EmptyTransition(), new SelectTransition());
		}
	}

	@Override
	public int getID() {
		return this.state;
	}

}
