 package com.zevyirmiyahu.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.zevyirmiyahu.Game;
import com.zevyirmiyahu.Game.STATE;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.input.Mouse;

public class StartMenu {
	
	private  boolean isMouseOverPlayButton = false;
	private static boolean isMouseOverAboutButton = false;
	private Mouse mouse;

	public StartMenu(Mouse mouse) {
		this.mouse = mouse;
	}
	
	public void update() {
		isMouseOverButton();
	}

	private void isMouseOverButton() {

		int x = (int)mouse.getX() >> 4;
		int y = (int) mouse.getY() >> 4;
		
		if(x > 64 && x < 78 && y > 28 && y < 35 ) {
			isMouseOverPlayButton = true;
			if(Mouse.isClicked()) Game.state = STATE.OPENING;
		}
		else isMouseOverPlayButton = false;
		
		if(x > 64 && x < 78 && y > 36 && y < 44) {
			isMouseOverAboutButton = true;
			if(Mouse.isClicked()) Game.state = STATE.ABOUT;
		}
		else isMouseOverAboutButton = false;
	}
	
	public void render(Screen screen) {
		screen.renderSheet(0, 0, SpriteSheet.menuBackground, true);
	}
	
	// DRAW BUTTONS
	public void render(Graphics g) {
	
		// Play button
		if(isMouseOverPlayButton) {
			g.setColor(new Color(0x3D3D3D));
			g.fillRect(1045, 470, 200, 90);
			g.setColor(new Color(0xFFC700));
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 60));
			g.drawString("PLAY", 1070, 540);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(1045, 470, 200, 90);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 60));
			g.drawString("PLAY", 1070, 540);
		}
		
		// About button
		if(isMouseOverAboutButton) {
			g.setColor(new Color(0x3D3D3D));
			g.fillRect(1050, 615, 200, 90);
			g.setColor(new Color(0xFFC700));
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 60));
			g.drawString("About", 1060, 680);
			
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(1050, 615, 200, 90);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 60));
			g.drawString("About", 1060, 680);
		}
	}
	
}
