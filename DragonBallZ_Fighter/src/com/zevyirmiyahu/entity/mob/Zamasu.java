package com.zevyirmiyahu.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import com.zevyirmiyahu.Game;
import com.zevyirmiyahu.Game.STATE;
import com.zevyirmiyahu.entity.projectile.Shot1;
import com.zevyirmiyahu.entity.projectile.Shot2;
import com.zevyirmiyahu.entity.projectile.ZamasuShot1;
import com.zevyirmiyahu.graphics.AnimatedSprite;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.level.Level;

public class Zamasu extends Mob {

	private boolean xFlip = true;
	private boolean isHit = false; // used to change character sprite when hit
	private boolean jumping = false; // controls AI jump
	private boolean hasTeleported = false; // controls teleportation animation time
	private boolean isDead = false;
	boolean isZamasuPunching = false;
	
	private int isDeadTimer = 0; // creates a slight pause after player wins
	private int moveTimer = 0; // moving timer used in moveAI()
	private int jumpTimer = 0; // timer used in AI jump mechanics
	private int punchTimer = 0; // used to control punching in attackAI()
	private int teleportTimer = 0; // used for teleportAI()
	private int time = 0; // used to keep track of general time
	
	static final int ATTACK_SHOT = 25; // damage goku gets for being shot
	static final int ATTACK_PUNCH = 6;
	public static final int ki_energy = 4; // energy used for shoot at goku
	private int health = 632;
	private int ki = 316;
	
	private AnimatedSprite animZamasu = new AnimatedSprite(SpriteSheet.zamasuStanding, 32, 32, 3);
	private AnimatedSprite animZamasuWalk = new AnimatedSprite(SpriteSheet.zamasuRight, 32, 32, 3);
	private AnimatedSprite animZamasuJump = new AnimatedSprite(SpriteSheet.zamasuJumping, 32, 32, 1);
	private AnimatedSprite animZamsuFall = new AnimatedSprite(SpriteSheet.zamasuFalling, 32, 32, 1);
	private AnimatedSprite animZamasuShoot = new AnimatedSprite(SpriteSheet.zamasuShooting, 32, 32, 1);
	private AnimatedSprite animZamasuPunch = new AnimatedSprite(SpriteSheet.zamasuPunching, 32, 32, 2);
	private AnimatedSprite animZamasuHit = new AnimatedSprite(SpriteSheet.zamasuHit, 32, 32, 1);
	private AnimatedSprite animZamasuTeleport = new AnimatedSprite(SpriteSheet.zamasuTeleporting, 32, 32, 2);
	private AnimatedSprite animZamasuDead = new AnimatedSprite(SpriteSheet.zamasuIsDead, 32, 32, 1);
	private AnimatedSprite animCurrent = animZamasu;

	public Zamasu(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
	}
	
	public void update() {
		
		// controls change between game and win menu
		if(health <= 0){
			isDeadTimer++;
			isDead = true;
			animCurrent = animZamasuDead;
			
			if(isDead && isDeadTimer > 240) Game.state = STATE.WIN;
		}
		else {
			time++;
			fightAI();
			animCurrent.update();
			
			if(zamasuShot() || zamasuPunched()) {
				time = 0;
				isHit = true;
				animCurrent = animZamasuHit;
			}
			// ZAMASU IS SHOT
			if(!isZamasuPunching && isHit && time / 60.0 < 0.3) animCurrent = animZamasuHit;
			else isHit = false;
			
			if(time > 7200) time = 0; // must reset time, avoids game crash
		}
	}

	private boolean zamasuPunched() {
		boolean gokuFacingZamasu = false;
		int gokuDistance = (int)(Level.getPlayer(0).getX() - x) >> 4;
		
		if(Level.getPlayer(0).xFlipGoku && gokuDistance > 0 || !Level.getPlayer(0).xFlipGoku && gokuDistance < 0) {
			gokuFacingZamasu = true; // goku is face correct direction to hit Zamasu
		}
		if(!gokuFacingZamasu) return false; // if not, return false goku cant punch backwards
		if(Level.getPlayer(0).isGokuPunching && gokuDistance > -2 && gokuDistance < 3) {
			if(Level.getPlayer(0).isSuper) {
				health -= Level.getPlayer(0).attack_punchSuper;
				if(gokuDistance > 0) x -= 2;
				else x += 2;
			}
			else {
				health -= Level.getPlayer(0).attack_punch;
				if(gokuDistance > 0) x -= 1;
				else x += 1;
			}
			return true;
		}
		return false;
	}
	
	private boolean zamasuShot() {
		for(int i = 0; i < Level.gokuShots1.size(); i++) {			
			Shot1 shot1 = Level.getShot1(i);
			boolean isShot1 = collision(this, Level.getShot1(i));
			if(isShot1) {
				if(Level.getPlayer(0).isSuper) {
					health -= Level.getPlayer(0).attack_shot1Super;
				} 
				else health -= Level.getPlayer(0).attack_shot1;
				Level.remove(shot1, i); 
				return true;
			}
		}
		for(int i = 0; i < Level.gokuShots2.size(); i++) {
			Shot2 shot2 = Level.getShot2(i);
			boolean isShot2 = collision(this, Level.getShot2(i));
			if(isShot2) {
				if(Level.getPlayer(0).isSuper) {
					health -= Level.getPlayer(0).attack_shot2Super;
				} 
				else health -= Level.getPlayer(0).attack_shot2;
				Level.remove(shot2, i); 
				return true;
			}
		}
		return false;
	}
	
