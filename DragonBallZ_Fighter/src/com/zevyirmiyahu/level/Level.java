package com.zevyirmiyahu.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zevyirmiyahu.entity.Entity;
import com.zevyirmiyahu.entity.mob.KingKai;
import com.zevyirmiyahu.entity.mob.Player;
import com.zevyirmiyahu.entity.mob.Zamasu;
import com.zevyirmiyahu.entity.projectile.Shot1;
import com.zevyirmiyahu.entity.projectile.Shot2;
import com.zevyirmiyahu.entity.projectile.ZamasuShot1;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;

/*
 * Note: all backgrounds are pre-drawn images for a 
 * more cartoon graphical effect. Level generates level by
 * loading an image to screen
 */

public class Level {
		
	static List<Player> players = new ArrayList<Player>();
	protected static List<KingKai> kingKais = new ArrayList<KingKai>();
	protected static List<Entity> enemies = new LinkedList<Entity>();
	public static List<Shot1> gokuShots1 = new LinkedList<Shot1>();
	public static List<Shot2> gokuShots2 = new LinkedList<Shot2>();
	public static List<ZamasuShot1> zamasuShots1 = new ArrayList<ZamasuShot1>();
	public static List<Clouds> cloud = new ArrayList<Clouds>();	
	
	public Level() {
		add(new Zamasu(20, 10));
		add(new KingKai(11, 4));
		add(new Clouds(0, 0));
	}
	
	// used for playing game again after win/lose
	public void clearAll() {
		players.remove(0);
		kingKais.remove(0);
		enemies.remove(0);
		cloud.remove(0);
	}
	
	public static Player getPlayer(int index) {
		return players.get(index);
	}
	
	public static Zamasu getZamasu(int index) {
		return (Zamasu)enemies.get(index);
	}
 	
	public static KingKai getKingKai(int index) {
		return kingKais.get(index);
	}
	
	public static Entity getEnemy(int index) {
		return enemies.get(index);
	}
	
	public static Shot1 getShot1(int index) {
		return gokuShots1.get(index);
	}
	
	public static Shot2 getShot2(int index) {
		return gokuShots2.get(index);
	}
	
	public static ZamasuShot1 getZamasuShot(int index) {
		return zamasuShots1.get(index);
	}
	
	public int zamasuShots1Size() {
		return zamasuShots1.size();
	}
	
	public void update() {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		for(int i = 0; i < kingKais.size(); i++) {
			kingKais.get(i).update();
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		for(int i = 0; i < gokuShots1.size(); i++) {
			gokuShots1.get(i).update();
		}
		for(int i = 0; i < gokuShots2.size(); i++) {
			gokuShots2.get(i).update();
		}
		for(int i = 0; i < zamasuShots1.size(); i++) {
			zamasuShots1.get(i).update();
		}
		for(int i = 0; i < cloud.size(); i++) {
			cloud.get(i).update();
		}

		remove(); // removes all shots out of range
		addCloud(); // check to add another cloud
		
		// check for shot collision and removes 
		int size = Math.max(zamasuShots1.size(), Math.max(gokuShots1.size(), gokuShots2.size()));
		int currIndex = 0;
		while(size != 0 && currIndex < size) {
			Shot1 e1 = null; 
			Shot2 e2 = null; 
			ZamasuShot1 e3 = null; 
			if(!gokuShots1.isEmpty() && currIndex < gokuShots1.size()) e1 = gokuShots1.get(currIndex);
			if(!gokuShots2.isEmpty() && currIndex < gokuShots2.size()) e2 = gokuShots2.get(currIndex);
			if(!zamasuShots1.isEmpty() && currIndex < zamasuShots1.size()) e3 = zamasuShots1.get(currIndex);
			remove(e1, e3);
			remove(e2, e3);
			currIndex++;
		}
	}
	
	private void addCloud() {
		if(cloud.get(i).getX() < -1100 && cloud.size() == 1) add(new Clouds(26, 0));
	}
	
	public void add(Entity e) {
		e.init(this);
		if(e instanceof Player) {
			players.add((Player)e);
		}
		else if(e instanceof KingKai) {
			kingKais.add((KingKai)e);
		}
		else if(e instanceof Zamasu) {
			enemies.add((Zamasu)e);
		}
		else if(e instanceof Shot1) {
			gokuShots1.add((Shot1)e);
		}
		else if(e instanceof Shot2) {
			gokuShots2.add((Shot2)e);
		}
		else if(e instanceof ZamasuShot1) {
			zamasuShots1.add((ZamasuShot1)e);
		}
		else if(e instanceof Clouds) {
			cloud.add((Clouds)e);
		}
	}
	
	// remove shots that are off screen
	private static void remove() {
		for(int i = 0; i < gokuShots1. size(); i ++) {
			if((int)getShot1(i).getX() >> 4 < -1 || (int)getShot1(i).getX() >> 4 > 27) {
				gokuShots1.remove(i);
			}
		}
		for(int i = 0; i < gokuShots2.size(); i++) {
			if((int)getShot2(i).getX() >> 4 < -1 || (int)getShot2(i).getX() >> 4 > 27) {
				gokuShots2.remove(i);
			}
		}
		for(int i = 0; i < zamasuShots1.size(); i++) {
			if((int)getZamasuShot(i).getX() >> 4 < -1 || (int)getZamasuShot(i).getX() >> 4 > 27) {
				zamasuShots1.remove(i);
			}
		}
		for(int i = 0; i < cloud.size(); i++) {
			if((int)cloud.get(i).getX() >> 4 < -120) cloud.remove(i);	
		}
	}
	// removes collided shots
	public static void remove(Entity e1, Entity e2) {
		
		if(e1 == null || e2 == null) return;

		if((int)e1.getX() >> 4 == (int)e1.getX() >> 4 && (int)e1.getY() >> 4 == (int)e2.getY() >> 4) {
			if(e1 instanceof ZamasuShot1) {
				int i = zamasuShots1.indexOf((ZamasuShot1)e1);
				zamasuShots1.remove(i);
			}
			if(e2 instanceof Shot1) {
				int i = gokuShots1.indexOf((Shot1)e2);
				gokuShots1.remove(i);
			}
		}
	}
	
	public static void remove(Entity e, int index) {
		if(e instanceof ZamasuShot1) {
			zamasuShots1.remove(index);
		}
		if(e instanceof Shot1) {
			gokuShots1.remove(index);
		}
		if(e instanceof Shot2) {
			gokuShots2.remove(index);
		}
	}

	public void render(Screen screen) {
		screen.renderSheet(0, 0, SpriteSheet.levelOneBackground, true);	
		
		for(int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(screen);
		}
		for(int i = 0; i < gokuShots1.size(); i++) {
			gokuShots1.get(i).render(screen);
		}
		for(int i = 0; i < gokuShots2.size(); i++) {
			gokuShots2.get(i).render(screen);
		}
		for(int i = 0; i < zamasuShots1.size(); i++) {
			zamasuShots1.get(i).render(screen);
		}
		for(int i = 0; i < cloud.size(); i++) {
			cloud.get(i).render(screen);
		}
	}
}
