package com.zevyirmiyahu.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	private boolean[] keys = new boolean[120];
	public boolean jump, left, right, punch, shoot1, shoot2, superSaiyan;
	
	public Keyboard(){}
	
	public void update() {
		jump = keys[KeyEvent.VK_SPACE];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		punch = keys[KeyEvent.VK_Z];
		shoot1 = keys[KeyEvent.VK_X];
		shoot2 = keys[KeyEvent.VK_C];
		superSaiyan = keys[KeyEvent.VK_A];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
