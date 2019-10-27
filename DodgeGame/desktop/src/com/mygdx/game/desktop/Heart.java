package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Heart {

	// Instance vars
	private Sprite sprite;
	private Texture texture;
	
	public boolean removed = false;
	
	public Heart(int teamNum, int heartNum) {
		texture = new Texture(Gdx.files.internal("sprites/heart.png"));
		sprite = new Sprite(texture, 0, 0, 50, 40);

		if (teamNum == 1) {
			this.setPosition(heartNum * 45, 0);
		} else {
			this.setPosition(Main.WIDTH - (heartNum * 45) - 45, 0);
		}
	}
	
	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
	}
	
	public void removeHeart() {
		this.removed = true;
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
