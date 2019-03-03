package com.zevyirmiyahu.entity;

import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.level.Level;

public abstract class Entity {

	protected double x, y;
	protected Level level;
	
	public Entity() {}
	
	// set characters position
	public void setPosition(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
	}
	
	// used primarily for keeping players on screen
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getTileCoordX() {
		return (int)x >> 4;
	}
	public int getTileCoordY() {
		return (int)y >> 4;
	}
	
	public boolean collision(Entity e1, Entity e2) {
		int deltaX = ((int)e1.getX() - (int)e2.getX()) >> 4;
		int deltaY = ((int)e1.getY() - (int)e2.getY()) >> 4;	
		if(Math.abs(deltaX) < 1 && Math.abs(deltaY) <= 2) return true;
		else return false;
	}
	
	public void update() {}
	
	public void render(Screen screen) {}
	
	public void init(Level level) {
		this.level = level;
	}
}
