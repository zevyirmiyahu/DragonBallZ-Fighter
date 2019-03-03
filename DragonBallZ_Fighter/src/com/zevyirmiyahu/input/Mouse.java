package com.zevyirmiyahu.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1; // mouse button
	
	public Mouse() {}
	
	public double getX() {
		return mouseX ;
	}
	
	public double getY() {
		return mouseY;
	}
	
	public static boolean isClicked() {
		if(mouseB == 1) {
			mouseB = MouseEvent.NOBUTTON;
			return true;
		}
		else {
			mouseB = MouseEvent.NOBUTTON;
			return false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseB = e.getButton();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	    mouseB = MouseEvent.NOBUTTON; // resets mouse button after pressed
	  }
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
