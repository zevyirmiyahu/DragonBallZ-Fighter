package com.zevyirmiyahu.level;

import java.awt.Graphics;

import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.input.Keyboard;

/*
 * Opening Scene of the game before level beings
 */
public class OpeningScene {
	
	Keyboard input;
	
	public OpeningScene() {
		Level.getPlayer(0).setPosition(7, 4);
	}
	
	public void update() {
		Level.getPlayer(0).update();
		Level.getKingKai(0).update();
	}
	
	public void render(Graphics g) {		
		Level.getKingKai(0).render(g);
	}
	
	public void render(Screen screen) {
		screen.renderSheet(0,  0, SpriteSheet.kingKaisPlanet, true);
		Level.getKingKai(0).render(screen);
		Level.getPlayer(0).render(screen);
	}
}
