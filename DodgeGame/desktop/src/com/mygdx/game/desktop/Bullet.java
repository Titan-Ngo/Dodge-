package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Implements a Bullet object. 
 */
public class Bullet {

	// Constants
	public final int SPRITE_WIDTH = 70;
	public final int SPRITE_HEIGHT = 70;	
	private final int INITIAL_VELOCITY = 300;
	
	public final int BULLET_WIDTH = 58;
	public final int BULLET_HEIGHT = 35;	
	private final int BULLET_INITIAL_VELOCITY = 400;

	// Instance vars
	private Player player;
	private Sprite sprite;
	private Texture texture;
	private float velocity;
	private SpriteBatch batch;
	private int numLives = 9;
	
	public boolean hasHit = false;
	public int bulletNum;
	
	// Public coordinates
	public float x;
	public float y;
	
	
	
	/**
	 * Constructor for a Bullet object.
	 * @param BulletNum, either 1 or 2 for Bullet1's position or Bullet2's position; determine direction
	 * @param x is player x coord
	 * @param y is player y coord
	 */
	public Bullet(int teamNum, float x, float y) {
		texture = new Texture(Gdx.files.internal("sprites/catBullet.png"));
		sprite = new Sprite(texture, 0, 0, BULLET_WIDTH, BULLET_HEIGHT);
		this.bulletNum = teamNum;
		if (teamNum == 1) {
			this.setPosition(x + SPRITE_WIDTH, y);
			this.velocity = BULLET_INITIAL_VELOCITY;
		} else {
			this.setPosition(x - SPRITE_WIDTH, y);
			this.velocity = -1 * BULLET_INITIAL_VELOCITY;
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	// Bullet movement methods
	
	/**
	 * Sets the position of the Bullet object.
	 * @param x, x pos to be set
	 * @param y, y pos to be set
	 */
	public void setPosition(float x, float y) {
		// Set x and y only if they are in bounds
		if (x < (-1 * BULLET_WIDTH)) { 
			return;
		} else if (x > Main.WIDTH ) {
			return;
		} else {
			this.x = x;
			this.y = y;
			sprite.setPosition(this.x, this.y);
		}
	}
	
	/** Moves x by an amount yDiff */
	public void moveForward(float delta) {
		setPosition(this.x + (this.velocity * delta), this.y);
	}

}
