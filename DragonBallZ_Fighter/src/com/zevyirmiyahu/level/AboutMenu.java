package com.zevyirmiyahu.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.zevyirmiyahu.Game;
import com.zevyirmiyahu.Game.STATE;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.input.Mouse;

public class AboutMenu {
	
	private Mouse mouse;
	private static boolean isMouseOverBackButton = false;

	public AboutMenu(Mouse mouse) {
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
		// renders black instead of void
		for(int i = 0; i < screen.pixels.length; i++) {
			screen.pixels[i] = 0x000000; // set to black
		}
		screen.renderSheet(20 << 4, 1 << 4, SpriteSheet.aboutMenuPicture, true);
	}
	
	public void render(Graphics g) {
	
		String aboutTitle ="ABOUT:";
		
		String about1 = "This project is a Dragon Ball Z fighting game programmed in Java. "
							+ "The sprites were ";
		String about2 = "all drawn with Aseprite and the backgrounds were drawn with the "
							+ "Wacom Intuos ";
		String about3 = "drawing pad using Clip Art Studios. The game was programmed from stratch without ";
		
		String about4 = " the aid of a game engine.";
		
		String controlTitle = "CONTROLS:";
		String control1 = "LEFT: Left Arrow";
		String control2 = "RIGHT: Right Arrow";
		String control3 = "JUMP: Spacebar";
		String control4 = "PUNCH: Z";
		String control5 = "ENERGY SHOT: X";
		String control6 = "KAMEHAMEHA: C"; 
		String control7 = "SUPER SAIYIN: S";
		
		String contactTitle = "CONTACT:";
		String contact1 = "Email: zy@zevyirmiyahu.com";
		String contact2 = "Personal Site: www.zevyirmiyahu.com";
		String contact3 = "GitHub: https://zevyirmiyahu.github.io/";
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
		g.drawString(aboutTitle, 3 << 4, 4 << 4);
		
		g.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
		g.drawString(about1, 4 << 4, 6 << 4);
		g.drawString(about2, 4 << 4, 8 << 4);
		g.drawString(about3, 4 << 4, 10 << 4);
		g.drawString(about4, 4 << 4, 12 << 4);
		
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
		g.drawString(controlTitle, 3 << 4, 18 << 4);
		
		g.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
		g.drawString(control1, 4 << 4, 20 << 4);
		g.drawString(control2, 4 << 4, 22 << 4);
		g.drawString(control3, 4 << 4, 24 << 4);
		g.drawString(control4, 4 << 4, 26 << 4);
		g.drawString(control5, 4 << 4, 28 << 4);
		g.drawString(control6, 4 << 4, 30 << 4);
		g.drawString(control7, 4 << 4, 32 << 4);
		
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
		g.drawString(contactTitle, 3 << 4, 38 << 4);
		
		g.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
		g.drawString(contact1, 4 << 4, 40 << 4);
		g.drawString(contact2, 4 << 4, 42 << 4);
		g.drawString(contact3, 4 << 4, 44 << 4);	
		
		// Back button
		if(isMouseOverBackButton) {
			g.setColor(new Color(0x3D3D3D));
			g.fillRect(70 << 4, 40 << 4, 210, 90);
			g.setColor(new Color(0xFFC700));
			g.setFont(new Font("TimesNewRoman", Font.BOLD, 40));
			g.drawString("<<BACK", 70 << 4, 44 << 4);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(65 << 4, 38 << 4, 200, 100);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesNewRoman", Font.BOLD, 40));
			g.drawString("<<BACK", 70 << 4, 44 << 4);
		}
	}
}
