package net.net23.paulb.ludumdare.collision;

import net.net23.paulb.ludumdare.entities.Entity;

public class AABB {

	public static boolean CollisionCheck(Entity e1, Entity e2) {
		return ((e1.getY() + e1.getH()) <= e2.getY() || e1.getY() >= (e2.getY() + e2.getH()) || (e1.getX() + e1.getW()) <= e2.getX() || e1.getX() >= (e2.getX() + e2.getH()) ) ? false : true;
	}
	
}
