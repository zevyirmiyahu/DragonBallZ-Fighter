package com.zevyirmiyahu.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.zevyirmiyahu.Game;
import com.zevyirmiyahu.Game.STATE;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.input.Mouse;

public class WinMenu {

	private Mouse mouse;
	private static boolean isMouseOverBackButton = false;

	public WinMenu(Mouse mouse) {
		this.mouse = mouse;
	}
	
	public void update() {
		
		int x = (int)mouse.getX() >> 4;
		int y = (int) mouse.getY() >> 4;
		
		if(x > 65 && x < 84 && y > 39 && y < 48) {
			isMouseOverBackButton = true;
			if(Mouse.isClicked()) Game.state = STATE.MENU;
		}
		else isMouseOverBackButton = false;
	}
	
	public void render(Screen screen) {
		screen.renderSheet(0, 0, SpriteSheet.gameWinPicture, true);
	}
	
	public void render(Graphics g) {
		
		// Replay button
		if(isMouseOverBackButton) {
			g.setColor(Color.GRAY);
			g.fillRect(68 << 4, 40 << 4, 210, 100);
			g.setColor(new Color(0xFFC700));
			g.setFont(new Font("TimesNewRoman", Font.BOLD, 40));
			g.drawString("Menu", 70 << 4, 44 << 4);
		}
		else {
			g.setColor(new Color(0x3D3D3D));
			g.fillRect(68 << 4, 40 << 4, 210, 100);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesNewRoman", Font.BOLD, 40));
			g.drawString("Menu", 70 << 4, 44 << 4);
		}
	}
}
