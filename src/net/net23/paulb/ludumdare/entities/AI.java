package net.net23.paulb.ludumdare.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AI extends Mob {

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
	
	private int beforeAttackdir;
	
	private boolean canMoveDown, canMoveUp, canMoveRight, canMoveLeft, attacking;
	
	public AI(int x, int y, int w, int h, double xSpeed, double ySpeed, double health, String path) {
		super(x, y, w, h, xSpeed, ySpeed, health);
		
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
			this.idleLeft = spriteSheet.getSprite(0, 2);
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
		this.attacking = false;
		this.beforeAttackdir = 0;
	} 

	public void render(Graphics g) {
		switch (directrion) {
			case 0:
				g.drawImage(this.idleUp, this.getX(), this.getY());
				break;
			case 1:
				g.drawAnimation(this.walkUp, this.getX() , this.getY() );
				break;
			case 2:
				g.drawImage(this.idleRight, this.getX(), this.getY() );
				break;
			case 3:
				g.drawAnimation(this.walkRight, this.getX() , this.getY() );
				break;
			case 4:
				g.drawImage(this.idleLeft, this.getX(), this.getY() );
				break;
			case 5:
				g.drawAnimation(this.walkLeft, this.getX() , this.getY() );
				break;
			case 6:
				g.drawImage(this.idleDown, this.getX() , this.getY() );
				break;
			case 7:
				g.drawAnimation(this.walkDown, this.getX() , this.getY() );
				break;
		}
	}
}