package com.zevyirmiyahu.graphics;

public class AnimatedSprite extends Sprite {

	private Sprite sprite;
	private int rate = 5;
	private int frame = 0;
	private int time = 0;
	private int length = -1; // length of animation (number of frames on sheet)
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if(length > sheet.getSprites().length) System.err.println("ERROR: Length of animation is too long!");
	}
	
	public void update() {
		time++;
		if(time % rate == 0) {
			if(frame >= length - 1) frame = 0;
			else frame++;
			sprite = sheet.getSprites()[frame];
		}
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setFrameRate(int frames) {
		rate = frame;
	}
	
	public void setFrame(int index) {
		if(index > sheet.getSprites().length - 1) {
			System.err.println("Index out of bounds");
			return;
		}
		sprite = sheet.getSprites()[index];
	}
}
