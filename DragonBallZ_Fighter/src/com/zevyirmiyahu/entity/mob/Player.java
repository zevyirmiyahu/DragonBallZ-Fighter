package com.zevyirmiyahu.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.zevyirmiyahu.Game;
import com.zevyirmiyahu.Game.STATE;
import com.zevyirmiyahu.entity.projectile.Shot1;
import com.zevyirmiyahu.entity.projectile.Shot2;
import com.zevyirmiyahu.entity.projectile.ZamasuShot1;
import com.zevyirmiyahu.graphics.AnimatedSprite;
import com.zevyirmiyahu.graphics.Screen;
import com.zevyirmiyahu.graphics.SpriteSheet;
import com.zevyirmiyahu.input.Keyboard;
import com.zevyirmiyahu.level.Level;

public class Player extends Mob {
	
	int attack_shot1 = 4;
	int attack_shot2 = 8;
	int attack_punch = 1;
	int attack_shot1Super = 6; // super attacks are for when goku is super saiyan, gives more damage
	int attack_shot2Super = 14;
	int attack_punchSuper = 2;
	private static int ki_energy1 = 8; // amount of ki used for shot1
	private static int ki_energy2 = 15; // amount of ki used for shot2
	
	boolean isSuper = false; // controls if player is super saiyan
	boolean xFlipGoku = false;
	private boolean isDead = false; // controls game over menu change
	private boolean jumping = false;
	private static boolean walking = false;
	private boolean fall = false; // used for signaling gravity in opening scene
	private boolean isHit = false; // used to change character sprite when hit
	boolean isGokuPunching = false; // used to determine if Zamasu takes damage from punch
	
	private int walkDirection = 0; // direction player walks for purpose of jumping
	private int isDeadTimer = 0; // controls switch between game and game over menu
	private int jumpTimer = 0; // timer for jumping
	private int shootTimer = 0; // timer for firing
	private int punchTimer = 0; // so player can't just hold down punch for continues damage
	private int superSaiyanTimer = 0; 
	private int unsuperSaiyanTimer = 0; // controls changing to/from super saiyan
	private int time = 0; // keeps track of general timing
	
	private int health = 316;
	private double ki = 316; // energy
	
	//private Screen screen;
	private Keyboard input;
	
	private AnimatedSprite animPlayer = new AnimatedSprite(SpriteSheet.playerGokuStanding, 32, 32, 7);
	private AnimatedSprite animPlayerWalk = new AnimatedSprite(SpriteSheet.playerGokuRight, 32, 32, 3);
	private AnimatedSprite animPlayerJump = new AnimatedSprite(SpriteSheet.playerGokuJumping, 32, 32, 1);
	private AnimatedSprite animPlayerFall = new AnimatedSprite(SpriteSheet.playerGokuFalling, 32, 32, 1);
	private AnimatedSprite animPlayerPunch = new AnimatedSprite(SpriteSheet.playerGokuPunching, 32, 32, 2);
	private AnimatedSprite animPlayerShoot1 = new AnimatedSprite(SpriteSheet.playerGokuShooting1, 32, 32, 1);
	private AnimatedSprite animPlayerShoot2 = new AnimatedSprite(SpriteSheet.playerGokuShooting2, 32, 32, 1);
	private AnimatedSprite animPlayerHit = new AnimatedSprite(SpriteSheet.playerGokuHit, 32, 32, 1);
	private AnimatedSprite animPlayerDead = new AnimatedSprite(SpriteSheet.playerGokuDead, 32, 32, 1);
	
	// super saiyan
	private AnimatedSprite animPlayerSuper = new AnimatedSprite(SpriteSheet.playerGokuStandingSuper, 32, 32, 7);
	private AnimatedSprite animPlayerWalkSuper = new AnimatedSprite(SpriteSheet.playerGokuRightSuper, 32, 32, 3);
	private AnimatedSprite animPlayerJumpSuper = new AnimatedSprite(SpriteSheet.playerGokuJumpingSuper, 32, 32, 1);
	private AnimatedSprite animPlayerFallSuper = new AnimatedSprite(SpriteSheet.playerGokuFallingSuper, 32, 32, 1);
	private AnimatedSprite animPlayerPunchSuper = new AnimatedSprite(SpriteSheet.playerGokuPunchingSuper, 32, 32, 2);
	private AnimatedSprite animPlayerShoot1Super = new AnimatedSprite(SpriteSheet.playerGokuShooting1Super, 32, 32, 1);
	private AnimatedSprite animPlayerShoot2Super = new AnimatedSprite(SpriteSheet.playerGokuShooting2Super, 32, 32, 1);
	private AnimatedSprite animPlayerHitSuper = new AnimatedSprite(SpriteSheet.playerGokuHitSuper, 32, 32, 1);
	private AnimatedSprite animPlayerEnergySuper = new AnimatedSprite(SpriteSheet.playerGokuEnergySuper, 32, 32, 2);
	
