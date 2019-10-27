package com.mygdx.game.desktop;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Implements a Player object. 
 */


public class Player {

	// Constants
	public final int SPRITE_WIDTH = 70;
	public final int SPRITE_HEIGHT = 70;	
	public final int INITIAL_VELOCITY = 300;
	
	// Instance vars
	private Sprite sprite;
	private float velocity;
	public int numLives;
	
	// Team/user ID
	public int teamNum;
	public String userName;
	public String player_id;
	
	// Public coordinates
	public float x;
	public float y;

	
	/**
	 * Constructor for a Player object.
	 * @param playerNum, either 1 or 2 for team1's position or team2's position.
	 */
	public Player(int teamNum, String player_id, String userName, int hearts) {
		Texture texture = new Texture(Gdx.files.internal("sprites/Green_Dot_4.png"));
		sprite = new Sprite(texture, 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);

		if (teamNum == 1) {
			this.setPosition(0, Main.HEIGHT/2 + (float)0.001);
		} else {
			this.setPosition(Main.WIDTH - SPRITE_WIDTH + (float)0.0001, (float)0.0001 + Main.HEIGHT/2);
		}
		
		this.player_id = player_id;
		this.numLives = hearts;
		this.teamNum = teamNum;
		this.velocity = INITIAL_VELOCITY;
		this.userName = userName;
	}
	
	public Player(int teamNum, String player_id, String userName, int hearts, float x, float y) {
		Texture texture = new Texture(Gdx.files.internal("sprites/Green_Dot_4.png"));
		sprite = new Sprite(texture, 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);

		this.setPosition(x, y);
		
		this.player_id = player_id;
		this.numLives = hearts;
		this.teamNum = teamNum;
		this.velocity = INITIAL_VELOCITY;
		this.userName = userName;
	}

	public void draw(SpriteBatch batch, BitmapFont font) {
		sprite.draw(batch);
		//font.draw(batch, this.userName, this.x, this.y + SPRITE_HEIGHT + 10);
	}
	
	// Player movement methods
	
	/**
	 * Sets the position of the Player object.
	 * @param x, x pos to be set
	 * @param y, y pos to be set
	 */
	public void setPosition(float x, float y) {
		// Set x and y only if they are in bounds
		if (x < 0) { 
			this.x = 0;
		} else if (x > Main.WIDTH - SPRITE_WIDTH) {
			this.x = Main.WIDTH - SPRITE_WIDTH;	
		} else {
			this.x = x;
		}
		if (y < 0 + Main.HEIGHT_OFFSET) { 
			this.y = Main.HEIGHT_OFFSET;
		} else if (y > Main.HEIGHT - SPRITE_HEIGHT) {
			this.y = Main.HEIGHT - SPRITE_HEIGHT;	
		} else {
			this.y = y;			
		}
		//System.out.println("height:" + y + " width:" + x);
		//System.out.println("Frame" + Main.WIDTH + "  " + Main.HEIGHT);
		sprite.setPosition(this.x, this.y);
		// call updateHitbox
	}
	
	/** Moves y by an amount yDiff */
	public void moveUp(float delta) {
		setPosition(this.x, this.y + (this.velocity * delta));
	}
	
	public void moveDown(float delta) {
		setPosition(this.x, this.y - (this.velocity * delta));
	}
	
	
	// Handles bullet collisions/lives
	
	public int checkCollision(Bullet bullet) {
		
		// Makes sure that bullets coming from 
		if( bullet.bulletNum == this.teamNum ) {
			return 0;
		}
		
		float xStart = this.x + 14;
		float xEnd = this.x + SPRITE_WIDTH - 14;
		float yStart = this.y + 8; 
		float yEnd = this.y + SPRITE_HEIGHT - 8;

		float bulletXEnd = bullet.x + bullet.BULLET_WIDTH;
		float bulletYEnd = bullet.y + bullet.BULLET_HEIGHT;
		
		if (bullet.x >= xStart && bullet.x <= xEnd || bulletXEnd >= xStart && bulletXEnd <= xEnd ) {
			if (bullet.y >= yStart && bullet.y <= yEnd || bulletYEnd >= yStart && bulletYEnd <= yEnd ) {
				this.numLives--;
				bullet.hasHit = true;
				if (this.numLives == 2) {
					sprite.setColor(Color.ORANGE);
				} else {
					sprite.setColor(Color.RED);					
				}
				//teamHearts[numLives].removeHeart();
			}
		}
		
		if(this.numLives <= 0) {
			return 1; // stop game
		}	
		
		return 0;
	}
	
	public boolean isAlive() {
		return this.numLives > 0;
	}

}
