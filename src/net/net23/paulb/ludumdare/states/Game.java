package net.net23.paulb.ludumdare.states;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import net.net23.paulb.ludumdare.entities.AI;
import net.net23.paulb.ludumdare.entities.Entity;
import net.net23.paulb.ludumdare.entities.Knight;
import net.net23.paulb.ludumdare.entities.Mob;
import net.net23.paulb.ludumdare.entities.Player;
import net.net23.paulb.ludumdare.maps.Level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.SelectTransition;

public class Game extends BasicGameState {

	private int state;
	private Player player;
	private Level level;
	
	private int xOffset;
	private int yOffset;

	private boolean paused;
	private Image pauseScreen;
	
	private LinkedList<Entity> entities;
	
	public Game(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		this.pauseScreen = new Image("res/ui/paused.png");
		this.pauseScreen.setFilter(Image.FILTER_NEAREST);
	}
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		this.level = new Level(12368216);
		
		this.entities = new LinkedList<Entity>();
		
		this.player = new Player( this.level.getPlayerSpawnX(), this.level.getPlayerSpawnY(), 16, 16, 0.1, 0.1, 100, "res/textures/sprites/player.png");
		
		entities.add(this.player);
		//entities.add(new Knight( this.level.getPlayerSpawnX(), this.level.getPlayerSpawnY(), 16, 16, 0.1, 0.1, 100, "res/textures/sprites/knight.png"));
		entities.add(new Knight( this.level.getPlayerSpawnX(), this.level.getPlayerSpawnY() - 50, 16, 16, 0.1, 0.1, 100, "res/textures/sprites/knight.png"));
		
		this.xOffset = 0;
		this.yOffset = 0;
		
		this.paused = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame gs, Graphics g) throws SlickException {
		g.scale(4, 4);
		sortDepthOrder();
		setOffset();
		
		g.translate(xOffset, yOffset );
		
		this.level.render(g);
		
		for (Entity q : entities) {
			q.render(g);
		}

		
		if (paused) {
			g.translate(-xOffset, -yOffset);
			g.drawImage(this.pauseScreen, 0, 0);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if (!paused) {
			player.checkCollision(this.level);
			player.update(input, delta);
			
			for (Entity i : entities) {
				if (i != player) {
					((AI) i).checkCollision(this.level, player);
					((AI) i).move(delta);
					
					if (((AI) i).getAttacking()) {
						player.takeDamage(((AI) i).getDamage());
						System.out.println(player.getHealth());
					}
				}
			}
			
			playerBoundsCheck();
			playerAttack();
			

			
			entityHealthCheck();
			
			if (this.player.isDead()) {
				gs.enterState(0);
			}
			
			if (input.isKeyPressed(Input.KEY_P)) {
				paused = true;
			}
			
		} else {
			if (input.isKeyPressed(Input.KEY_P)) {
				paused = false;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gs.enterState(0, new EmptyTransition(), new SelectTransition());
		}
	}

	@Override
	public int getID() {
		return this.state;
	}
	
	private void sortDepthOrder() {
		Collections.sort(entities, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				
				if (e1.getY() < e2.getY()) { return -1; }
				if (e1.getY() == e2.getY()) { return 0; }
				if (e1.getY() > e2.getY()) { return 1; }

				return 0;
			}
		});
		
	}
	
	public void setOffset() {

		xOffset = -(this.player.getX() - 90);
		yOffset = -(this.player.getY() - 50);
		
		if (xOffset > 0) { xOffset = 0; }
		if (yOffset > 0) { yOffset = 0; }
		
		if (xOffset < -56) { xOffset = -56; }
		if (yOffset < -106) { yOffset = -106; }
	}
	
	public void entityHealthCheck() {
		for (int i = entities.size() - 1; i >= 0; --i) {
		    if (((Mob) entities.get(i)).getHealth() < 0) {
		    	entities.remove(i);
		    	break;
		    }
		}
	}
	
	
	private void playerBoundsCheck() {
		if (this.player.getX() < 0 ) { this.player.setX(0); }
		if (this.player.getY() < 0 ) { this.player.setY(0); }
		
		if (this.player.getX() > this.level.getMapWidth() ) { this.player.setX(this.level.getMapWidth()); }
		if (this.player.getY() > this.level.getMapHeight()) { this.player.setY(this.level.getMapHeight());}
	}
	
	private void playerAttack() {
		int playerDirection = this.player.getDirection();
		
		if (playerDirection == 8) {
			for (Entity i : entities) {
				if (player.getY() > i.getY()) {
					int distance = player.getY() - i.getY();
					
					if (distance <= 10) {
						((Mob) i).takeDamage(player.getDamage());;
					}
				}
			}
		}
		
		if (playerDirection == 9) {
			for (Entity i : entities) {
				if (player.getX() < i.getX()) {
					int distance = player.getX() - i.getX();
					int distanceY = player.getY() - i.getY();
					
					if (distance <= 10 && distanceY <= 10) {
						((Mob) i).takeDamage(player.getDamage()); 
					}
				}
			}
		}
		
		if (playerDirection == 10) {
			for (Entity i : entities) {
				if (player.getX() > i.getX()) {
					int distance = player.getX() - i.getX();
					int distanceY = player.getY() - i.getY();
					
					if (distance <= 10 && distanceY <= 10) {
						((Mob) i).takeDamage(player.getDamage()); 
					}
				}
			}
		}
		
		if (playerDirection == 11 ) {
			for (Entity i : entities) {
				if (player.getY() < i.getY()) {
					int distance = player.getY() - i.getY();
					
					if (distance <= 10) {
						((Mob) i).takeDamage(player.getDamage());;
					}
				}
			}
		}
	}

}
