package net.net23.paulb.ludumdare.entities;

import net.net23.paulb.ludumdare.maps.Level;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Player extends Mob {

	private Image image;
	private SpriteSheet spriteSheet;
	private boolean canMoveDown, canMoveUp, canMoveRight, canMoveLeft;
	
	public Player(int x, int y, int w, int h, double xSpeed, double ySpeed, String path) {
		super(x, y, w, h, xSpeed, ySpeed);
		
		try {
			this.image = new Image(path);
			this.image.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		

		this.canMoveLeft = false;
		this.canMoveUp = false;
		this.canMoveRight = false;
		this.canMoveDown = false;
	}
	
	
	public void render(Graphics g, int camX, int camY) {
		g.drawImage(image, this.getX() + camX, this.getY() + camY);
	}
	
	
	public void update(Input input, int delta) {
		this.move(input, delta);
	}
	
	public void checkCollision(Level level) {
		int tileX = this.getX() / level.TILESIZE;
		int tileY = this.getY() / level.TILESIZE;
		

		if (level.collision(tileX, tileY,     this)) { canMoveUp = false;}    else {canMoveUp = true;} 
		if (level.collision(tileX, tileY + 1, this)) { canMoveDown = false;}  else {canMoveDown = true;} 
		if (level.collision(tileX, tileY, this)) { canMoveLeft = false;}  else {canMoveLeft = true;} 
		if (level.collision(tileX + 1, tileY, this)) { canMoveRight = false;} else {canMoveRight = true;} 
	}
	
	public void move(Input input, int delta) {
		
		if (input.isKeyDown(Input.KEY_W) && canMoveUp) {
			this.setY((int )(this.getY() - this.getYSpeed() * delta));
		}
		if (input.isKeyDown(Input.KEY_A) && canMoveLeft) {
			this.setX((int )(this.getX() - this.getXSpeed() * delta));
		}
		if (input.isKeyDown(Input.KEY_S) && canMoveDown) {
			this.setY((int )(this.getY() + this.getYSpeed() * delta));
		}
		if (input.isKeyDown(Input.KEY_D) && canMoveRight) {
			this.setX((int )(this.getX() + this.getXSpeed() * delta));
		}
	}

}