	/*
	 * This is the methods that controls Zamasu's behavior in the fight.
	 * Uses 5 helper methods: 
	 * 	punchAI() 
	 * 	moveAI()
	 * 	shootAI()
	 *	jumpAI()
	 * 	teleportAI()
	 * 
	 * Distance is the main factor that determines what Zamasu does during battle. Randomness has
	 * been added for a more realistic combat tactics.
	 * 
	 */
	private void fightAI() {
		
		int gokuDistance = (int)(Level.getPlayer(0).getX() - x); // distance Zamasu is to Goku
		if(gokuDistance < 8) xFlip = true;
		if(gokuDistance > 8) xFlip = false;
		
		if(Math.abs(gokuDistance) <= 16) {
			punchAI(gokuDistance);
		}
		else {
			moveAI(gokuDistance);		
			shootAI(gokuDistance);
		}
		jumpAI(gokuDistance);
		teleportAI(gokuDistance);
	}
	
	private void moveAI(int xDistance) {
		
		int xa = 0;
		
		// random walk timer
		moveTimer++;
		if(moveTimer > 120) moveTimer = 0;
		Random ran = new Random();
		int n = ran.nextInt(5);

		if(moveTimer - n > 60) {
			if(xDistance < 16) {
				animCurrent = animZamasuWalk;
				xa--;
			}
			else if(xDistance > 16) {
				animCurrent = animZamasuWalk;
				xa++;
			}
		} 
		else {
			animCurrent = animZamasu;
			xa = 0;
		}
		x += xa;
	}
	
	private void jumpAI(int xDistance) {
		jumpTimer++;
		int yCoord = this.getTileCoordY();
		double ya = 0;
		double gravity = 0;
		int gokuDistance = Math.abs(xDistance) >> 4;
		Random ran = new Random();
		
		if(yCoord < 10 && !jumping) gravity = 2.5;
		if(Level.getPlayer(0).isPlayerJumping() && yCoord == 10 && gokuDistance > 6 && ran.nextInt(100) < 4) jumping = true;
		if(jumping) {
				ya -= 0.009 * Math.pow(jumpTimer, 2) + 1.2; // jump amount
				animZamasuJump.update();
				animCurrent = animZamasuJump;
		}
		if(jumpTimer % 30 == 0) {
			jumping = false;
			jumpTimer = 0;
		}
		if(ya + gravity > 0) {
			animCurrent = animZamsuFall;
		} 
		y += Math.floor(ya + gravity); // floor to ensure land on same y coordinate
		if(jumpTimer > 7200) jumpTimer = 0; // reset
	}
	
	private void shootAI(int xDistance) {
		if((Math.abs(xDistance) >> 4) < 2) return; // too close to shoot goku
		if(ki <= 0) return; // can no longer shoot
		
		Random ran = new Random();
		int n = ran.nextInt(300);
		
		if(n == 0) {
			shoot(new ZamasuShot1(x + 8, y + 16, 2.1, xFlip));
			if(ki > 0) ki -= ki_energy;
			animCurrent = animZamasuShoot;
		}
	}
	
	private void punchAI(int xDistance) {
		punchTimer++;
		Random ran = new Random();
		int num = ran.nextInt(10);
		if(num > 5) return; // limits punching
		
		if(Math.abs(xDistance) < 16 && punchTimer % 60 == 0) {
			punchTimer = 0;
			isZamasuPunching = true;
			animZamasuPunch.update();
			animCurrent = animZamasuPunch;
		} else isZamasuPunching = false;
	}
	
	private void teleportAI(int xDistance) {
		int gokuDistance = Math.abs(xDistance) >> 4;
		teleportTimer++;
		if(teleportTimer % 120 == 0 && !hasTeleported && gokuDistance > 5) {
			teleportTimer = 0; // reset
			Random ran = new Random();
			int num = ran.nextInt(10);
			
			if(num == 0) {
				hasTeleported = true;
				x = Level.getPlayer(0).getX() + 32;
			} else if(num == 1) {
				hasTeleported = true;
				x = Level.getPlayer(0).getX() - 32;
			}
		
			// ensures zamasu teleports only within borders
			if(x > 431) x = 431;
			if(x < 1) x = 1;
		}
		else if((Level.getPlayer(0).isGokuPunching && gokuDistance < 2)) {
			teleportTimer = 0; // reset
			Random ran = new Random();
			int num = ran.nextInt(100);
			
			if(num == 0) {
				hasTeleported = true;
				x = Level.getPlayer(0).getX() + 96;
			} else if(num == 1) {
				hasTeleported = true;
				x = Level.getPlayer(0).getX() - 96;
			}
		
			// ensures zamasu teleports only within borders
			if(x > 431) x = 431;
			if(x < 1) x = 1;
		}
		if(hasTeleported) {
			animZamasuTeleport.update();
			animCurrent = animZamasuTeleport;
		}
		if(teleportTimer > 60) hasTeleported = false;
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		// health bar
		g2.setColor(Color.BLACK);
		g2.drawRect(3 * 455 - 317 - 15, 15, 317, 31);
		g2.setColor(Color.RED);
		//g2.fillRect(3 * 455 - health - 16, 16, health, 30);
		g2.fillRect(3 * 455 - (health / 2) - 16, 16, (health / 2), 30);

		// ki bar
		g2.setColor(Color.BLACK);
		g2.drawRect(3 * 455 - 317 - 15, 61, 317, 31);
		g2.setColor(Color.BLUE);
		g2.fillRect(3 * 455 - ki - 16, 62, ki, 30);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
		g.drawString("Zamasu", 76 << 4, 8 << 4);
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)x, (int)y, animCurrent.getSprite(), xFlip, false);
	}
}
