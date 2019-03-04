package com.zevyirmiyahu;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.zevyirmiyahu.entity.mob.Player;
import com.zevyirmiyahu.entity.mob.Zamasu;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.input.Keyboard;
import com.zevyirmiyahu.input.Mouse;
import com.zevyirmiyahu.level.AboutMenu;
import com.zevyirmiyahu.level.Clouds;
import com.zevyirmiyahu.level.GameOverMenu;
import com.zevyirmiyahu.level.Level;
import com.zevyirmiyahu.level.OpeningScene;
import com.zevyirmiyahu.level.StartMenu;
import com.zevyirmiyahu.level.WinMenu;

/*
 * Dragon Ball Z fighting game
 * 
 * * @author Zev Yirmiyahu
 * 
 * E-Mail: zy@zevyirmiyahu.com
 * 
 * GitHub: https://github.com/zevyirmiyahu 
 * 
 * Personal Website: www.zevyirmiyahu.com
 * 
 */

public class Game extends Canvas implements Runnable {
	
	private static int width = 455;
	private static int height = 256; 
	private static int scale = 3;

	private static boolean running = false;
	
	private String title = "Dragon Ball Z Fighter";
	private Thread thread;
	private JFrame frame;
	private Screen screen;
	
	private static boolean resetGame = false; // used for when player wants to player again
	private static StartMenu startMenu;
	private static AboutMenu aboutMenu;
	private static OpeningScene openingScene;
	private static GameOverMenu gameOverMenu;
	private static WinMenu gameWinMenu;
	public static Level level;
	
	private Mouse mouse;
	private Keyboard input;
	private Player player;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public static enum STATE {
		MENU, 		// main menu
		OPENING,	// opening scene
		GAME,		// game beings
		ABOUT,		// about menu
		GAMEOVER, 	// Lose screen
		WIN			// Win screen
	}
	
	public static STATE state = STATE.MENU; 
		
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		
		setPreferredSize(size);
		setFocusable(true);

		screen = new Screen(width, height);
		frame = new JFrame();
		mouse = new Mouse();
		input = new Keyboard();
		player = new Player(4, 10, input);
		level = new Level();
		level.add(player);
		
		startMenu = new StartMenu(mouse);
		aboutMenu = new AboutMenu(mouse);
		openingScene = new OpeningScene();
		gameOverMenu = new GameOverMenu(mouse);
		gameWinMenu = new WinMenu(mouse);
		addKeyListener(input);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static int width() {
		return width * scale;
	}
	
	public static int height() {
		return height * scale;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1_000_000_000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		// Game Loop
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + updates + "UPS, " + frames +"FPS" 
				+ "                                                            "
				+ "                                                            "
				+ "                                                            "
				+ "                              Author: Zev Yirmiyahu");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	// if player decides to play again
	public void reinitialize() {
		if(resetGame) {
			level.clearAll();
			level = new Level();
			player = new Player(4, 10, input);
			level.add(player);
			openingScene = new OpeningScene();
			resetGame = false;
		}
	}
	
	public void update() {
		if(state.equals(STATE.MENU)) {
			resetGame = true;
			startMenu.update();
		}
		else if(state.equals(STATE.OPENING)) {
			openingScene.update();
			input.update();
		}
		else if(state.equals(STATE.ABOUT)) {
			aboutMenu.update();
		}
		else if(state.equals(STATE.GAME)) {			
			input.update();
			level.update();
		}
		else if(state.equals(STATE.GAMEOVER)) {
			gameOverMenu.update();
			reinitialize();
		}
		else if(state.equals(STATE.WIN)) {
			gameWinMenu.update();
			reinitialize();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
	
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
	
		if(state.equals(STATE.GAME)) {			
			level.render(screen);
			
			// render players health/ki bars
			Level.getPlayer(0).render(g);
			((Zamasu)Level.getEnemy(0)).render(g);
			
		}
		else if(state.equals(STATE.MENU)) {
			startMenu.render(screen);
			startMenu.render(g);
		}
		else if(state.equals(STATE.ABOUT)) {
			aboutMenu.render(g);
			aboutMenu.render(screen);
		}
		else if(state.equals(STATE.OPENING)) {
			openingScene.render(screen);
			openingScene.render(g);
		}
		else if(state.equals(STATE.GAMEOVER)) {
			gameOverMenu.render(screen);
			gameOverMenu.render(g);
		}
		else if(state.equals(STATE.WIN)) {
			gameWinMenu.render(screen);
			gameWinMenu.render(g);
		}
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}	
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {

		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}
