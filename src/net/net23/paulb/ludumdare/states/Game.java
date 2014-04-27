package net.net23.paulb.ludumdare.states;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import net.net23.paulb.ludumdare.audio.Music;
import net.net23.paulb.ludumdare.collision.AABB;
import net.net23.paulb.ludumdare.entities.AI;
import net.net23.paulb.ludumdare.entities.Entity;
import net.net23.paulb.ludumdare.entities.Key;
import net.net23.paulb.ludumdare.entities.Knight;
import net.net23.paulb.ludumdare.entities.Mob;
import net.net23.paulb.ludumdare.entities.Player;
import net.net23.paulb.ludumdare.maps.Level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
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

	private boolean paused, gameWin;
	private Image pauseScreen;
	private Image gameOverScreen;
	private Image gameWinScreen;
	
	private LinkedList<Entity> entities;
	
	
	private int amountOfKeys;
	private int keysCollected;
	private  LinkedList<Key> keys;
	
	private int spawnDelay = 8000;
	private int timer;
	private int kills;
	private int maxEntities;
	private int amountSpawned;
	
	private UnicodeFont gameOverfont;
	
	private Music healup;
	private Music collect;
	private Music gamewin;
	
	public Game(int state) {
		this.state = state;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		this.pauseScreen = new Image("res/ui/paused.png");
		this.pauseScreen.setFilter(Image.FILTER_NEAREST);
		
		this.gameOverScreen = new Image("res/ui/gameOver.png");
		this.gameOverScreen.setFilter(Image.FILTER_NEAREST);
		
		this.gameWinScreen = new Image("res/ui/gameWin.png");
		this.gameWinScreen.setFilter(Image.FILTER_NEAREST);
		
		this.healup = new Music("res/audio/sfx/heal.wav");
		this.collect = new Music("res/audio/sfx/collect.wav");
		this.gamewin = new Music("res/audio/sfx/gamewin.wav");
		
		gameOverfont = new UnicodeFont("res/fonts/Extrude.ttf", 24, false, false);
		gameOverfont.addAsciiGlyphs();
		gameOverfont.getEffects().add(new ColorEffect());
		gameOverfont.loadGlyphs();
		maxEntities = 1000;
	}
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		this.level = new Level(12368216);

		boolean keyPos[][] = this.level.getKeyPos();
		int i = 0;
		int j = 0;
		
		this.keys = new LinkedList<Key>();
		this.entities = new LinkedList<Entity>();
		this.entities.clear();
		this.keys.clear();
		
		for (i = 0; i < keyPos.length; i++) {
			for (j = 0; j < keyPos.length; j++) {
				if (keyPos[i][j]) {
					amountOfKeys++;
	
					Key tmpkey = new Key(i * level.TILESIZE, j * level.TILESIZE,level.TILESIZE , level.TILESIZE, "res/textures/sprites/keys.png");
					
					this.keys.add(tmpkey);
					this.entities.add(tmpkey);
				}
			}
		} 
		

		
		this.player = new Player( this.level.getPlayerSpawnX(), this.level.getPlayerSpawnY(), 16, 16, 0.1, 0.1, 100, "res/textures/sprites/player.png");
		
		entities.add(this.player);
		
		createEntity(100); createEntity(100); createEntity(100); createEntity(100); createEntity(100); createEntity(100); createEntity(100); createEntity(100);	createEntity(100); createEntity(100);
		
		System.out.print(entities.size() - amountOfKeys);
		
		this.xOffset = 0;
		this.yOffset = 0;
		
		this.paused = false;
		this.kills = 0;
		this.maxEntities = 1000;
		this.amountSpawned = 0;
		this.timer = 0;
		this.keysCollected = 0;
		gameWin = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame gs, Graphics g) throws SlickException {
		
		if (!player.isDead() && !gameWin) {
			g.scale(4, 4);
			sortDepthOrder();
			setOffset();
			
			g.translate(xOffset, yOffset );
			
			this.level.render(g);
			
			for (Entity q : entities) {
				q.render(g);
		
				if (!( q instanceof Key)) {
					g.draw(new Rectangle(q.getX() - 3, q.getY()  - 5, 20, 2));
					g.fillRect(q.getX() - 3, q.getY()  - 5, (float) ((q.getHealth() * 0.20)), 2);
				}
			}
			
			if (paused) {
				g.translate(-xOffset, -yOffset);
				g.drawImage(this.pauseScreen, 0, 0);
			}
			
			g.translate(-xOffset, -yOffset);
			g.scale(0.3F, 0.3F);
			g.drawString("kills: " + kills, 10, 30);
			g.drawString("Keys left:: " + keys.size(), 10, 60);
		} else if (gameWin) {
			g.setFont(gameOverfont);
			g.drawImage(gameWinScreen, 0, 0);
			
			g.drawString("Kills: " + kills + '\n' +"Health Increased: " + player.getMaxHealth(), 250, 360);
		} else {
			g.setFont(gameOverfont);
			g.drawImage(gameOverScreen, 0, 0);
			
			g.drawString("Kills: " + kills + '\n' +"Health Increased: " + player.getMaxHealth(), 250, 360);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) throws SlickException {
		Input input = gc.getInput();

		
		if (!paused && !player.isDead() && !gameWin) {
			player.checkCollision(this.level);
			player.update(input, delta);
			
			for (Entity i : entities) {
				if (i != player && !(i instanceof Key)) {
					((AI) i).checkCollision(this.level, player);
					((AI) i).move(delta);
					
					if (((AI) i).getAttacking()) {
						player.takeDamage(((AI) i).getDamage());
					}
				}
			}
			
			playerBoundsCheck();
			playerAttack();
			
			entityHealthCheck();
			
			for ( Key i : keys) {
				if (AABB.CollisionCheck(player, i)) {
					keysCollected += 1;
					keys.remove(i);
					entities.remove(i);
					
					this.collect.playClip(0, false);
					
					if (keysCollected >= amountOfKeys) {
						gamewin.playClip(-10, false);
						this.gameWin = true;
					}
					
					break;
				}
			}
			
			timer += delta;
			if (timer >= spawnDelay) {
				timer = 0;
				
				int health = 100;
				
				if (amountSpawned % 5 == 0) {
					health += amountSpawned * 2; 
				}
				
				if (amountSpawned % 10 == 0 && spawnDelay > 4000) {
					spawnDelay -= 100;
				}
				createEntity(health);
			}
			
			if (input.isKeyPressed(Input.KEY_P) || !gc.hasFocus()) {
				paused = true;
			}
			
		} else if (gameWin) {
			
		} else if (player.isDead())  {
			
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
		
		//if (xOffset < -56) { xOffset = -56; }
		//if (yOffset < -106) { yOffset = -106; }
		
		if (xOffset < -232){ xOffset = -232;}
		if (yOffset < -234){ yOffset = -234;}

	}
	
	public void entityHealthCheck() {
		for (int i = entities.size() - 1; i >= 0; --i) {
			if (!(entities.get(i) instanceof Key)) {
			    if (((Mob) entities.get(i)).getHealth() < 0) {
			    	if (entities.get(i) != player) {
			    		entities.remove(i);
			    		kills++;
			    		createEntity(100);
				    	int heal = (int) (Math.floor(Math.random() * 100));
				    	
				    	if (heal < 26) {
				    		this.healup.playClip(0,	false);
				    		
				    		this.player.heal();
				    	}
				    	
				    	break;
			    	} else { 
			    		player.setHealth(-10); 
			    	}
			    }
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
				if (!(i instanceof Key)) {
					if (player.getY() > i.getY()) {
						int distance = player.getY() - i.getY();
						
						if (distance <= 16) {
							((Mob) i).takeDamage(player.getDamage());;
						}
					}
				}
			}
		}
		
		if (playerDirection == 9) {
			for (Entity i : entities) {
				if (!(i instanceof Key)) {
					if (player.getX() < i.getX()) {
						int distance = player.getX() - i.getX();
						int distanceY = player.getY() - i.getY();
						
						if (distance <= 16 && distanceY <= 16) {
							((Mob) i).takeDamage(player.getDamage()); 
						}
					}
				}
			}
		}
		
		if (playerDirection == 10) {
			for (Entity i : entities) {
				if (!(i instanceof Key)) {
					if (player.getX() > i.getX()) {
						int distance = player.getX() - i.getX();
						int distanceY = player.getY() - i.getY();
						
						if (distance <= 16 && distanceY <= 16) {
							((Mob) i).takeDamage(player.getDamage()); 
						}
					}
				}
			}
		}
		
		if (playerDirection == 11 ) {
			for (Entity i : entities) {
				if (!(i instanceof Key)) {
					if (player.getY() < i.getY()) {
						int distance = player.getY() - i.getY();
						
						if (distance <= 16) {
							((Mob) i).takeDamage(player.getDamage());;
						}
					}
				}
			}
		}
	}
	
	private void createEntity(int health) {
		if ( (this.entities.size() - amountOfKeys) < maxEntities) {
			
			int x = (int) Math.floor((Math.random() * (level.getMapWidth() / level.TILESIZE)));
			int y = (int) Math.floor((Math.random() * (level.getMapWidth() / level.TILESIZE)));
			
			while (level.getAiSpawn(x, y)) {
				x = (int) Math.floor((Math.random() * (level.getMapWidth() / level.TILESIZE)));
				y = (int) Math.floor((Math.random() * (level.getMapWidth() / level.TILESIZE)));
			}
			
	
			entities.add(new Knight( x * level.TILESIZE, y * level.TILESIZE, 16, 16, 0.1, 0.1, health, "res/textures/sprites/knight.png"));
		}
	}

}
