package com.zevyirmiyahu.entity.projectile;

import com.zevyirmiyahu.graphics.AnimatedSprite;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;

public class Shot2 extends Projectile {

	public static final int FIRE_RATE = 15;
	private boolean xFlip = false;
	private int damage = 70;
	private AnimatedSprite animShot = new AnimatedSprite(SpriteSheet.shot2, 32, 16, 2); 

	public Shot2(double x, double y, double speed, boolean xFlip) {
		super(x, y, speed, xFlip);
	}
	
	public void update() {
		animShot.update();
		move();
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)x, (int)y, animShot.getSprite(), xFlip, false);
	}
}
