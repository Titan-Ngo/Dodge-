package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Implements a Player object. 
 */
public class Player {

	// Constants
	private final int SPRITE_WIDTH = 59;
	private final int SPRITE_HEIGHT = 65;	
	
	// Instance vars
	private Sprite sprite;
	private Texture texture;
	private float velocity;
	private int numLives = 3;
	
	// Public coordinates
	public float x;
	public float y;
	
	/**
	 * Constructor for a Player object.
	 * @param playerNum, either 1 or 2 for player1's position or player2's position.
	 */
	public Player(int playerNum) {
		texture = new Texture(Gdx.files.internal("sprites/smallDodgeCat.jpg"));
		sprite = new Sprite(texture, 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);

		if (playerNum == 1) {
			this.setPosition(0, Main.HEIGHT/2);
		} else {
			this.setPosition(Main.WIDTH - SPRITE_WIDTH, Main.HEIGHT/2);
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	// Player movement methods
	
	/**
	 * Sets the position of the Player object.
	 * @param x, x pos to be set
	 * @param y, y pos to be set
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		sprite.setPosition(this.x, this.y);
		// call updateHitbox
	}
	
	/** Moves x by an amount xDiff */
	public void moveX(float xDiff) {
		sprite.setPosition(this.x + xDiff, this.y);
	}
	
	/** Moves y by an amount yDiff */
	public void moveY(float yDiff) {
		 sprite.setPosition(this.x, this.y + yDiff);
	}
	
	/**
	 * 
	 */
	public void shootBullets() {
	}

}
