package com.zevyirmiyahu.graphics;

public class Sprite {
	
	private int x, y;
	private int width, height;
	private final int SIZE;
	
	public int[] pixels;
	protected SpriteSheet sheet;
	
	public static Sprite playerGoku = new Sprite(32, 0, 0, SpriteSheet.playerGoku);
	
	public static Sprite kingKaiHead = new Sprite(1, 0, 32, 16, SpriteSheet.kingKai);
	
	public static Sprite clouds = new Sprite(0, 0, 1365, 256, SpriteSheet.levelOneForeground);
	
	// For a square sprite
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.width = size;
		this.height = size;
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		SIZE = size;
		pixels = new int[size * size];
		load();
	}
	
	// for none square sprites
	public Sprite(int x, int y, int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		SIZE = -1;
		pixels = new int[width * height];
		load();
	}
	
	// constructor specifically for AnimatedSprite class
	public Sprite(SpriteSheet sheet, int width, int height) {
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	// load pixels from sprite sheet of particular sprite
	private void load() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[y * width + x] = sheet.pixels[sheet.SPRITE_WIDTH * (y + this.y) + (x + this.x)];
			}
		}
	}
}
