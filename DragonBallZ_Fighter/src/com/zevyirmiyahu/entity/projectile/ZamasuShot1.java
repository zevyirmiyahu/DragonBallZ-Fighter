package com.zevyirmiyahu.entity.projectile;

import com.zevyirmiyahu.graphics.AnimatedSprite;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;

public class ZamasuShot1 extends Projectile {
	
	public static final int FIRE_RATE = 15;
	
	private static final int DAMAGE = 50;
	
	private AnimatedSprite animShot = new AnimatedSprite(SpriteSheet.zamasuShot1, 16, 16, 2); 

	public ZamasuShot1(double x, double y, double speed, boolean xFlip) {
		super(x, y, speed, xFlip);
	}
	
	public void update() {
		animShot.update();
		move();
	}
	
	public void render(Screen screen) {
		// note: offset x by 16 for better rendering with collision
		screen.renderSprite((int)x + 16, (int)y, animShot.getSprite(), false, false);
	}
}