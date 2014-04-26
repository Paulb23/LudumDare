package net.net23.paulb.ludumdare.entities;

import net.net23.paulb.ludumdare.maps.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Player extends Mob {

	private Image image;
	private SpriteSheet spriteSheet;
	
	private Image idleUp;
	private Image idleRight;
	private Image idleLeft;
	private Image idleDown;
	
	private Animation walkUp;
	private Animation walkRight;
	private Animation walkLeft;
	private Animation walkDown;
	
	private int directrion;
	
	private boolean dead;
	
	
	private boolean canMoveDown, canMoveUp, canMoveRight, canMoveLeft;
	
	public Player(int x, int y, int w, int h, double xSpeed, double ySpeed, String path) {
		super(x, y, w, h, xSpeed, ySpeed);
		
		try {
			this.image = new Image(path);
			this.image.setFilter(Image.FILTER_NEAREST);
			
			this.spriteSheet = new SpriteSheet(this.image, 16, 16);
			
			Image[] walkupi = {this.spriteSheet.getSprite(1, 0), this.spriteSheet.getSprite(2, 0) };
			Image[] walkrighti = {this.spriteSheet.getSprite(1, 1), this.spriteSheet.getSprite(2, 1) };
			Image[] walkleftti = {this.spriteSheet.getSprite(2, 2), this.spriteSheet.getSprite(1, 2) };
			Image[] walkdowni = {this.spriteSheet.getSprite(1, 3), this.spriteSheet.getSprite(2, 3) };
			
			this.walkUp = new Animation(walkupi, 250);
			this.walkRight = new Animation(walkrighti, 250);
			this.walkLeft = new Animation(walkleftti, 250);
			this.walkDown = new Animation(walkdowni, 250);
			
			this.idleUp = spriteSheet.getSprite(0, 0);
			this.idleRight = spriteSheet.getSprite(0, 1);
			this.idleDown = spriteSheet.getSprite(0, 3);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.directrion = 0;
		this.canMoveLeft = false;
		this.canMoveUp = false;
		this.canMoveRight = false;
		this.canMoveDown = false;
		this.dead = false;
	}
	
	
	public void render(Graphics g, int camX, int camY) {
		switch (directrion) {
			case 0:
				g.drawImage(this.idleUp, this.getX() + camX, this.getY() + camY);
				break;
			case 1:
				g.drawAnimation(this.walkUp, this.getX() + camX, this.getY() + camY);
				break;
			case 2:
				g.drawImage(this.idleRight, this.getX() + camX, this.getY() + camY);
				break;
			case 3:
				g.drawAnimation(this.walkRight, this.getX() + camX, this.getY() + camY);
				break;
			case 4:
				g.drawImage(this.idleLeft, this.getX() + camX, this.getY() + camY);
				break;
			case 5:
				g.drawAnimation(this.walkLeft, this.getX() + camX, this.getY() + camY);
				break;
			case 6:
				g.drawImage(this.idleDown, this.getX() + camX, this.getY() + camY);
				break;
			case 7:
				g.drawAnimation(this.walkDown, this.getX() + camX, this.getY() + camY);
				break;
		}
		
	}
	
	
	public void update(Input input, int delta) {
		this.move(input, delta);
	}
	
	public void checkCollision(Level level) {
		int tileX = this.getX() / level.TILESIZE;
		int tileY = this.getY() / level.TILESIZE;
		

		if (level.collision(tileX, tileY     )) { canMoveUp = false;}    else {canMoveUp = true;} 
		if (level.collision(tileX, tileY + 1 )) { canMoveDown = false;}  else {canMoveDown = true;} 
		if (level.collision(tileX, tileY	 )) { canMoveLeft = false;}  else {canMoveLeft = true;} 
		if (level.collision(tileX + 1, tileY )) { canMoveRight = false;} else {canMoveRight = true;}
	}
	
	public void move(Input input, int delta) {
		
		if (input.isKeyDown(Input.KEY_W) && canMoveUp && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_D )) {
			this.setY((int )(this.getY() - (this.getYSpeed() / 2) * delta));
			directrion = 1;
		} 
		if (input.isKeyDown(Input.KEY_A) && canMoveLeft && !input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_D )) {
			this.setX((int )(this.getX() - (this.getXSpeed() / 2 )* delta));
			directrion = 5;
		}
		if (input.isKeyDown(Input.KEY_S) && canMoveDown && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_D )) {
			this.setY((int )(this.getY() + this.getYSpeed() * delta));
			directrion = 7;
		}
		if (input.isKeyDown(Input.KEY_D) && canMoveRight && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W )) {
			this.setX((int )(this.getX() + this.getXSpeed() * delta));
			directrion = 3;
		}
		
		if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_D )) {
			directrion = 0;
		}
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void Alive() {
		this.dead = false;
	}

}
