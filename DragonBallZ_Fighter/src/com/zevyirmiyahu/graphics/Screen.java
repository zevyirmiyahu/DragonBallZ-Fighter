package com.zevyirmiyahu.graphics;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	public int xOffset, yOffset;
	
	private final int ALPHA_COL = 0; //0x000000 alpha channel
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	// clear resets pixels all to black
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	/*
	public void renderRect(int xp, int yp, int width, int height, int color) {
		
		for(int x = xp; x < xp + width; x++) {
			if(x < 0 | x >= this.width || yp >= this.height) continue; //top part of rectangle
			if(yp > 0) pixels[x + yp * this.width] = color;
			if(yp + height >= this.height) continue; 
			if(yp + height > 0) pixels[x + (yp + height) * this.width] = color;
		}
		for(int y = yp; y <= yp + height; y++) {
			if(xp >= this.width || y < 0 || y >= this.height) continue; //this.width is refering to width of screen not local variable
			if(xp >= 0) pixels[xp + y * this.width] = color;
			if(xp + width >= this.width) continue; 
			if(xp + width > 0) pixels[(xp + width) + y * this.width] = color;
		}
	} */
	
	// fixed: sprite is stuck to 'background'
	public void renderSprite(int xp, int yp, Sprite sprite, boolean xFlip, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				int xs = x;
				if(xFlip) xs = 31 - x; // flips sprite for mirror image
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[xs + y * sprite.getWidth()];
				if(col != ALPHA_COL) pixels[xa + ya * width] = col;
			}
		}
	}
	
	// Renders complete sprite sheet
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
			int ya = y + yp;
			for(int x = 0; x < sheet.SPRITE_WIDTH; x++) {
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
			}
		}
	}
}
