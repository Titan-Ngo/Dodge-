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
	
	/**
	 * Constructor
	 * args: playerNum, either 1 or 2 for player1's position or player2's position
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
	
	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
