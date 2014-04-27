package net.net23.paulb.ludumdare.entities;

import net.net23.paulb.ludumdare.maps.Level;

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
	
	private Animation attackUp;
	private Animation attackRight;
	private Animation attackLeft;
	private Animation attackDown;
	
	private int directrion;
	
	private boolean dead, walkedleft, walkedright;
	
	private int beforeAttackdir, playerX, playerY;
	
	private boolean canMoveDown, canMoveUp, canMoveRight, canMoveLeft, attacking;
	private int distanceY;
	
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
		walkedleft = true;
		walkedright = false;
	} 
	
	public void checkCollision(Level level, Player player) {
		int tileX = this.getX() / level.TILESIZE;
		int tileY = this.getY() / level.TILESIZE;
		
		playerY = player.getY();
		playerX = player.getX();
		
		if (level.collision(tileX, tileY     ) ) { canMoveUp = false;}    else {canMoveUp = true;} 
		if (level.collision(tileX, tileY + 1 ) ) { canMoveDown = false;}  else {canMoveDown = true;} 
		if (level.collision(tileX, tileY	 ) ) { canMoveLeft = false;  walkedright = false; walkedleft = true;}  else {canMoveLeft = true;} 
		if (level.collision(tileX + 1, tileY ) ) { canMoveRight = false; walkedright = true; walkedleft = false; } else {canMoveRight = true;} 
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
	
	public void move(int delta) {
		int tileX = this.getX() / 16;
		int tileY = this.getY() / 16;
		
		int playerTileX = this.playerX / 16;
		int playerTileY = this.playerY / 16;
		
		int tileDistanceX = tileX - playerTileX;
		int tileDistanceY = tileY - playerTileY;
		
		int distanceX = this.playerX - this.getX();
		int distanceY = this.playerY - this.getY() ;
		
		System.out.println(tileDistanceX + " " + tileDistanceY);
		
		if (!canAttack(distanceX, distanceY)) {
			if (distanceX > -160 && distanceX < 160) {
				if (distanceY > -160 || distanceY < 160) {
					   float dx = this.playerX - getX() ;
					   float dy = this.playerY - getY() ;
					   
					   //normalize it
					   int len = (int)Math.sqrt(dx * dx + dy * dy);
					   if (len!=0) {
						   dx /= len;
						   dy /= len;
					    }
	
					      //scale the vector based on our movement speed
					   float speed = 1f;
					   dx *= speed;
					   
					   dy *= speed;
					    
					   //move the entity with our new direction vector
					   setX((int)(getX() + dx));
					   setY((int)(getY() + dy));
				}
				
			}
		}
		
		
		if (distanceX <= 15 && distanceY <= 15 && distanceX > -15  && distanceY > -15) {
			attacking = true;
			
			if (playerY < this.getY()) {
				directrion = 8;
			} else {
				directrion = 11;
			}
			
		} else {
			attacking = false;
		}
	}
	
	public boolean getAttacking() {
		return this.attacking;
	}
	
	public float getDamage() {
		return 0.1f;
	}
	
	private boolean canAttack(int distanceX, int distanceY) {
		System.out.println("X: " + distanceX + " Y: " + distanceY);
		
		return (distanceX <= 15 && distanceY <= 15 && distanceX > -15  && distanceY > -15) ? true : false;

	}
}