	private AnimatedSprite animCurrent = animPlayer; // Initialize for standing position
	private AnimatedSprite animCurrentEnergySuper = animPlayerEnergySuper; // aura when Goku first turns super saiyan 
		
	public Player(int x, int y, Keyboard input) {
		this.x = x << 4;
		this.y = y << 4;
		this.input = input;
	}
	
	public boolean isPlayerJumping() {
		return jumping;
	}
	
	public void update() {
		
		int xCoord = this.getTileCoordX(); // players coordinates in a tile format.
		int yCoord = this.getTileCoordY();
		double xa = 0, ya = 0;
		double gravity = 0;
		jumpTimer++;
		shootTimer++;
		time++;
		
		if(yCoord < 10 && !jumping && Game.state.equals(Game.STATE.GAME)) gravity = 2.5;
		
		// player is in level so no limit to attack
		if(Game.state.equals(Game.STATE.GAME)) {
			
			// controls change between game and lose menu
			if(health <= 0){
				isDeadTimer++;
				isDead = true;
				animCurrent = animPlayerDead;
				
				if(isDead && isDeadTimer > 240) Game.state = STATE.GAMEOVER;
			}
			else {
				// controls super saiyan change mechanism
				if(ki > 0 && input.superSaiyan && !isSuper && unsuperSaiyanTimer > 60) {
					unsuperSaiyanTimer = 0;
					isSuper = true;
				}
				if(input.superSaiyan && isSuper && superSaiyanTimer > 60) {
					superSaiyanTimer = 0;
					isSuper = false;
				}
				if(isSuper) {
					attack_shot1 = 6; // makes attacks stronger being super saiyan
					attack_shot2 = 10;
					ki -= 0.1; // being super saiyan uses ki energy
					superSaiyanTimer++;
					animPlayerEnergySuper.update();
					animCurrentEnergySuper = animPlayerEnergySuper;
				}
				if(!isSuper) unsuperSaiyanTimer++;
				if(superSaiyanTimer > 7200 || unsuperSaiyanTimer > 7200) {
					superSaiyanTimer = 0;
					unsuperSaiyanTimer = 0;
				}
				if(ki <= 0) isSuper = false; // no more energy to be super saiyan
				
				attack();
				if(walking) move();
			}
		} 
		else if(Game.state.equals(Game.STATE.OPENING)) { // limit players attack
			curveMove(); // curve motion for king Kai's planet
			if(Level.getKingKai(0).speechFinished) {
				animPlayerJump.update();
				animCurrent = animPlayerJump;
				y--;
				if(y == 2) fall = true;
				if(fall) {
					y += 3.3;
					animPlayerFall.update();
					animCurrent = animPlayerFall;
				}
				if(yCoord > 37) {
					Game.state = STATE.GAME; // Fight begins!
					this.setPosition(7, -1);
				}
			}
		}
		
		// JUMPING
		if(input.jump && !jumping && yCoord == 10) jumping = true;	// can jump
		
		if(jumping && !isDead) {
			ya -= 0.009 * Math.pow(jumpTimer, 2) + 1.2;  //2.7;	// jump amount
			if(isSuper) {
				animPlayerJumpSuper.update();
				animCurrent = animPlayerJumpSuper;
			}
			else {				
				animPlayerJump.update();
				animCurrent = animPlayerJump;
			}
		}
		
		if(jumpTimer % 30 == 0) { // reset jump
			jumping = false;
			jumpTimer = 0;
		}
		
		// FALLING
		if(ya + gravity > 0) {
			if(isSuper) {
				animCurrent = animPlayerFallSuper;
			}
			else {				
				animCurrent = animPlayerFall;
			}
		}
		
		// PLAYER IS SHOT
		if(playerShot() || playerPunched()) {
			xa = 0; // player can't move for an instant
			isHit = true;
			time = 0;
		}
		if(!isDead && isHit && time / 60.0 < 0.3) {
			if(isSuper) animCurrent = animPlayerHitSuper;
			else animCurrent = animPlayerHit;
		}
		else isHit = false;
		
		y += Math.floor(ya + gravity); // floor to ensure lands on same y coordinate
		if(time > 7200) time = 0; // must reset to avoid game crash
	}
	
	private boolean playerPunched() {
		int gokuDistance = (int)(Level.getPlayer(0).getX() - Level.getEnemy(0).getX());
		if(((Zamasu)Level.getEnemy(0)).isZamasuPunching && gokuDistance < 2) {
			health -= ((Zamasu)Level.getEnemy(0)).ATTACK_PUNCH;
			return true;
		}
		return false;
	}
	
