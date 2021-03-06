package net.net23.paulb.ludumdare.states;

import net.net23.paulb.ludumdare.audio.Music;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.SelectTransition;

public class Menu extends BasicGameState {
	
	private int state;
	private Image title;
	private Music music;
	
	public Menu(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		this.title = new Image("res/ui/title.png");
		this.music = new Music("res/audio/music/menumusic.wav");
		this.music.playClip(-30, true);
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
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			gc.getInput();
			gs.enterState(1, new EmptyTransition(), new SelectTransition());
		}
		
		if (input.isKeyPressed(Input.KEY_C)) {
			gs.enterState(2, new EmptyTransition(), new SelectTransition());
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			Display.destroy();
			gc.exit();
			System.exit(0);
		}
	}

	@Override
	public int getID() {
		return this.state;
	}

}
