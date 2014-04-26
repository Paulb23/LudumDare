package net.net23.paulb.ludumdare.game;

import net.net23.paulb.ludumdare.states.Game;
import net.net23.paulb.ludumdare.states.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Ludumdare extends StateBasedGame {
	
	public static int MENUSTATE = 0;
	public static int GAMESTATE = 1;
	
	public static boolean DEBUG = false;
	
	
	private static String GAMETITLE = "My Game!";
	private static int SCREENWIDTH = 800;
	private static int SCREENHEIGHT= 600;
	

	public Ludumdare(String name) {
		super(name);
	}
	
	
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new Menu(MENUSTATE));
		this.addState(new Game(GAMESTATE));
		
		this.enterState(MENUSTATE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Ludumdare(GAMETITLE));
			app.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, false);
			app.setTargetFrameRate(60);
			app.start();
			
			if (DEBUG) {
				app.setShowFPS(false);
			} else {
				app.setShowFPS(true);
			}
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
