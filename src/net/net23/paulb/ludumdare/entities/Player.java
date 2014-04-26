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
	
	private Animation attackUp;
	private Animation attackRight;
	private Animation attackLeft;
	private Animation attackDown;
	
	private int directrion;
	
	private boolean dead;
	
	private int beforeAttackdir;
	
	private int equipedWeapon;
	
	private boolean canMoveDown, canMoveUp, canMoveRight, canMoveLeft, attacking;
	
	public Player(int x, int y, int w, int h, double xSpeed, double ySpeed, double health, String path) {
		super(x, y, w, h, xSpeed, ySpeed, health);
		
		try {
			this.image = new Image(path);
			this.image.setFilter(Image.FILTER_NEAREST);
			
			this.spriteSheet = new SpriteSheet(this.image, 16, 16);
			
			Image[] walkupi = {this.spriteSheet.getSprite(1, 0), this.spriteSheet.getSprite(2, 0) };
			Image[] walkrighti = {this.spriteSheet.getSprite(1, 1), this.spriteSheet.getSprite(2, 1) };
			Image[] walkleftti = {this.spriteSheet.getSprite(2, 2), this.spriteSheet.getSprite(1, 2) };
			Image[] walkdowni = {this.spriteSheet.getSprite(1, 3), this.spriteSheet.getSprite(2, 3) };
			Image[] attackupi = {this.spriteSheet.getSprite(0, 4), this.spriteSheet.getSprite(1, 4), this.spriteSheet.getSprite(1, 4), this.spriteSheet.getSprite(1, 4) };
			Image[] attackrighti = {this.spriteSheet.getSprite(0, 5), this.spriteSheet.getSprite(1, 5), this.spriteSheet.getSprite(1, 5), this.spriteSheet.getSprite(1, 5) };
			Image[] attacklefti = {this.spriteSheet.getSprite(0, 6), this.spriteSheet.getSprite(1, 6), this.spriteSheet.getSprite(1, 6), this.spriteSheet.getSprite(1, 6) };
			Image[] attackdowni = {this.spriteSheet.getSprite(0, 7), this.spriteSheet.getSprite(1, 7), this.spriteSheet.getSprite(1, 7), this.spriteSheet.getSprite(1, 7) };
			
			this.walkUp = new Animation(walkupi, 250);
			this.walkRight = new Animation(walkrighti, 250);
			this.walkLeft = new Animation(walkleftti, 250);
			this.walkDown = new Animation(walkdowni, 250);
			
			this.attackUp = new Animation(attackupi, 250); 
			this.attackRight = new Animation(attackrighti, 250); 
			this.attackLeft = new Animation(attacklefti, 250); 
			this.attackDown = new Animation(attackdowni, 250); 
			
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
		this.equipedWeapon = 0;
	}
	
	
	public void render(Graphics g) {
		switch (directrion) {
			case 0:
				g.drawImage(this.idleUp, this.getX(), this.getY());
				break;
			case 1:
				g.drawAnimation(this.walkUp, this.getX(), this.getY());
				break;
			case 2:
				g.drawImage(this.idleRight, this.getX(), this.getY());
				break;
			case 3:
				g.drawAnimation(this.walkRight, this.getX(), this.getY());
				break;
			case 4:
				g.drawImage(this.idleLeft, this.getX(), this.getY());
				break;
			case 5:
				g.drawAnimation(this.walkLeft, this.getX(), this.getY());
				break;
			case 6:
				g.drawImage(this.idleDown, this.getX() , this.getY());
				break;
			case 7:
				g.drawAnimation(this.walkDown, this.getX(), this.getY());
				break;
			case 8:
				g.drawAnimation(this.attackUp, this.getX(), this.getY());
				break;
			case 9:
				g.drawAnimation(this.attackRight, this.getX(), this.getY());
				break;
			case 10:
				g.drawAnimation(this.attackLeft, this.getX(), this.getY());
				break;
			case 11:
				g.drawAnimation(this.attackDown, this.getX(), this.getY() );
				break;
		}
		
		if (attackUp.getFrame() == 3 || attackRight.getFrame() == 3  || attackLeft.getFrame() == 3  || attackDown.getFrame() == 3) {
			attacking = false;
			directrion = beforeAttackdir;
			
			attackUp.restart();	
			attackRight.restart();	
			attackLeft.restart();	
			attackDown.restart();	
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
		
		if (input.isKeyPressed(Input.KEY_1)) {
			equipedWeapon = 0;
		} else if (input.isKeyPressed(Input.KEY_2)) {
			equipedWeapon = 1;
		} else if (input.isKeyPressed(Input.KEY_3)) {
			equipedWeapon = 2;
		}
		
		if (input.isKeyPressed(Input.KEY_UP) && !attacking) { 
			beforeAttackdir = 0;
			attacking = true;
			
			directrion = 8;
		
		} else if (input.isKeyPressed(Input.KEY_RIGHT) && !attacking) {
			beforeAttackdir = 2;
			attacking = true;
			
			directrion = 9;
			
		} else if (input.isKeyPressed(Input.KEY_LEFT) && !attacking) {
			beforeAttackdir = 4;
			attacking = true;
			
			directrion = 10;
			
		} else if (input.isKeyPressed(Input.KEY_DOWN) && !attacking) {
			beforeAttackdir = 6;
			attacking = true;
			
			directrion = 11;

		} else if (!attacking) {
			if (input.isKeyDown(Input.KEY_W) && canMoveUp && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_D ) && !attacking) {
				this.setY((int )(this.getY() - (this.getYSpeed() / 2) * delta));
				directrion = 1;
			} 
			if (input.isKeyDown(Input.KEY_A) && canMoveLeft && !input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_D ) && !attacking) {
				this.setX((int )(this.getX() - (this.getXSpeed() / 2 )* delta));
				directrion = 5;
			}
			if (input.isKeyDown(Input.KEY_S) && canMoveDown && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_D ) && !attacking) {
				this.setY((int )(this.getY() + this.getYSpeed() * delta));
				directrion = 7;
			}
			if (input.isKeyDown(Input.KEY_D) && canMoveRight && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W ) && !attacking) {
				this.setX((int )(this.getX() + this.getXSpeed() * delta));
				directrion = 3;
			}
			
			if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_D ) && !attacking) {
				switch (directrion) {
					case 1:
						directrion = 0;
						break;
					case 3:
						directrion = 2;
						break;
					case 5:
						directrion = 4;
						break;
					case 7:
						directrion = 6;
						break;
				}
			}
		}
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void Alive() {
		this.dead = false;
	}
	
	public int getDirection() {
		return this.directrion;
	}


	public int getDamage() {
		return 1;
	}

}
