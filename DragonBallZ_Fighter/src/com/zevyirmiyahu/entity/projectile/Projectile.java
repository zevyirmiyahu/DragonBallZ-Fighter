package com.zevyirmiyahu.entity.projectile;

import com.zevyirmiyahu.entity.Entity;
import com.zevyirmiyahu.graphics.Sprite;

public class Projectile extends Entity {
	
	protected final double xOrigin, yOrigin; // Spawn point of projectile
	
	protected double speed;
	private boolean xFlip = false;
	
	protected Sprite sprite; // projectile sprite
	
	public Projectile(double x, double y, double speed, boolean xFlip) {
		xOrigin = x;
		yOrigin = y;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.xFlip = xFlip;
	}
	
	protected void move() {
		double xa = 0;
		if(!xFlip) xa += speed;
		else xa -= speed;
		x += xa;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
