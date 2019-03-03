package com.zevyirmiyahu.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	
	private String path; // file path of sprites
	protected final int SPRITE_WIDTH, SPRITE_HEIGHT;
	protected final int SIZE;
	
	private int width, height;
	public int[] pixels;
	private Sprite[] sprites;
	
	public static SpriteSheet playerGoku = new SpriteSheet("/com/zevyirmiyahu/res/goku_spritesheet.png", 256, 192);
	public static SpriteSheet playerGokuStanding = new SpriteSheet(playerGoku, 0, 0, 7, 1, 32, 32);
	public static SpriteSheet playerGokuRight = new SpriteSheet(playerGoku, 0, 1, 3, 1, 32, 32);
	public static SpriteSheet playerGokuJumping = new SpriteSheet(playerGoku, 0, 2, 1, 1, 32, 32);
	public static SpriteSheet playerGokuFalling = new SpriteSheet(playerGoku, 7, 0, 1, 1, 32, 32);
	public static SpriteSheet playerGokuPunching = new SpriteSheet(playerGoku, 6, 5, 2, 1, 32, 32);
	public static SpriteSheet playerGokuShooting1 = new SpriteSheet(playerGoku, 3, 1, 1, 1, 32, 32);
	public static SpriteSheet playerGokuShooting2 = new SpriteSheet(playerGoku, 4, 1, 1, 1, 32, 32);
	public static SpriteSheet playerGokuHit = new SpriteSheet(playerGoku,1, 2, 1, 1, 32, 32);
	
	// super saiyan goku
	public static SpriteSheet playerGokuStandingSuper = new SpriteSheet(playerGoku, 0, 3, 7, 1, 32, 32);
	public static SpriteSheet playerGokuRightSuper = new SpriteSheet(playerGoku, 0, 4, 3, 1, 32, 32);
	public static SpriteSheet playerGokuJumpingSuper = new SpriteSheet(playerGoku, 0, 5, 1, 1, 32, 32);
	public static SpriteSheet playerGokuFallingSuper = new SpriteSheet(playerGoku, 7, 3, 1, 1, 32, 32);
	public static SpriteSheet playerGokuPunchingSuper = new SpriteSheet(playerGoku, 4, 5, 2, 1, 32, 32);
	public static SpriteSheet playerGokuShooting1Super = new SpriteSheet(playerGoku, 3, 4, 1, 1, 32, 32);
	public static SpriteSheet playerGokuShooting2Super = new SpriteSheet(playerGoku, 4, 4, 1, 1, 32, 32);
	public static SpriteSheet playerGokuHitSuper = new SpriteSheet(playerGoku, 1, 5, 1, 1, 32, 32);
	public static SpriteSheet playerGokuEnergySuper = new SpriteSheet(playerGoku, 6, 4, 2, 1, 32, 32);
	public static SpriteSheet playerGokuDead = new SpriteSheet(playerGoku, 7, 1, 1, 1, 32, 32);
	
	public static SpriteSheet kingKai = new SpriteSheet("/com/zevyirmiyahu/res/kingKai.png", 64, 64);
	public static SpriteSheet kingKaiStanding = new SpriteSheet(kingKai, 0, 0, 1, 1, 32, 32);
	public static SpriteSheet kingKaiCrying = new SpriteSheet(kingKai, 0, 1, 2, 1, 32, 32);
	
	public static SpriteSheet shot1 = new SpriteSheet(playerGoku, 4, 5, 6, 5, 16, 16);
	public static SpriteSheet shot2 = new SpriteSheet(playerGoku, 4, 5, 5, 5, 32, 16);
	
	public static SpriteSheet zamasu = new SpriteSheet("/com/zevyirmiyahu/res/zamasu_spritesheet.png", 256, 192);
	public static SpriteSheet zamasuStanding = new SpriteSheet(zamasu, 0, 0, 3, 1, 32, 32);
	public static SpriteSheet zamasuRight = new SpriteSheet(zamasu, 0, 1, 3, 1, 32, 32);
	public static SpriteSheet zamasuJumping = new SpriteSheet(zamasu, 0, 2, 1, 1, 32, 32);
	public static SpriteSheet zamasuFalling = new SpriteSheet(zamasu, 7, 2, 1, 1, 32, 32);
	public static SpriteSheet zamasuShooting = new SpriteSheet(zamasu, 1, 2, 1, 1, 32, 32);
	public static SpriteSheet zamasuPunching = new SpriteSheet(zamasu, 2, 2, 1, 2, 32, 32);
	public static SpriteSheet zamasuTeleporting = new SpriteSheet(zamasu, 5, 2, 2, 1, 32, 32);
	public static SpriteSheet zamasuHit = new SpriteSheet(zamasu, 3, 2, 1, 1, 32, 32);
	public static SpriteSheet zamasuIsDead = new SpriteSheet(zamasu, 4, 2, 1, 1, 32, 32);

	public static SpriteSheet zamasuShot1 = new SpriteSheet(zamasu, 0, 6, 3, 1, 16, 16);
	
	public static SpriteSheet aboutMenuPicture = new SpriteSheet("/com/zevyirmiyahu/res/DBZ_Sprite_Menu.png", 128, 64);
	public static SpriteSheet menuBackground = new SpriteSheet("/com/zevyirmiyahu/res/DBZ_Title_Menu_V3.jpg", 455, 256);
	public static SpriteSheet levelOneForeground = new SpriteSheet("/com/zevyirmiyahu/res/DBZ_Clouds.png", 1365, 256);
	public static SpriteSheet levelOneBackground = new SpriteSheet("/com/zevyirmiyahu/res/DBZLevel.jpg", 455, 256);
	public static SpriteSheet kingKaisPlanet = new SpriteSheet("/com/zevyirmiyahu/res/KingKaisPlanet.jpg", 455, 256);
	public static SpriteSheet gameOverPicture = new SpriteSheet("/com/zevyirmiyahu/res/DBZ_GameOver_Menu.png", 455, 256);
	public static SpriteSheet gameWinPicture = new SpriteSheet("/com/zevyirmiyahu/res/DBZ_Win_Menu_V1.jpg", 455, 256);
	
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1; // not used with this constructor
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[width * height];
		load();
	}
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteWidth, int spriteHeight) {
		int xx = x * spriteWidth;
		int yy = y * spriteHeight;
		int w = width * spriteWidth;
		int h = height * spriteHeight;
		if(width == height) SIZE = width;
		else SIZE = -1; //Since not square
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for(int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteWidth * spriteHeight];
				for(int y0 = 0; y0 < spriteHeight; y0++) {
					for(int x0 = 0; x0 < spriteWidth; x0++) {
						spritePixels[x0 + y0 * spriteWidth] 
								= pixels[(x0 + xa * spriteWidth) + (y0 + ya * spriteHeight) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteWidth, spriteHeight);
				sprites[frame++] = sprite;
			}
		}
	}

	private void load() {
		try {
			System.out.println("Trying to load: " + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println("Succeded!");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("FAILED!");
		}
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
}
