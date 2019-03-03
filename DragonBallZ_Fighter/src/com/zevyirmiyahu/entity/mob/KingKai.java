package com.zevyirmiyahu.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.zevyirmiyahu.graphics.AnimatedSprite;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.level.Level;

public class KingKai extends Mob {
	
	private int timer = 0;
	private boolean cryStart = false;
	
	public boolean speechFinished = false; // used to exit opening scene
	
	private ArrayList<String> displayText = kingKaiSpeech; // make copy
	
	private AnimatedSprite animKingKaiCrying = new AnimatedSprite(SpriteSheet.kingKaiCrying, 32, 32, 2);
	private AnimatedSprite animCurrent = new AnimatedSprite(SpriteSheet.kingKaiStanding, 32, 32, 1);
	
	public KingKai(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		intializeKingKaiSpeech();
	}
	
	public void update() {
		
		if(((int)Level.getPlayer(0).getX() >> 4) > 8) cryStart = true;
		
		if(cryStart) {
			animKingKaiCrying.update();
			animCurrent = animKingKaiCrying;
			timer++; // start timer
		}
		
		if(timer != 0 && timer % 180 == 0) {
			if(!displayText.get(0).equals("")) displayText.remove(0); //avoid indexOutOfBoundsException	
			else timer = 1; // prepares to exit opening scene
		}
		
		if(timer > 7000) timer = 0; // reset
		if(displayText.get(0).equals("") && timer % 121 == 0) { // 2 seconds after speech ends goku needs to jump
			speechFinished = true;
		}
	}
	
	public void render(Graphics g) {
		if(cryStart) {
			if(!displayText.get(0).equals("")) {
				g.setColor(Color.BLACK);
				g.fillRoundRect(43 << 4, 3 << 4, 30 << 4, 4 << 4, 32, 32);
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
			g.drawString(displayText.get(0), 45 << 4, 5 << 4);
		}
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)x, (int)y, animCurrent.getSprite(), false, false);
	}
}
