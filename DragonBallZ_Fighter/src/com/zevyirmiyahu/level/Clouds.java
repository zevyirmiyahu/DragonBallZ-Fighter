package com.zevyirmiyahu.level;

import com.zevyirmiyahu.entity.Entity;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.Sprite;

public class Clouds extends Entity {
	
	public Clouds(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
	}
	
	public void update() {
		double xa = 0.0;
		xa += 0.3;
		x -= xa;
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)x, (int)y, Sprite.clouds, false, false);
	}
}
