package net.net23.paulb.ludumdare.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Player extends Mob {

	private Image image;
	private SpriteSheet spriteSheet;
	private boolean grounded;
	
	private double gravity = 0.7;
	private double maxYSpeed = 0.2;
	
	
	public Player(int x, int y, int w, int h, double xSpeed, double ySpeed, String path) {
		super(x, y, w, h, xSpeed, ySpeed);
		
		try {
			this.image = new Image(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.grounded = false;
	}
	
	
	public void render(Graphics g) {
		g.drawImage(image, this.getX(), this.getY());
	}
	
	
	public void update(Input input, int delta) {
		this.move(input, delta);
	}
	
	
	public void move(Input input, int delta) {
		
		// gravity
	//	if (!grounded) {
	//		this.setY((int )(this.getY() + (this.getYSpeed() + gravity) * delta));
	//	}
	//	
	//	if (this.getYSpeed() > maxYSpeed) {
	//		this.setYSpeed(maxYSpeed);
	//	}
		
		if (input.isKeyDown(Input.KEY_W)) {
			this.setY((int )(this.getY() - this.getYSpeed() * delta));
		}
		if (input.isKeyDown(Input.KEY_A)) {
			this.setX((int )(this.getX() - this.getXSpeed() * delta));
		}
		if (input.isKeyDown(Input.KEY_S)) {
			this.setY((int )(this.getY() + this.getYSpeed() * delta));
		}
		if (input.isKeyDown(Input.KEY_D)) {
			this.setX((int )(this.getX() + this.getXSpeed() * delta));
		}
	}

}
