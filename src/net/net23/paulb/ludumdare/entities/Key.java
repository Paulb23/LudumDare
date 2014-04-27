package net.net23.paulb.ludumdare.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Key extends Entity {

	private Image image;
	
	public Key(int x, int y, int w, int h, String string) {
		super(x, y, w, h);
		
		try {
			this.image = new Image(string);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, this.getX(), this.getY());
		this.image.setFilter(Image.FILTER_NEAREST);
	}

	@Override
	public double getHealth() {
		return 0;
	}

}