	private boolean playerShot() {
		for(int i = 0; i < Level.zamasuShots1.size(); i++) {			
			ZamasuShot1 shot1 = Level.getZamasuShot(i);
			boolean isShot = collision(this, shot1);
			if(isShot) {
				health -= Zamasu.ATTACK_SHOT;
				Level.remove(shot1, i); 
				return true;
			}
		}
		return false;
	}
	
	private void attack() {
		
		punchTimer++;
		if(punchTimer > 60) punchTimer = 0;
		isGokuPunching = false;
		if(input.punch) {
			if(punchTimer < 10) isGokuPunching = true;
			else isGokuPunching = false;
			walking = false;
			if(isSuper) {
				animPlayerPunchSuper.update();
				animCurrent = animPlayerPunchSuper;
			}
			else {
				animPlayerPunch.update();
				animCurrent = animPlayerPunch;				
			}
			x += 0.0;
		}
		else if(input.shoot1) {
			if(shootTimer > 30) {
				shootTimer = 0;
				int xOffset;
				if(xFlipGoku) xOffset = -4;
				else xOffset = 16;
				if(ki > 0) {
					shoot(new Shot1(x + xOffset, y + 16, 2.1, xFlipGoku));
					ki -= ki_energy1; // remove energy
				}
			}
			walking = false;
			if(isSuper) {
				animPlayerShoot1Super.update();
				animCurrent = animPlayerShoot1Super;
			}
			else {
				animPlayerShoot1.update();
				animCurrent = animPlayerShoot1;				
			}
			x += 0.0;
		}
		
		else if(input.shoot2) {
			if(shootTimer > 30) {
				shootTimer = 0;
				int xOffset;
				if(xFlipGoku) xOffset = -10;
				else xOffset = 16;
				if(ki > 0) {
					shoot(new Shot2(x + xOffset, y + 16, 3.1, xFlipGoku));
					ki -= ki_energy2;					
				}
			}
			walking = false;
			if(isSuper) {
				animPlayerShoot2Super.update();
				animCurrent = animPlayerShoot2Super;
			}
			else {
				animPlayerShoot2.update();
				animCurrent = animPlayerShoot2;				
			}
			x += 0.0;
		}
		else walking = true;
	}
	
	private void move() {
		
		double speed = 1.3;
		
		// walking
		if(input.right) {
			xFlipGoku = false;
			walkDirection = 1;
			if(isSuper) {
				animPlayerWalkSuper.update();
				animCurrent = animPlayerWalkSuper;
			}
			else {				
				animPlayerWalk.update();
				animCurrent = animPlayerWalk;
			}
			if((int)this.getX() >> 4 > 26) x -= 0.01; // keep player on screen *GAME BORDER*
			else x += speed;
		}
		else if(input.left) {
			xFlipGoku = true;
			walkDirection = -1;
			if(isSuper) {
				animPlayerWalkSuper.update();
				animCurrent = animPlayerWalkSuper;
			}
			else {				
				animPlayerWalk.update();
				animCurrent = animPlayerWalk;
			}
			if((int)this.getX() >> 4 < 0) x += 0.01; // keep player on screen *GAME BORDER*
			else x -= speed;
		}
		else {
			walking = false;
			walkDirection = 0;
			if(isSuper) {
				animPlayerSuper.update();
				animCurrent = animPlayerSuper;
			}
			else {				
				animPlayer.update();
				animCurrent = animPlayer;
			}
		}
	}
	
	// Movement for king Kai's planet in opening scene
	private void curveMove() {
		
		double speed = 1.0;
	
		double xp = ((int)Level.getPlayer(0).getX() >> 4);
		if(xp < 7.0) x += 1.0;
		else if (xp > 9.0) x -= 1.0;
		
		// walking
		if(input.right) {
			xFlipGoku = false;
			walkDirection = 1;
			animPlayerWalk.update();
			animCurrent = animPlayerWalk;
			x += speed;
			y -= 0.0;
		}
		else if(input.left) {
			xFlipGoku = true;
			walkDirection = -1;
			animPlayerWalk.update();
			animCurrent = animPlayerWalk;
			x -= speed;
			y += 0.0;
		}
		else {
			walking = false;
			walkDirection = 0;
			animPlayer.update();
			animCurrent = animPlayer;
		}
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		// health bar
		g2.setColor(Color.BLACK);
		g2.drawRect(15, 15, 317, 31);
		g2.setColor(Color.RED);
		g2.fillRect(16, 16, health, 30);
		
		// ki bar
		g2.setColor(Color.BLACK);
		g2.drawRect(15, 61, 317, 31);
		g2.setColor(Color.BLUE);
		g2.fillRect(16, 62, (int)ki, 30);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
		g.drawString("Goku", 1 << 4, 8 << 4);
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)x, (int)y, animCurrent.getSprite(), xFlipGoku, false);
		if(isSuper && superSaiyanTimer < 300) screen.renderSprite((int)x, (int)y, animCurrentEnergySuper.getSprite(), xFlipGoku, false);
	}
}
