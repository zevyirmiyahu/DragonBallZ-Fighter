package com.zevyirmiyahu.entity.mob;

import java.util.ArrayList;

import com.zevyirmiyahu.Game;
import com.zevyirmiyahu.entity.Entity;
import com.zevyirmiyahu.entity.projectile.Shot1;
import com.zevyirmiyahu.entity.projectile.Shot2;
import com.zevyirmiyahu.entity.projectile.ZamasuShot1;

public class Mob extends Entity {

	protected boolean moving = false;
	protected int health;
	protected ArrayList<String> kingKaiSpeech = new ArrayList<String>();
	
	protected enum Direction {
		LEFT, RIGHT, UP, DOWN
	}
	
	protected Direction dir;
	
	protected void intializeKingKaiSpeech() {
		String s1 = "GOKU! Zamasu has returned from oblivion!";
		String s2 = "He is trying to destroy all of spirit world.";
		String s3 = "You must stop him Goku! Please!";
		String s4 = "Be VERY careful he's extremely powerful.";
		kingKaiSpeech.add(s1);
		kingKaiSpeech.add(s2);
		kingKaiSpeech.add(s3);
		kingKaiSpeech.add(s4);
		kingKaiSpeech.add(""); //marks the end of the speech
	}
	
	public void shoot(Entity e) {
		if(e instanceof Shot1) Game.level.add((Shot1)e);
		if(e instanceof Shot2) Game.level.add((Shot2)e);
		if(e instanceof ZamasuShot1) Game.level.add((ZamasuShot1)e);
	}
}
